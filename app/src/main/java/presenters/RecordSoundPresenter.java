package presenters;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.MediaRecorder;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.example.giwahdavalos.gizo.R;
import com.github.piasy.rxandroidaudio.AudioRecorder;

import java.io.File;
import java.io.IOException;

import components.DaggerRecordSoundComponent;
import components.RecordSoundComponent;
import modules.DialogLoadingModule;
import modules.PreferencesEditorModule;
import modules.PreferencesModule;
import rest.models.Pictograma;
import ui.ghosts.RecordSoundView;
import utils.StringHelper;

/**
 * Created by Gi Wah Davalos on 10/07/2016.
 */
public class RecordSoundPresenter {

    private final SharedPreferences pref;
    private final SharedPreferences.Editor editor;
    private final AlertDialog alert_dialog;
    RecordSoundView recordSoundView;
    File file;
    RecordSoundComponent component;
    MediaRecorder mRecorder;


    public RecordSoundPresenter(RecordSoundView recordSoundView, String idColeccion, String nombreRaw) {
        this.recordSoundView = recordSoundView;

        String nombre = StringHelper.toAudioFormat(nombreRaw);

        component
                = DaggerRecordSoundComponent
                .builder()
                .preferencesModule(new PreferencesModule(((Activity) recordSoundView).getApplicationContext()))
                .preferencesEditorModule(new PreferencesEditorModule(((Activity) recordSoundView).getApplicationContext()))
                .dialogLoadingModule(new DialogLoadingModule(
                        ((Activity) recordSoundView),
                        R.style.BlueAlertDialogStyle))
                .build();

        pref = component.providePreferences();
        editor = component.provideSharedPrefsEditor();
        alert_dialog = component.provideAlertLoading();

        this.file = new File(Environment.getExternalStoragePublicDirectory("/" +
                                ((Activity)this.recordSoundView).getResources().getString(R.string.app_name) + "/" +
                                idColeccion
                ), nombre);

        /*if (file.exists()) {
            file.delete();
        }*/

    }

    public void startRecording() {
        Log.e("RECORDING", this.file.getAbsolutePath());
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(this.file.getAbsolutePath());
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e("RECORDING", "prepare() failed");
        }

        mRecorder.start();
    }

    public void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

    public void deleteFile() {
        file.delete();
    }

    public void stopRecordingAndAccept() {
        alert_dialog.show();
        alert_dialog.hide();
        this.recordSoundView.finish();
    }
}
