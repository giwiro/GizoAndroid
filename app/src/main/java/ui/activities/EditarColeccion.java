package ui.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.TextView;

import com.example.giwahdavalos.gizo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import presenters.AddColeccionPresenter;
import presenters.EditarColeccionPresenter;
import rest.models.Coleccion;
import ui.ghosts.EditarColeccionView;

/**
 * Created by Gi Wah Davalos on 5/07/2016.
 */
public class EditarColeccion extends AppCompatActivity implements EditarColeccionView{

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.input_texto)
    EditText input_texto;

    @BindView(R.id.add_button)
    FloatingActionButton add_button;

    private EditarColeccionPresenter editarColeccionPresenter;
    private Coleccion coleccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_coleccion);

        this.coleccion = (Coleccion) getIntent().getSerializableExtra("coleccion");

        ButterKnife.bind(this);
        editarColeccionPresenter = new EditarColeccionPresenter(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        input_texto.setText(coleccion.getTexto());
        toolbar.setNavigationOnClickListener( v -> finish() );

    }
}
