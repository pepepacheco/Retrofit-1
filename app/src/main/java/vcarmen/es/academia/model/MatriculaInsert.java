package vcarmen.es.academia.model;

import com.google.gson.annotations.SerializedName;

public class MatriculaInsert {
    @SerializedName("ID_alumno")
    private int idAlumno;

    @SerializedName("ID_asignatura")
    private int idAsignatura;

    @SerializedName("fecha_inicio")
    private String fechaInicio;

    @SerializedName("fecha_fin")
    private String fechaFin;

    public MatriculaInsert(int idAlumno, int idAsignatura, String fechaInicio, String fechaFin) {
        this.idAlumno = idAlumno;
        this.idAsignatura = idAsignatura;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public int getIdAsignatura() {
        return idAsignatura;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }
}
