package vcarmen.es.academia.rest.alumno;

import java.util.List;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import vcarmen.es.academia.model.Alumno;

public interface AlumnoService {

    @GET("/alumno")
    void getAlumnos(Callback<List<Alumno>> callback);

    @GET("/alumno/{id}")
    void getAlumnoId(@Path("id") int id, Callback<List<Alumno>> callback);

    @GET("/alumno/nombre/{nombre}")
    void getAlumnoNombre(@Path("nombre") String nombre, Callback<List<Alumno>> callback);

    @GET("/alumno/dni/{dni}")
    void getAlumnoDni(@Path("dni") String dni, Callback<List<Alumno>> callback);

    @POST("/alumno")
    void postAlumno(@Body Alumno alumno, Callback<Alumno> callback);

    @PUT("/alumno")
    void putAlumno(@Body Alumno alumno, Callback<Alumno> callback);

    @DELETE("/alumno/{id}")
    void deleteAlumno(@Path("id") Integer id, Callback<List<Alumno>> callback);

}
