package presenters;

import android.app.Activity;
import android.content.SharedPreferences;

import java.util.List;

import components.ColeccionesComponent;
import components.DaggerColeccionesComponent;
import modules.PreferencesModule;
import rest.models.Coleccion;
import ui.ghosts.ColeccionesView;
import utils.ColeccionHelper;

/**
 * Created by Gi Wah Davalos on 22/06/2016.
 */
public class ColeccionesPresenter {

    private final ColeccionesView coleccionesView;
    private final SharedPreferences pref;

    public ColeccionesPresenter(ColeccionesView coleccionesView) {
        this.coleccionesView = coleccionesView;
        ColeccionesComponent component
                = DaggerColeccionesComponent
                .builder()
                .preferencesModule(new PreferencesModule(((Activity) coleccionesView).getApplicationContext()))
                .build();

        pref = component.providePreferences();
    }

    public List<Coleccion> getColecciones() {
        return ColeccionHelper.getColecciones(pref);
    }
}
