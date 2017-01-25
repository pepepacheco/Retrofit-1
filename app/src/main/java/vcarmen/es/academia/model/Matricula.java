package vcarmen.es.academia.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Matricula implements Parcelable {
    @SerializedName("Alumno_ID")
    private int alumnoID;

    @SerializedName("Nombre")
    private String nombre;

    @SerializedName("Apellidos")
    private String apellidos;

    @SerializedName("Asignatura_ID")
    private int asignaturaID;

    @SerializedName("Asignatura")
    private String asignatura;

    @SerializedName("fecha_inicio")
    private Date fechaInicio;

    @SerializedName("fecha_fin")
    private Date fechaFin;

    public Matricula(int alumnoID, String nombre, String apellidos, int asignaturaID, String asigantura, Date fechaInicio, Date fechaFin) {
        this.alumnoID = alumnoID;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.asignaturaID = asignaturaID;
        this.asignatura = asigantura;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    private Matricula(Parcel in) {
        alumnoID = in.readInt();
        nombre = in.readString();
        apellidos = in.readString();
        asignaturaID = in.readInt();
        asignatura = in.readString();
        fechaInicio = (java.util.Date) in.readSerializable();
        fechaFin = (java.util.Date) in.readSerializable();
    }

    public static final Creator<Matricula> CREATOR = new Creator<Matricula>() {
        @Override
        public Matricula createFromParcel(Parcel in) {
            return new Matricula(in);
        }

        @Override
        public Matricula[] newArray(int size) {
            return new Matricula[size];
        }
    };

    public int getAlumnoID() {
        return alumnoID;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public int getAsignaturaID() {
        return asignaturaID;
    }

    public String getAsignatura() {
        return asignatura;
    }

    public String getFechaInicio() {
        return new SimpleDateFormat("yyy-MM-dd").format(fechaInicio);
    }

    public String getFechaFin() {
        return new SimpleDateFormat("yyy-MM-dd").format(fechaFin);
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(alumnoID);
        parcel.writeString(nombre);
        parcel.writeString(apellidos);
        parcel.writeInt(asignaturaID);
        parcel.writeString(asignatura);
        parcel.writeSerializable(fechaInicio);
        parcel.writeSerializable(fechaFin);
    }
}
