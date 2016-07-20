package components;

import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;

import javax.inject.Singleton;

import dagger.Component;
import modules.DialogLoadingModule;
import modules.PreferencesEditorModule;
import modules.PreferencesModule;

/**
 * Created by Gi Wah Davalos on 10/07/2016.
 */
@Singleton
@Component(modules = {PreferencesModule.class, PreferencesEditorModule.class, DialogLoadingModule.class})
public interface RecordSoundComponent {
    SharedPreferences providePreferences();
    SharedPreferences.Editor provideSharedPrefsEditor();
    AlertDialog provideAlertLoading();
}
