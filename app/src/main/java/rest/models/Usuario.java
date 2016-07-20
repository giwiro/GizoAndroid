package rest.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Gi Wah Davalos on 15/05/2016.
 */
public class Usuario implements Serializable{

    String _id;
    String nombres;
    String apellidos;
    String email;
    String password;
    List<Coleccion> colecciones;

    public Usuario(String _id, String nombres, String apellidos, String email, String password, List<Coleccion> colecciones) {
        this._id = _id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.password = password;
        this.colecciones = colecciones;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Coleccion> getColecciones() {
        return colecciones;
    }

    public void setColecciones(List<Coleccion> colecciones) {
        this.colecciones = colecciones;
    }

    @Override
    public String toString() {
        return
                this.nombres + " " + this.apellidos + " -> " + this.email;
    }
}
