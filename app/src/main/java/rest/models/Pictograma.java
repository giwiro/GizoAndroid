package rest.models;

import java.io.Serializable;

/**
 * Created by Gi Wah Davalos on 26/06/2016.
 */
public class Pictograma implements Serializable {

    private String _id;
    private String nombre;
    private String fileName;
    private String cloudPath;
    private String soundFileName;
    private String soundCloudPath;

    public Pictograma(String _id, String nombre, String fileName, String cloudPath, String soundFileName, String soundCloudPath) {
        this._id = _id;
        this.nombre = nombre;
        this.fileName = fileName;
        this.cloudPath = cloudPath;
        this.soundFileName = soundFileName;
        this.soundCloudPath = soundCloudPath;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCloudPath() {
        return cloudPath;
    }

    public void setCloudPath(String cloudPath) {
        this.cloudPath = cloudPath;
    }

    public String getSoundFileName() {
        return soundFileName;
    }

    public void setSoundFileName(String soundFileName) {
        this.soundFileName = soundFileName;
    }

    public String getSoundCloudPath() {
        return soundCloudPath;
    }

    public void setSoundCloudPath(String soundCloudPath) {
        this.soundCloudPath = soundCloudPath;
    }
}
