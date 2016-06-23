package rest.models;

/**
 * Created by Gi Wah Davalos on 22/06/2016.
 */
public class Coleccion {
    String _id;
    String texto;

    public Coleccion(String _id, String texto) {
        this._id = _id;
        this.texto = texto;
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
}
