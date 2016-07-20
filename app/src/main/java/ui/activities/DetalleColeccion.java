package ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.giwahdavalos.gizo.R;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import presenters.DetalleColeccionPresenter;
import rest.models.Coleccion;
import rest.models.Pictograma;
import ui.recyclerView.adapters.PictogramasListAdapter;
import utils.ColeccionHelper;
import utils.ImageHelper;
import utils.PictogramaSaveHelper;
import utils.StringHelper;

public class DetalleColeccion extends AppCompatActivity implements ui.ghosts.DetalleColeccionView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.empty_pictogramas)
    TextView empty_colecciones;

    @BindView(R.id.pictogramas_list)
    RecyclerView recyclerView;

    @BindView(R.id.add_button)
    FloatingActionButton add_button;

    private Coleccion coleccion;
    //private String pictograma_name_to_save;
    private PictogramaSaveHelper pictogramaSaveHelper;
    private DetalleColeccionPresenter detalleColeccionPresenter;
    private PictogramasListAdapter pictogramasListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_coleccion);

        String idColeccion
                = ((Coleccion) getIntent().getSerializableExtra("coleccion") ).get_id();
        ButterKnife.bind(this);
        this.detalleColeccionPresenter = new DetalleColeccionPresenter(this);

        this.coleccion = detalleColeccionPresenter.getCollection(idColeccion);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        List<Pictograma> pictogramas = this.coleccion.getPictogramas();
        checkEmptyList(pictogramas);
        this.pictogramasListAdapter = new PictogramasListAdapter(this, coleccion);

        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        this.recyclerView.setAdapter(pictogramasListAdapter);


        this.toolbar_title.setText(StringHelper.shortenString(20, this.coleccion.getTexto()));
        this.toolbar.setNavigationOnClickListener(v -> finish());

        this.add_button.setOnClickListener( v -> {
            pictogramaSaveHelper = new PictogramaSaveHelper();
            new MaterialDialog.Builder(this)
                    .title(R.string.new_pictograma_title)
                    .content(R.string.new_pictograma_subtitle)
                    .widgetColorRes(R.color.colorPrimary)
                    .inputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)
                    .positiveText(R.string.new_pictograma_button)
                    .input(getResources().getString(R.string.new_pictograma_hint), "", (MaterialDialog dialog, CharSequence input) -> {
                        String nombreRaw = input.toString();
                        if (nombreRaw.length() <= 0) {
                            Toast
                                    .makeText(this, "Ingrese un nombre por favor", Toast.LENGTH_LONG)
                                    .show();
                            return;
                        }
                        String nombre = StringHelper.toImgFormat(nombreRaw);
                        pictogramaSaveHelper.setNombre(nombreRaw);
                        pictogramaSaveHelper.setFile(new File(Environment
                                .getExternalStoragePublicDirectory( "/" +
                                                getResources().getString(R.string.app_name) + "/" +
                                                this.coleccion.get_id()
                                ),
                                nombre));
                        Uri tempUri = Uri.fromFile(pictogramaSaveHelper.getFile());

                        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                        intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                        startActivityForResult(intent, 0);

                    }).show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detalle_coleccion_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_edit) {
            Intent i = new Intent(DetalleColeccion.this, EditarColeccion.class);
            i.putExtra("coleccion", this.coleccion);
            startActivity(i);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 0) {

            Log.d("FILE", resultCode + "");
            switch (resultCode) {
                case Activity.RESULT_OK:
                    Intent i = new Intent(this, RecordSound.class);
                    /*i.putExtra("idColeccion", this.coleccion.get_id());
                    i.putExtra("nombrePictograma", this.pictograma.getNombre());*/

                    Intent intent =
                            new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);

                    if (isAvailable(getApplicationContext(), intent)) {
                        startActivityForResult(intent, 1);
                    }
                    break;
                case Activity.RESULT_CANCELED:
                    Toast
                            .makeText(this, "Error", Toast.LENGTH_LONG )
                            .show();
                    break;
                default:

                    break;
            }
        } else if (requestCode == 1) {
            Log.d("AUDIO", resultCode + "");
            switch (resultCode) {
                case Activity.RESULT_OK:
                    Uri audioUri = data.getData();
                    try {
                        String filePath = getAudioFilePathFromUri(audioUri);
                        File soundFile = copyFile(filePath);
                        File imageFile = pictogramaSaveHelper.getFile();

                        ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
                        int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                ExifInterface.ORIENTATION_NORMAL);



                        getContentResolver().delete(audioUri, null, null);
                        (new File(filePath)).delete();

                        Log.d("SOUND_BEFORE", soundFile.length() + " B");
                        Log.d("IMAGE_BEFORE", imageFile.length() + " B");

                        File compressed = ImageHelper.compressImage(imageFile, 1024, 576);

                        Log.d("IMAGE_AFTER", compressed.length() + " B");

                        String nombre = pictogramaSaveHelper.getNombre();

                        if (imageFile.exists()) {
                            //Pictograma pictograma = new Pictograma();
                            detalleColeccionPresenter
                                    .executeCreatePictograma(coleccion.get_id(), compressed, soundFile, nombre);
                            Toast
                            .makeText(this, "Nombre: " + nombre, Toast.LENGTH_LONG )
                                    .show();
                        }


                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                    break;
                case Activity.RESULT_CANCELED:
                    Toast
                            .makeText(this, "Error Audio", Toast.LENGTH_LONG )
                            .show();
                    break;
                default:

                    break;
            }

            /*File file = pictogramaSaveHelper.getFile();

            //Log.d("FILE_BEFORE", file.length() + " B");

            File compressed = ImageHelper.compressImage(file, 1024, 576);

            //Log.d("FILE_AFTER", compressed.length() + " B");

            String nombre = pictogramaSaveHelper.getNombre();

            if (file.exists()) {
                //Pictograma pictograma = new Pictograma();
                detalleColeccionPresenter
                        .executeCreatePictograma(coleccion.get_id(), compressed, nombre);
                        *//*Toast
                                .makeText(this, "Nombre: " + nombre, Toast.LENGTH_LONG )
                                .show();*//*
            }*/
        }
    }

    public void checkEmptyList(List<Pictograma> pictogramas) {
        if (pictogramas == null || pictogramas.size() == 0) {
            empty_colecciones.setVisibility(TextView.VISIBLE);
        }else{
            empty_colecciones.setVisibility(TextView.GONE);
        }

    }

    @Override
    public void refreshPictogramas(Pictograma pictograma) {
        this.pictogramasListAdapter.push(pictograma);
    }

    private File copyFile(String fileName) throws IOException {

        String nombre = StringHelper.toAudioFormat(this.pictogramaSaveHelper.getNombre());
        String OUTPUT_FILE = Environment.getExternalStorageDirectory() + "/" +
                getResources().getString(R.string.app_name) + "/" +
                this.coleccion.get_id() + "/" + nombre;
        File outFile = new File(OUTPUT_FILE);
        InputStream in = new FileInputStream(new File(fileName));
        OutputStream out = new FileOutputStream(outFile);

        try {
            IOUtils.copy(in, out);
        } catch (IOException ioe) {
            Log.e("COPYING error", "IOException occurred.", ioe);
        } finally {
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(in);
        }

        return outFile;
    }

    private String getAudioFilePathFromUri(Uri uri) {
        Cursor cursor = getContentResolver()
                .query(uri, null, null, null, null);
        cursor.moveToFirst();
        int index = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA);
        return cursor.getString(index);
    }

    public static boolean isAvailable(Context ctx, Intent intent) {
        final PackageManager mgr = ctx.getPackageManager();
        List<ResolveInfo> list =
                mgr.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

}
