package ui.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.giwahdavalos.gizo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import presenters.AddColeccionPresenter;
import ui.ghosts.AddColeccionView;
import utils.SoftKeyboard;

public class AddColeccion extends AppCompatActivity implements AddColeccionView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.input_texto)
    EditText input_texto;

    @BindView(R.id.add_button)
    FloatingActionButton add_button;

    private AddColeccionPresenter addColeccionPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coleccion);

        ButterKnife.bind(this);
        addColeccionPresenter = new AddColeccionPresenter(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        toolbar.setNavigationOnClickListener( v -> finish() );
        add_button.setOnClickListener( v -> {
            this.disableElements();

            String texto = input_texto.getText().toString();
            addColeccionPresenter.executeAddColeccion(texto);
        } );

    }

    @Override
    public void disableElements() {
        SoftKeyboard.hideKeyboard(this);
        this.add_button.setEnabled(false);
        this.add_button.setClickable(false);

        this.input_texto.setEnabled(false);
    }

    @Override
    public boolean canSubmit() {
        String texto = input_texto.getText().toString();

        if (texto.length() > 0) {
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void enableElements() {
        this.add_button.setEnabled(true);
        this.add_button.setClickable(true);

        this.input_texto.setEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
