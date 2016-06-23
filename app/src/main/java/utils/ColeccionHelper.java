package utils;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import rest.models.Coleccion;

/**
 * Created by Gi Wah Davalos on 22/06/2016.
 */
public class ColeccionHelper {
    private static final String COLECCIONES_TAG = "colecciones";
    private static final String COLECCION_HELPER_TAG = "ColeccionHelper";

    public static void writeColecciones(SharedPreferences.Editor editor, List<Coleccion> colecciones) {
        Log.d(COLECCION_HELPER_TAG, "writing list of " + colecciones.size());
        Gson gson = new Gson();
        String serialized_colecciones = gson.toJson(colecciones);
        editor.putString(COLECCIONES_TAG, serialized_colecciones);
        editor.apply();
    }

    public static void pushColeccion(SharedPreferences.Editor editor, SharedPreferences pref, Coleccion coleccion) {
        Gson gson = new Gson();
        List<Coleccion> colecciones = getColecciones(pref);
        colecciones.add(coleccion);

        String serialized_colecciones = gson.toJson(colecciones);

        editor.putString(COLECCIONES_TAG, serialized_colecciones);
        editor.apply();
    }

    public static List<Coleccion> getColecciones(SharedPreferences pref) {
        Gson gson = new Gson();
        String serialized_colecciones = pref.getString(COLECCIONES_TAG, "[]");

        List<Coleccion> colecciones
                = gson.fromJson(serialized_colecciones, new TypeToken<ArrayList<Coleccion>>(){}.getType() );

        return colecciones;
    }
}
