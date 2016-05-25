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


    Button registro_button;
    EditText nombres;
    EditText apellidos;
    EditText email;
    EditText password;
    LinearLayout go_login_button;
    ProgressBar progressBar;

    private RegistroView registroView;
    private RegistroService registroService;
    private SharedPreferences.Editor editor;

    public RegistroPresenter(RegistroView registroView) {
        this.registroView = registroView;
        this.registro_button = (Button) ((Activity)registroView).findViewById(R.id.registro_button);

        this.nombres = (EditText) ((Activity)registroView).findViewById(R.id.nombres);
        this.apellidos = (EditText) ((Activity)registroView).findViewById(R.id.apellidos);
        this.email = (EditText) ((Activity)registroView).findViewById(R.id.email);
        this.password = (EditText) ((Activity)registroView).findViewById(R.id.password);
        this.go_login_button = (LinearLayout) ((Activity)registroView).findViewById(R.id.go_login_button);
        this.progressBar = (ProgressBar) ((Activity)registroView).findViewById(R.id.progressBar);

        registroService = RestAdapter.getInstance().create(RegistroService.class);

        RegistroComponent component
                = DaggerRegistroComponent
                .builder()
                .preferencesEditorModule(new PreferencesEditorModule(((Activity) registroView).getApplicationContext()))
                .build();

        editor = component.provideSharedPrefsEditor();
    }

    public void executeRegistro(String nombres_txt, String apellidos_txt, String email_txt, String password_txt) {
        if (!canSubmit()) {
            enableElements();
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
                        enableElements();
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

    public boolean canSubmit() {
        String nombres_txt = nombres.getText().toString();
        String apellidos_txt = apellidos.getText().toString();
        String email_txt = email.getText().toString();
        String password_txt = password.getText().toString();

        if (nombres_txt.length() > 0 &&
                apellidos_txt.length() > 0 &&
                email_txt.length() > 0 &&
                password_txt.length() > 0) {
            return true;
        }else{
            return false;
        }
    }


    public void enableElements() {

        this.registro_button.setVisibility(Button.VISIBLE);
        this.registro_button.setClickable(true);

        this.nombres.setVisibility(TextView.VISIBLE);
        this.apellidos.setVisibility(TextView.VISIBLE);
        this.email.setVisibility(TextView.VISIBLE);
        this.password.setVisibility(TextView.VISIBLE);
        this.go_login_button.setVisibility(LinearLayout.VISIBLE);

        this.progressBar.setVisibility(ProgressBar.GONE);
    }
}
