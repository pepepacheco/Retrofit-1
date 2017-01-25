package vcarmen.es.academia.model;

import com.google.gson.annotations.SerializedName;

public class Asignatura {
    @SerializedName("ID")
    private Integer id;

    @SerializedName("Horas")
    private Integer horas;

    @SerializedName("Nombre")
    private String nombre;

    @SerializedName("Ciclo")
    private String ciclo;

    @SerializedName("Curso")
    private String curso;

    public Asignatura (Integer id, String nombre, String ciclo, String curso, Integer horas) {
        this.id = id;
        this.nombre = nombre;
        this.ciclo = ciclo;
        this.curso = curso;
        this.horas = horas;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCiclo() {
        return ciclo;
    }

    public String getCurso() {
        return curso;
    }

    public Integer getHoras() {
        return horas;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
