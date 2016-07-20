package ui.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.giwahdavalos.gizo.R;

import java.util.List;

import ui.ghosts.ColeccionesView;
import ui.recyclerView.adapters.ColeccionesListAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import ui.recyclerView.decorations.DividerItemDecoration;
import presenters.ColeccionesPresenter;
import rest.models.Coleccion;
import utils.SoftKeyboard;

public class Colecciones extends AppCompatActivity implements ColeccionesView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    /*@BindView(R.id.search_view_container
    SearchViewLayout searchViewLayout;*/

    @BindView(R.id.colecciones_list)
    RecyclerView recyclerView;

    @BindView(R.id.empty_colecciones)
    TextView empty_colecciones;

    @BindView(R.id.add_button)
    FloatingActionButton add_button;

    private ColeccionesPresenter coleccionesPresenter;
    private ColeccionesListAdapter coleccionesListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccentDark));
        setContentView(R.layout.activity_colecciones);
        coleccionesPresenter = new ColeccionesPresenter(this);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        List<Coleccion> colecciones = coleccionesPresenter.getColecciones();
        checkEmptyList(colecciones);
        coleccionesListAdapter = new ColeccionesListAdapter(this, colecciones);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(coleccionesListAdapter);

        //searchViewLayout.setExpandedContentSupportFragment(this, new SearchStaticListSupportFragment());
        /*searchViewLayout.handleToolbarAnimation(toolbar);
        searchViewLayout.setCollapsedHint("Collapsed Hint");
        searchViewLayout.setExpandedHint("Expanded Hint");*/

        add_button.setOnClickListener( v -> {
            Intent i = new Intent(Colecciones.this, AddColeccion.class);
            startActivity(i);
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.colecciones_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_recon) {
            Intent i = new Intent(Colecciones.this, Blank.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SoftKeyboard.hideKeyboard(this);
        List<Coleccion> colecciones = coleccionesPresenter.getColecciones();
        Log.d("COLECCIONES_ONRESUME", colecciones.size() + "");
        //Log.d("PICTOGRAMAS_ONRESUME", colecciones.get(0).getPictogramas().size() + "");

        coleccionesListAdapter = new ColeccionesListAdapter(this, colecciones);
        checkEmptyList(colecciones);
        recyclerView.swapAdapter(coleccionesListAdapter, false);
        //coleccionesListAdapter.notifyDataSetChanged();
    }

    public void checkEmptyList(List<Coleccion> colecciones) {
        if (colecciones == null || colecciones.size() == 0) {
            empty_colecciones.setVisibility(TextView.VISIBLE);
        }else{
            empty_colecciones.setVisibility(TextView.GONE);
        }

    }
}
