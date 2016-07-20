package presenters;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.example.giwahdavalos.gizo.R;

import components.AddColeccionComponent;
import components.DaggerAddColeccionComponent;
import modules.DialogLoadingModule;
import modules.PreferencesEditorModule;
import modules.PreferencesModule;
import rest.RestAdapter;
import rest.models.Coleccion;
import rest.models.Usuario;
import rest.services.AddColeccionService;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import ui.ghosts.AddColeccionView;
import utils.ColeccionHelper;
import utils.SessionHelper;

/**
 * Created by Gi Wah Davalos on 22/06/2016.
 */
public class AddColeccionPresenter {

    private AddColeccionView addColeccionView;
    private AddColeccionService addColeccionService;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private AlertDialog alert_dialog;

    public AddColeccionPresenter(AddColeccionView addColeccionView) {
        this.addColeccionView = addColeccionView;

        AddColeccionComponent component
                = DaggerAddColeccionComponent
                .builder()
                .preferencesModule(new PreferencesModule(((Activity) addColeccionView).getApplicationContext()))
                .preferencesEditorModule(new PreferencesEditorModule(((Activity) addColeccionView).getApplicationContext()))
                .dialogLoadingModule(new DialogLoadingModule(
                        ((Activity) addColeccionView),
                        R.style.BlueAlertDialogStyle))
                .build();

        this.addColeccionService = RestAdapter.getInstance().create(AddColeccionService.class);

        pref = component.providePreferences();
        editor = component.provideSharedPrefsEditor();
        alert_dialog = component.provideAlertLoading();

        alert_dialog.hide();

    }

    public void executeAddColeccion (String texto) {
        if (!addColeccionView.canSubmit()) {
            addColeccionView.enableElements();
            return;
        }

        alert_dialog.show();
        Usuario usuario = SessionHelper.getSession(pref);
        String idUsuario = usuario.get_id();

        addColeccionService
                .addColeccion(idUsuario, texto)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Coleccion>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        addColeccionView.enableElements();
                        alert_dialog.dismiss();
                        Toast.makeText((Activity)addColeccionView, e.getMessage(),
                                Toast.LENGTH_LONG).show();
                        Log.e("ERROR", e.getMessage());

                    }

                    @Override
                    public void onNext(Coleccion coleccion) {
                        ColeccionHelper.pushColeccion(editor, pref, coleccion);
                        alert_dialog.dismiss();
                        Toast.makeText((Activity)addColeccionView, "Colección agregada",
                                Toast.LENGTH_LONG).show();

                        ((Activity)addColeccionView).finish();
                    }
                });

    }
}
