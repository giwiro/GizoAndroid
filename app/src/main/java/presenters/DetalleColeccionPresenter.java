package presenters;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.example.giwahdavalos.gizo.R;

import java.io.File;

import components.DaggerDetalleColeccionComponent;
import components.DetalleColeccionComponent;
import modules.DialogLoadingModule;
import modules.PreferencesEditorModule;
import modules.PreferencesModule;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rest.RestAdapter;
import rest.models.Coleccion;
import rest.models.Pictograma;
import rest.services.AddPictogramaService;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import ui.ghosts.DetalleColeccionView;
import utils.ColeccionHelper;
import utils.PictogramaHelper;

/**
 * Created by Gi Wah Davalos on 26/06/2016.
 */
public class DetalleColeccionPresenter {

    private DetalleColeccionView detalleColeccionView;
    private AddPictogramaService addPictogramaService;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private AlertDialog alert_dialog;

    public DetalleColeccionPresenter(DetalleColeccionView detalleColeccionView) {
        this.detalleColeccionView = detalleColeccionView;

        DetalleColeccionComponent component
                = DaggerDetalleColeccionComponent
                .builder()
                .preferencesModule(new PreferencesModule(((Activity) detalleColeccionView).getApplicationContext()))
                .preferencesEditorModule(new PreferencesEditorModule(((Activity) detalleColeccionView).getApplicationContext()))
                .dialogLoadingModule(new DialogLoadingModule(
                    ((Activity) detalleColeccionView),
                    R.style.BlueAlertDialogStyle))
                .build();

        addPictogramaService = RestAdapter.getInstance().create(AddPictogramaService.class);

        pref = component.providePreferences();
        editor = component.provideSharedPrefsEditor();
        alert_dialog = component.provideAlertLoading();

        alert_dialog.hide();
    }

    public Coleccion getCollection(String idColeccion) {
        return ColeccionHelper.getColeccionObject(pref, idColeccion);
    }

    public void executeCreatePictograma(String idColeccion, File file, File sound, String pic_name) {

        alert_dialog.show();

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part file_part =
                MultipartBody.Part.createFormData("foto", file.getName(), requestFile);

        RequestBody requestAudio =
                RequestBody.create(MediaType.parse("multipart/form-data"), sound);

        MultipartBody.Part audio_part =
                MultipartBody.Part.createFormData("audio", sound.getName(), requestAudio);

        // add another part within the multipart request
        /*RequestBody requestNombre =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), pic_name);*/


        addPictogramaService
                .addPictograma(pic_name, idColeccion, file_part, audio_part)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Pictograma>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        try {
                            alert_dialog.dismiss();
                            Toast.makeText((Activity)detalleColeccionView, e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                            Log.e("ERROR", e.getMessage());
                        }catch(Throwable ex) {
                            Log.e("ERROR", ex.getMessage());
                            ex.printStackTrace();
                        }

                    }

                    @Override
                    public void onNext(Pictograma pictograma) {
                        alert_dialog.dismiss();
                        PictogramaHelper.pushPictograma(editor, pref, idColeccion, pictograma);
                        detalleColeccionView.refreshPictogramas(pictograma);
                        Toast.makeText((Activity)detalleColeccionView, "Guardado: " + pictograma.getNombre(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }


}
