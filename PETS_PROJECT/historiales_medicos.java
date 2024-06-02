
package PETS_PROJECT;

import javafx.beans.property.*;

public class historiales_medicos {
    
    private final IntegerProperty Id_historial;
    private final StringProperty Fecha_consulta;
    private final StringProperty Descripcion;
    private final IntegerProperty Id_mascota;
    
    
    public historiales_medicos(int Id_historial, String Fecha_consulta, String Descripcion, int Id_mascota) {
        this.Id_historial = new SimpleIntegerProperty(Id_historial);
        this.Fecha_consulta = new SimpleStringProperty(Fecha_consulta);
        this.Descripcion = new SimpleStringProperty(Descripcion);
        this.Id_mascota = new SimpleIntegerProperty(Id_mascota);
    }
    
 public int getIdHistorial() {
        return Id_historial.get();
    }

    public IntegerProperty Id_HistorialProperty() {
        return Id_historial;
    }

    public String getFecha() {
        return Fecha_consulta.get();
    }

    public StringProperty Fecha_consultaProperty() {
        return Fecha_consulta;
    }

    public String getDescripcion() {
        return Descripcion.get();
    }

    public StringProperty DescripcionProperty() {
        return Descripcion;
    }
    
    public int getIdMascotas() {
        return Id_mascota.get();
    }

    public IntegerProperty Id_MascotasProperty() {
        return Id_mascota ;
    }
    
    }
