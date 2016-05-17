package ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.giwahdavalos.gizo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import presenters.RegistroPresenter;

public class Registro extends AppCompatActivity implements RegistroView{

    @BindView(R.id.go_login_button)
    LinearLayout go_login_button;

    @BindView(R.id.registro_button)
    Button registro_button;

    @BindView(R.id.nombres)
    EditText nombres;

    @BindView(R.id.apellidos)
    EditText apellidos;

    @BindView(R.id.email)
    EditText email;

    @BindView(R.id.password)
    EditText password;

    RegistroPresenter registroPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.RegistroTheme);
        setContentView(R.layout.activity_registro);

        ButterKnife.bind(this);

        registroPresenter = new RegistroPresenter(this);

        go_login_button.setOnClickListener((View) -> finish());

        registro_button.setOnClickListener((View) -> {
            this.disableElements();

            String nombres_txt = nombres.getText().toString();
            String apellidos_txt = apellidos.getText().toString();
            String email_txt = email.getText().toString();
            String password_txt = password.getText().toString();

            registroPresenter.executeRegistro(nombres_txt, apellidos_txt, email_txt, password_txt);
        });
    }

    @Override
    public void disableElements() {
        this.registro_button.setEnabled(false);
        this.registro_button.setClickable(false);

        this.nombres.setEnabled(false);
        this.apellidos.setEnabled(false);
        this.email.setEnabled(false);
        this.password.setEnabled(false);
    }
}
