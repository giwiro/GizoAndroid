package utils;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import rest.models.Usuario;

/**
 * Created by Gi Wah Davalos on 25/05/2016.
 */
public class SessionHelper {

    private final static String USUARIO_TAG = "usuario";

    public static void writeSession(SharedPreferences.Editor editor, Usuario usuario) {
        Gson gson = new Gson();
        String serialized_user = gson.toJson(usuario);

        editor.putString("usuario", serialized_user);
        editor.apply();
    }

    public static Usuario getSession(SharedPreferences pref) {
        Gson gson = new Gson();
        String serialized_user = pref.getString(USUARIO_TAG, "");

        Usuario usuario = gson.fromJson(serialized_user, Usuario.class);

        return usuario;
    }

    public static void finishSession(SharedPreferences.Editor editor) {
        editor.remove(USUARIO_TAG);
        editor.apply();
    }
}
