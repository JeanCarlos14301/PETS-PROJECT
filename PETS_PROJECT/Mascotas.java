
package PETS_PROJECT;

import javafx.beans.property.*;

public class Mascotas {
   
       private final IntegerProperty Id_mascotas;
    private final StringProperty Nombre_mascotas;
    private final StringProperty Especie;
    private final StringProperty Raza;
    private final StringProperty Fecha_nacimiento;
    private final IntegerProperty Id_propietario;
    
    
  
    public Mascotas(int Id_mascotas, String Nombre_mascotas, String Especie, String Raza, String Fecha_nacimiento, int Id_propietario) {
        this.Id_mascotas = new SimpleIntegerProperty(Id_mascotas);
        this.Nombre_mascotas = new SimpleStringProperty(Nombre_mascotas);
        this.Especie = new SimpleStringProperty(Especie);
        this.Raza = new SimpleStringProperty(Raza);
        this.Fecha_nacimiento = new SimpleStringProperty(Fecha_nacimiento);
        this.Id_propietario = new SimpleIntegerProperty(Id_propietario);
    }
   
    public int getIdMascota() {
        return Id_mascotas.get();
    }

    public IntegerProperty Id_mascotasProperty() {
        return Id_mascotas;
    }

    public String getNombre_mascotas() {
        return Nombre_mascotas.get();
    }

    public StringProperty Nombre_mascotasProperty() {
        return Nombre_mascotas;
    }

  public String getEspecie() {
        return Especie.get();
    }

    public StringProperty EspecieProperty() {
        return Especie;
    }
    
    public String getRaza() {
        return Raza.get();
    }

    public StringProperty RazaProperty() {
        return Raza;
    }
    
     public String getFecha_nacimiento() {
        return Fecha_nacimiento.get();
    }

    public StringProperty Fecha_nacimientoProperty() {
        return Fecha_nacimiento;
    }

    public int getId_Propietario() {
        return Id_propietario.get();
    }

    public IntegerProperty Id_propietarioProperty() {
        return Id_propietario;
    }

    
}
