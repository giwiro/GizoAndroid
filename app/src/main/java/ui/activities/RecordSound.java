package ui.activities;

import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Chronometer;
import android.widget.RelativeLayout;

import com.example.giwahdavalos.gizo.R;

import org.firezenk.audiowaves.Visualizer;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import presenters.RecordSoundPresenter;
import ui.ghosts.OnRevealAnimationListener;
import ui.ghosts.RecordSoundView;
import utils.GUIUtils;
import utils.StringHelper;

public class RecordSound extends AppCompatActivity implements RecordSoundView{

    @BindView(R.id.main_container)
    RelativeLayout main_container;

    @BindView(R.id.delete)
    FloatingActionButton delete;

    @BindView(R.id.stop)
    FloatingActionButton stop;

    @BindView(R.id.accept)
    FloatingActionButton accept;

    @BindView(R.id.activity_contact_fab)
    FloatingActionButton mFab;

    @BindView(R.id.record_layout)
    RelativeLayout record_layout;

    @BindView(R.id.chronometer)
    Chronometer chronometer;

    @BindView(R.id.sound_wave)
    Visualizer sound_wave;

    RecordSoundPresenter recordSoundPresenter;
    File file;
    MediaRecorder mRecorder;
    String OUTPUT_FILE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_sound);

        ButterKnife.bind(this);

        String idColeccion = getIntent().getStringExtra("idColeccion");
        String nombrePictograma = getIntent().getStringExtra("nombrePictograma");

        this.recordSoundPresenter = new RecordSoundPresenter(this, idColeccion, nombrePictograma);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setupEnterAnimation();
            setupExitAnimation();
        }
        //this.recordSoundPresenter.startRecording();

        //main_container.bringChildToFront(stop);
        String nombre = StringHelper.toAudioFormat(nombrePictograma);
        OUTPUT_FILE = Environment.getExternalStorageDirectory() + "/" +
                getResources().getString(R.string.app_name) + "/" +
                idColeccion + nombre;

        File outFile = new File(OUTPUT_FILE);

        if (mRecorder != null) {
            mRecorder.release();
        }

        if (outFile.exists()) {
            outFile.delete();
        }

        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mRecorder.setOutputFile(OUTPUT_FILE);
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mRecorder.start();

        delete.setOnClickListener( v -> {
            chronometer.stop();
            sound_wave.stopListening();
            /*this.recordSoundPresenter.stopRecording();
            this.recordSoundPresenter.deleteFile();*/
            if (mRecorder != null) {
                mRecorder.stop();
            }
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                this.onBackPressed();
            } else {
                backPressed();
            }
        });

        stop.setOnClickListener( v -> {
            sound_wave.stopListening();
            chronometer.stop();
            stop.setEnabled(false);
            //this.recordSoundPresenter.stopRecording();

            if (mRecorder != null) {
                mRecorder.stop();
            }
        });

        accept.setOnClickListener( v -> {
            sound_wave.stopListening();
            chronometer.stop();
            //this.recordSoundPresenter.stopRecordingAndAccept();

        });
    }

    private void initViews() {
        new Handler(Looper.getMainLooper()).post(() -> {
            Animation animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
            animation.setDuration(300);
            record_layout.startAnimation(animation);
            record_layout.setVisibility(View.VISIBLE);

            chronometer.start();
            sound_wave.startListening();
        });
    }

    private void setupExitAnimation() {
        Fade fade = new Fade();
        getWindow().setReturnTransition(fade);
        fade.setDuration(300);
    }

    private void setupEnterAnimation () {
        Transition transition
                = TransitionInflater.from(this).inflateTransition(R.transition.changebounds_with_arcmotion);
        transition.setDuration(200);
        getWindow().setSharedElementEnterTransition(transition);
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow(main_container);
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });

    }

    private void animateRevealShow(final View viewRoot) {
        int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
        int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
        GUIUtils.animateRevealShow(this, main_container, mFab.getWidth() / 2, R.color.red,
                cx, cy, new OnRevealAnimationListener() {
                    @Override
                    public void onRevealHide() {

                    }

                    @Override
                    public void onRevealShow() {
                        initViews();
                    }
                });
    }


    private void backPressed() {
        super.onBackPressed();
    }
    
}
