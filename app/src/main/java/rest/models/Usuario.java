package rest.models;

/**
 * Created by Gi Wah Davalos on 15/05/2016.
 */
public class Usuario {

    String _id;
    String nombres;
    String apellidos;
    String email;
    String password;

    public Usuario(String _id, String nombres, String apellidos, String email, String password) {
        this._id = _id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.password = password;
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

    @Override
    public String toString() {
        return
                this.nombres + " " + this.apellidos + " -> " + this.email;
    }
}
