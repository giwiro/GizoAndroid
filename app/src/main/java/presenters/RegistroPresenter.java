package presenters;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giwahdavalos.gizo.R;

import components.DaggerRegistroComponent;
import components.RegistroComponent;
import modules.PreferencesEditorModule;
import rest.RestAdapter;
import rest.models.Usuario;
import rest.services.RegistroService;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import ui.activities.Colecciones;
import ui.activities.RegistroView;
import utils.SessionHelper;

/**
 * Created by Gi Wah Davalos on 15/05/2016.
 */
public class RegistroPresenter {

    private RegistroView registroView;
    private RegistroService registroService;
    private SharedPreferences.Editor editor;

    public RegistroPresenter(RegistroView registroView) {
        this.registroView = registroView;
        registroService = RestAdapter.getInstance().create(RegistroService.class);

        RegistroComponent component
                = DaggerRegistroComponent
                .builder()
                .preferencesEditorModule(new PreferencesEditorModule(((Activity) registroView).getApplicationContext()))
                .build();

        editor = component.provideSharedPrefsEditor();
    }

    public void executeRegistro(String nombres_txt, String apellidos_txt, String email_txt, String password_txt) {
        if (!registroView.canSubmit()) {
            registroView.enableElements();
            return;
        }
        registroService
                .registro(
                        nombres_txt,
                        apellidos_txt,
                        email_txt,
                        password_txt)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Usuario>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        registroView.enableElements();
                        Toast.makeText((Activity)registroView, e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        Log.e("ERROR", e.getMessage());
                    }

                    @Override
                    public void onNext(Usuario usuario) {
                        //enableElements();

                        SessionHelper.writeSession(editor, usuario);


                        Log.e("USUARIO", usuario.toString());
                        Toast.makeText((Activity)registroView, "Usuario creado satisfactoriamente",
                                Toast.LENGTH_LONG).show();

                        Intent goToA = new Intent( (Activity)registroView , Colecciones.class);
                        goToA.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ((Activity)registroView).startActivity(goToA);
                        ((Activity)registroView).finish();
                    }
                });
    }


}
