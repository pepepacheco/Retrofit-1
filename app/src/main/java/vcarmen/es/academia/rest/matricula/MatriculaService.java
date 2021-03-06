package vcarmen.es.academia.rest.matricula;

import java.util.List;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;
import vcarmen.es.academia.model.Matricula;
import vcarmen.es.academia.model.MatriculaInsert;

public interface MatriculaService {
    @GET("/matricula")
    void getMatriculas(Callback<List<Matricula>> callback);

    @GET("/matricula/alumno/{dni}")
    void getMatriculaDniAlumno(@Path("dni") String dni, Callback<List<Matricula>> callback);

    @GET("/matricula/asignatura/{nombre}")
    void getMatriculaNombreAsignatura(@Path("nombre") String nombre, Callback<List<Matricula>> callback);

    @POST("/matricula")
    void postMatricula(@Body MatriculaInsert matricula, Callback<MatriculaInsert> callback);

    @PUT("/matricula")
    void putMatricula(@Body MatriculaInsert matricula, Callback<MatriculaInsert> callback);

    @DELETE("/matricula/delete/")
    void deleteMatricula(@Query("idAlumno") Integer idAlumno, @Query("idAsignatura") Integer idAsignatura, Callback<List<Matricula>> callback);
}
