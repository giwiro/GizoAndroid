package ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.giwahdavalos.gizo.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session_check();
        setContentView(R.layout.activity_main);
    }


    private void session_check() {
        Intent i = new Intent(MainActivity.this, Login.class);
        startActivity(i);
    }
}
