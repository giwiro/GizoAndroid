package presenters;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giwahdavalos.gizo.R;
import com.google.gson.Gson;

import components.DaggerLoginComponent;
import components.LoginComponent;
import modules.PreferencesEditorModule;
import modules.PreferencesModule;
import rest.RestAdapter;
import rest.models.Usuario;
import rest.services.LoginService;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import ui.activities.Colecciones;
import ui.activities.LoginView;
import utils.SessionHelper;

/**
 * Created by Gi Wah Davalos on 15/05/2016.
 */
public class LoginPresenter {

    TextView registro_text;
    Button login_button;
    EditText email;
    EditText password;
    ProgressBar progressBar;


    private LoginView loginView;
    private LoginService loginService;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;
        this.login_button = (Button) ((Activity)loginView).findViewById(R.id.login_button);
        this.email = (EditText) ((Activity)loginView).findViewById(R.id.email);
        this.password = (EditText) ((Activity)loginView).findViewById(R.id.password);
        this.registro_text = (TextView) ((Activity)loginView).findViewById(R.id.registro_text);
        this.progressBar = (ProgressBar) ((Activity)loginView).findViewById(R.id.progressBar);

        LoginComponent component
                = DaggerLoginComponent
                    .builder()
                    .preferencesModule(new PreferencesModule(((Activity) loginView).getApplicationContext()))
                    .preferencesEditorModule(new PreferencesEditorModule(((Activity) loginView).getApplicationContext()))
                    .build();

        pref = component.providePreferences();
        editor = component.provideSharedPrefsEditor();

        loginService = RestAdapter.getInstance().create(LoginService.class);

        Gson gson = new Gson();
        String serialized_user = pref.getString("usuario", "");

        Log.e("serialized_user", "<-" + serialized_user );
    }

    public void executeLogin(String email_txt, String password_txt) {
        if (!canSubmit()) {
            enableElements();
            return;
        }
        loginService
                .login(email_txt, password_txt)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Usuario>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        enableElements();
                        Toast.makeText((Activity)loginView, e.getMessage(),
                                Toast.LENGTH_LONG).show();
                        Log.e("ERROR", e.getMessage());
                    }

                    @Override
                    public void onNext(Usuario usuario) {
                        //enableElements();

                        SessionHelper.writeSession(editor, usuario);

                        Toast.makeText((Activity)loginView, "Autenticación correcta",
                                Toast.LENGTH_LONG).show();

                        Intent goToA = new Intent( (Activity)loginView , Colecciones.class);
                        goToA.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ((Activity)loginView).startActivity(goToA);
                        ((Activity)loginView).finish();

                        Log.e("USUARIO", usuario.toString());
                    }
                });
    }

    public boolean canSubmit() {
        String email_txt = email.getText().toString();
        String password_txt = password.getText().toString();

        if (email_txt.length() > 0 && password_txt.length() > 0) {
            return true;
        }else{
            return false;
        }
    }


    public void enableElements() {

        this.login_button.setVisibility(Button.VISIBLE);
        this.login_button.setClickable(true);

        this.email.setVisibility(TextView.VISIBLE);

        this.password.setVisibility(TextView.VISIBLE);
        this.password.setText("");

        this.registro_text.setVisibility(TextView.VISIBLE);

        this.progressBar.setVisibility(ProgressBar.GONE);
    }
}
