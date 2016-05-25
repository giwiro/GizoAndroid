package ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

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

        password.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    registro_button.performClick();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void disableElements() {

        try {
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            // TODO: handle exception
        }

        this.registro_button.setVisibility(Button.GONE);
        this.registro_button.setClickable(false);

        this.nombres.setVisibility(TextView.GONE);
        this.apellidos.setVisibility(TextView.GONE);
        this.email.setVisibility(TextView.GONE);
        this.password.setVisibility(TextView.GONE);
        this.go_login_button.setVisibility(LinearLayout.GONE);

        this.progressBar.setVisibility(ProgressBar.VISIBLE);
    }
}
