package ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.giwahdavalos.gizo.R;

import java.util.List;

import components.DaggerMainComponent;
import components.MainComponent;
import modules.PreferencesModule;
import rest.models.Coleccion;
import rest.models.Usuario;
import utils.ColeccionHelper;
import utils.SessionHelper;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        MainComponent component
                = DaggerMainComponent
                    .builder()
                    .preferencesModule(new PreferencesModule(this.getApplicationContext()))
                    .build();

        pref = component.providePreferences();

        session_check();
        general_check();

    }


    private void session_check() {

        Usuario usuario = SessionHelper.getSession(pref);

        boolean exists = usuario != null;
        Intent i;

        if (exists) {
            i = new Intent(MainActivity.this, Colecciones.class);
        }else{
            i = new Intent(MainActivity.this, Login.class);
        }


        startActivity(i);
    }

    private void general_check() {

        String usuario_tag = "GENERAL_CHECK > USUARIO";
        String colecciones_tag = "GENERAL_CHECK > COL";

        Usuario usuario = SessionHelper.getSession(pref);
        List<Coleccion> colecciones = ColeccionHelper.getColecciones(pref);

        if (usuario == null) {
            Log.d(usuario_tag, "No hay usuario");
        }else {
            Log.d(usuario_tag, usuario.toString());
        }

        if (colecciones == null) {
            Log.d(colecciones_tag, "No hay colecciones");
        }else{
            Log.d(colecciones_tag, "#" + colecciones.size());
        }
    }
}
