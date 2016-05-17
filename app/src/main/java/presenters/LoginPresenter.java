package presenters;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.giwahdavalos.gizo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import rest.RestAdapter;
import rest.models.Usuario;
import rest.services.LoginService;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import ui.activities.Colecciones;
import ui.activities.Login;
import ui.activities.LoginView;

/**
 * Created by Gi Wah Davalos on 15/05/2016.
 */
public class LoginPresenter {


    Button login_button;
    EditText email;
    EditText password;

    private LoginView loginView;
    private LoginService loginService;

    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;
        this.login_button = (Button) ((Activity)loginView).findViewById(R.id.login_button);
        this.email = (EditText) ((Activity)loginView).findViewById(R.id.email);
        this.password = (EditText) ((Activity)loginView).findViewById(R.id.password);

        loginService = RestAdapter.getInstance().create(LoginService.class);
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
                        Toast.makeText((Activity)loginView, "Autenticación correcta",
                                Toast.LENGTH_LONG).show();

                        Intent goToA = new Intent( (Activity)loginView , Colecciones.class);
                        goToA.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ((Activity)loginView).startActivity(goToA);

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
        this.login_button.setEnabled(true);
        this.login_button.setClickable(true);

        this.email.setEnabled(true);
        this.password.setEnabled(true);
        this.password.setText("");
    }
}
