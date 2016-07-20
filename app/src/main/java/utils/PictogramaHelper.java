package utils;

import android.content.SharedPreferences;

import java.util.Collection;
import java.util.List;

import rest.models.Coleccion;
import rest.models.Pictograma;

/**
 * Created by Gi Wah Davalos on 27/06/2016.
 */
public class PictogramaHelper {

    public static void pushPictograma
            (SharedPreferences.Editor editor, SharedPreferences pref, String idColeccion, Pictograma pictograma) {
        Coleccion coleccion = ColeccionHelper.getColeccionObject(pref, idColeccion);
        coleccion.pushPictograma(pictograma);
        ColeccionHelper.updateColeccion(editor, pref, coleccion);
    }

    public static int getPictogramaPosition
            (SharedPreferences pref, String idColeccion, String idPictograma){

        Coleccion coleccion = ColeccionHelper.getColeccionObject(pref, idColeccion);
        List<Pictograma> pictogramas = coleccion.getPictogramas();
        int pos = -1;

        if (pictogramas == null || pictogramas.size() == 0) {
            return pos;
        }

        for (Pictograma pic : pictogramas) {
            if (pic.get_id().equals(idPictograma)) {
                pos = pictogramas.indexOf(pic);
                break;
            }
        }

        return pos;
    }

    public static Pictograma getPictogramaObject
            (SharedPreferences pref, String idColeccion, String idPictograma){

        Coleccion coleccion = ColeccionHelper.getColeccionObject(pref, idColeccion);
        List<Pictograma> pictogramas = coleccion.getPictogramas();
        Pictograma rpta = null;

        if (pictogramas == null || pictogramas.size() == 0) {
            return null;
        }

        for (Pictograma pic : pictogramas) {
            if (pic.get_id().equals(idPictograma)) {
                rpta = pic;
                break;
            }
        }

        return rpta;
    }

    public static Pictograma editPictograma
            (SharedPreferences.Editor editor, SharedPreferences pref, String idColeccion, Pictograma pictograma) {

        List<Coleccion> colecciones = ColeccionHelper.getColecciones(pref);
        int coleccionPos = ColeccionHelper.getColeccionPosition(pref, idColeccion);
        int pictogramaPos = getPictogramaPosition(pref, idColeccion, pictograma.get_id());

        if (coleccionPos == -1 || pictogramaPos == -1) {
            return null;
        }

        colecciones.get(coleccionPos).getPictogramas().set(pictogramaPos, pictograma);
        return pictograma;


    }
}
