package ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.giwahdavalos.gizo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import presenters.LoginPresenter;

public class Login extends AppCompatActivity implements LoginView{

    @BindView(R.id.registro_text)
    TextView registro_text;

    @BindView(R.id.login_button)
    Button login_button;

    @BindView(R.id.email)
    EditText email;

    @BindView(R.id.password)
    EditText password;

    LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.LoginTheme);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        loginPresenter = new LoginPresenter(this);

        registro_text.setOnClickListener( (View) -> {
            Intent i = new Intent(Login.this, Registro.class);
            startActivity(i);
        } );

        login_button.setOnClickListener( (View) -> {
            this.disableElements();

            String email_txt = email.getText().toString();
            String password_txt = password.getText().toString();

            loginPresenter.executeLogin(email_txt, password_txt);
        });
    }


    @Override
    public void disableElements() {
        this.login_button.setEnabled(false);
        this.login_button.setClickable(false);

        this.email.setEnabled(false);

        this.password.setEnabled(false);
    }
}
