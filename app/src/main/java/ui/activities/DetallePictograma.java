package ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giwahdavalos.gizo.R;
import com.squareup.picasso.Picasso;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import butterknife.BindView;
import butterknife.ButterKnife;
import rest.models.Coleccion;
import rest.models.Pictograma;

public class DetallePictograma extends AppCompatActivity {

    @BindView(R.id.card_image)
    ImageView card_image;

    @BindView(R.id.titulo_pictograma)
    TextView titulo_pictograma;

    /*@BindView(R.id.fab)
    FloatingActionButton mFab;*/

    @BindView(R.id.play_sound)
    Button play_sound;

    private Pictograma pictograma;
    private Coleccion coleccion;
    private MediaPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pictograma);

        ButterKnife.bind(this);

        this.pictograma = (Pictograma) getIntent().getSerializableExtra("pictograma");
        this.coleccion = (Coleccion) getIntent().getSerializableExtra("coleccion");

        this.titulo_pictograma.setText(this.pictograma.getNombre());

        File imageFile = new File(Environment
                .getExternalStoragePublicDirectory("/" +
                                getResources().getString(R.string.app_name) + "/" +
                                this.coleccion.get_id()
                ),
                this.pictograma.getFileName());

        File soundFile = new File(Environment
                .getExternalStoragePublicDirectory("/" +
                        getResources().getString(R.string.app_name) + "/" +
                        this.coleccion.get_id()
                ),
                this.pictograma.getSoundFileName());


        if (imageFile.exists()) {
            Picasso
                    .with(this)
                    .load(imageFile)
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .fit().centerInside()
                    .into(card_image);

        }

        if (soundFile.exists()) {
            this.mPlayer = new MediaPlayer();
            try {
                this.mPlayer.setDataSource(soundFile.getAbsolutePath());
                this.mPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.play_sound.setOnClickListener( v -> {
            if (this.mPlayer != null) {
                this.mPlayer.start();
            }

        });

    }

}
