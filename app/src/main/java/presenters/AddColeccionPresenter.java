package presenters;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import components.AddColeccionComponent;
import components.DaggerAddColeccionComponent;
import modules.PreferencesEditorModule;
import modules.PreferencesModule;
import rest.RestAdapter;
import rest.models.Coleccion;
import rest.models.Usuario;
import rest.services.AddColeccionService;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import ui.activities.AddColeccionView;
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

    public AddColeccionPresenter(AddColeccionView addColeccionView) {
        this.addColeccionView = addColeccionView;

        AddColeccionComponent component
                = DaggerAddColeccionComponent
                .builder()
                .preferencesModule(new PreferencesModule(((Activity) addColeccionView).getApplicationContext()))
                .preferencesEditorModule(new PreferencesEditorModule(((Activity) addColeccionView).getApplicationContext()))
                .build();

        this.addColeccionService = RestAdapter.getInstance().create(AddColeccionService.class);

        pref = component.providePreferences();
        editor = component.provideSharedPrefsEditor();
    }

    public void executeAddColeccion (String texto) {
        if (!addColeccionView.canSubmit()) {
            addColeccionView.enableElements();
            return;
        }

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
                        Toast.makeText((Activity)addColeccionView, e.getMessage(),
                                Toast.LENGTH_LONG).show();
                        Log.e("ERROR", e.getMessage());
                    }

                    @Override
                    public void onNext(Coleccion coleccion) {
                        ColeccionHelper.pushColeccion(editor, pref, coleccion);
                        Toast.makeText((Activity)addColeccionView, "Colección agregada",
                                Toast.LENGTH_LONG).show();

                        ((Activity)addColeccionView).finish();
                    }
                });

    }
}
