package utils;

import java.io.File;

import rest.models.Pictograma;

/**
 * Created by Gi Wah Davalos on 26/06/2016.
 */
public class PictogramaSaveHelper {

    private File file;
    private String nombre;
    private String soundFileName;

    public PictogramaSaveHelper(File file, String nombre, String soundFileName) {
        this.file = file;
        this.nombre = nombre;
        this.soundFileName = soundFileName;
    }

    public PictogramaSaveHelper (){

    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSoundFileName() {
        return soundFileName;
    }

    public void setSoundFileName(String soundFileName) {
        this.soundFileName = soundFileName;
    }
}
