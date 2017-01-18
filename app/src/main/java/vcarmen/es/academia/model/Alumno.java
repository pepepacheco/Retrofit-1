package vcarmen.es.academia.model;

import com.google.gson.annotations.SerializedName;

public class Alumno {
    @SerializedName("ID")
    private Integer id;

    @SerializedName("DNI")
    private String dni;

    @SerializedName("Nombre")
    private String nombre;

    @SerializedName("Apellidos")
    private String apellidos;

    @SerializedName("email")
    private String email;

    public Alumno(Integer id, String dni, String nombre, String apellidos, String email) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Alumno{" +
                "id=" + id +
                ", dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
