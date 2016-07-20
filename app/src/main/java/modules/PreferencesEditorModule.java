package modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Gi Wah Davalos on 22/05/2016.
 */

@Module
public class PreferencesEditorModule {

    private Context ctx;

    public PreferencesEditorModule(Context ctx) {
        this.ctx = ctx;
    }

    @Provides
    @Singleton
    SharedPreferences.Editor provideSharedPrefsEditor() {
        return PreferenceManager.getDefaultSharedPreferences(ctx).edit();
    }
}
