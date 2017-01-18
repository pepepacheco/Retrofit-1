package vcarmen.es.academia.model;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

public class Matricula {
    @SerializedName("Nombre")
    private String nombre;

    @SerializedName("Apellidos")
    private String apellidos;

    @SerializedName("Asignatura")
    private String asignatura;

    @SerializedName("fecha_inicio")
    private Date fechaInicio;

    @SerializedName("fecha_fin")
    private Date fechaFin;

    public Matricula(String nombre, String apellidos, String asigantura, Date fechaInicio, Date fechaFin) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.asignatura = asigantura;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getAsigantura() {
        return asignatura;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setAsigantura(String asigantura) {
        this.asignatura = asigantura;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public String toString() {
        return "Matricula{" +
                "nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", asigantura='" + asignatura + '\'' +
                ", fechaInicio=" + fechaInicio.toLocaleString() +
                ", fechaFin=" + fechaFin.toGMTString() +
                '}';
    }
}
