package components;

import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Component;
import modules.PreferencesEditorModule;
import modules.PreferencesModule;

/**
 * Created by Gi Wah Davalos on 25/05/2016.
 */


@Singleton
@Component(modules = {PreferencesEditorModule.class})
public interface RegistroComponent {
    SharedPreferences.Editor provideSharedPrefsEditor();
}
