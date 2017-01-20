package vcarmen.es.academia.rest.asignatura;

import java.util.List;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import vcarmen.es.academia.model.Asignatura;

interface AsignaturaService {
    @GET("/asignatura")
    void getAsignaturas(Callback<List<Asignatura>> callback);

    @GET("/asignatura/nombre/{nombre}")
    void getAsignaturaNombre(@Path("nombre") String nombre, Callback<List<Asignatura>> callback);

    @GET("/asignatura/ciclo/{ciclo}")
    void getAsignaturaCiclo(@Path("ciclo") String ciclo, Callback<List<Asignatura>> callback);

    @POST("/asignatura")
    void postAsignatura(@Body Asignatura asignatura, Callback<Asignatura> callback);

    @PUT("/asignatura")
    void putAsignatura(@Body Asignatura asignatura, Callback<Asignatura> callback);

    @DELETE("/asignatura/{id}")
    void deleteAsignatura(@Path("id") Integer id, Callback<List<Asignatura>> callback);
}
