package ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.giwahdavalos.gizo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import presenters.LoginPresenter;
import ui.ghosts.LoginView;
import utils.SoftKeyboard;

public class Login extends AppCompatActivity implements LoginView {

    @BindView(R.id.registro_text)
    TextView registro_text;

    @BindView(R.id.login_button)
    Button login_button;

    @BindView(R.id.email)
    EditText email;

    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTheme(R.style.LoginTheme);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        loginPresenter = new LoginPresenter(this);

        registro_text.setOnClickListener( (View) -> {
            Intent i = new Intent(Login.this, Registro.class);
            startActivity(i);
        } );

        login_button.setOnClickListener( v -> {
            this.disableElements();
            String email_txt = email.getText().toString();
            String password_txt = password.getText().toString();
            loginPresenter.executeLogin(email_txt, password_txt);
        });
    }


    @Override
    public void disableElements() {

        SoftKeyboard.hideKeyboard(this);
        this.login_button.setVisibility(Button.GONE);
        this.login_button.setClickable(false);

        this.email.setVisibility(TextView.GONE);
        this.password.setVisibility(TextView.GONE);
        this.registro_text.setVisibility(TextView.GONE);
        this.progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    @Override
    public boolean canSubmit() {
        String email_txt = email.getText().toString();
        String password_txt = password.getText().toString();

        if (email_txt.length() > 0 && password_txt.length() > 0) {
            return true;
        }else{
            return false;
        }
    }

    @Override
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
