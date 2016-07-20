package components;

import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Component;
import modules.PreferencesModule;

/**
 * Created by Gi Wah Davalos on 21/05/2016.
 */
@Singleton
@Component(modules = {PreferencesModule.class})
public interface ColeccionesComponent {
    SharedPreferences providePreferences();
}
