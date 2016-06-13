package ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.giwahdavalos.gizo.R;

import org.opencv.android.OpenCVLoader;

import components.DaggerMainComponent;
import components.MainComponent;
import modules.PreferencesModule;
import rest.models.Usuario;
import utils.SessionHelper;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences pref;

    static {
        if (!OpenCVLoader.initDebug()) {
            Log.e("OPENCV!", "not loaded :(");
        }else{
            Log.e("OPENCV!", "LOADED !");
        }
    }

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
}
