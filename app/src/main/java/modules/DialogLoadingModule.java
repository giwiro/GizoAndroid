package modules;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.giwahdavalos.gizo.R;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Gi Wah Davalos on 24/06/2016.
 */

@Module
public class DialogLoadingModule {

    private Context ctx;
    private int style;
    private LayoutInflater inflater;

    public DialogLoadingModule(Context ctx, int style) {
        this.ctx = ctx;
        this.style = style;
        this.inflater = ((Activity) ctx).getLayoutInflater();
    }

    @Provides
    AlertDialog provideAlertLoading() {

        View dialogView
                = inflater.inflate(R.layout.dialog_loading, null);

        AlertDialog.Builder builder
                = new AlertDialog.Builder(this.ctx, this.style)
                .setOnKeyListener((dialog, keyCode, event) -> {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        return true;
                    }
                    return false;
                });

        TextView title = (TextView) dialogView.findViewById(R.id.dialog_title);
        title.setText("Cargando");
        /*builder.setTitle("Cargando");*/
        builder.setView(dialogView);

        AlertDialog dialog
                = builder.create();

        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }


}
