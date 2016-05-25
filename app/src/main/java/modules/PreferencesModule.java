package modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Gi Wah Davalos on 21/05/2016.
 */

@Module
public class PreferencesModule {

    Context ctx;

    public PreferencesModule(Context ctx) {
        this.ctx = ctx;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPrefs() {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

}
