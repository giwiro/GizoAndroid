package rest.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Gi Wah Davalos on 22/06/2016.
 */
public class Coleccion implements Serializable{
    private String _id;
    private String texto;
    private List<Pictograma> pictogramas;

    public Coleccion(String _id, String texto, List<Pictograma> pictogramas) {
        this._id = _id;
        this.texto = texto;
        this.pictogramas = pictogramas;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public List<Pictograma> getPictogramas() {
        return pictogramas;
    }

    public void setPictogramas(List<Pictograma> pictogramas) {
        this.pictogramas = pictogramas;
    }

    public void pushPictograma(Pictograma nuevoPictograma) {
        if (this.pictogramas != null) {
            this.pictogramas.add(nuevoPictograma);
        }
    }
}
