package PETS_PROJECT;

import javafx.beans.property.*;

public class Propietarios {
    private final IntegerProperty id_propietario;
    private final StringProperty Nombre_propietario;
    private final StringProperty telefono;

    public Propietarios(int id_propietario, String Nombre_propietario, String telefono) {
        this.id_propietario = new SimpleIntegerProperty(id_propietario);
        this.Nombre_propietario = new SimpleStringProperty(Nombre_propietario);
        this.telefono = new SimpleStringProperty(telefono);
    }

    public int getId_Propietario() {
        return id_propietario.get();
    }

    public IntegerProperty id_propietarioProperty() {
        return id_propietario;
    }

    public String getNombre() {
        return Nombre_propietario.get();
    }

    public StringProperty Nombre_propietarioProperty() {
        return Nombre_propietario;
    }

    public String getTelefono() {
        return telefono.get();
    }

    public StringProperty telefonoProperty() {
        return telefono;
    }
}