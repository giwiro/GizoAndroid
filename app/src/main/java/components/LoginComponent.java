package components;

import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Component;
import modules.PreferencesEditorModule;
import modules.PreferencesModule;
import rest.models.Usuario;

/**
 * Created by Gi Wah Davalos on 21/05/2016.
 */
@Singleton
@Component(modules = {PreferencesModule.class, PreferencesEditorModule.class})
public interface LoginComponent {
    SharedPreferences providePreferences();
    SharedPreferences.Editor provideSharedPrefsEditor();
}
