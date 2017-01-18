package vcarmen.es.academia.rest.alumno;

import android.support.design.widget.Snackbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.List;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import vcarmen.es.academia.R;
import vcarmen.es.academia.model.Alumno;
import vcarmen.es.academia.rest.ApiClient;
import vcarmen.es.academia.view.adapters.CustomListAdapter;

public final class AlumnoRest {
    private static RestAdapter restAdapter = ApiClient.getAdapter();
    private static AlumnoService alumnoService;

    static {
        restAdapter = ApiClient.getAdapter();
        alumnoService = restAdapter.create(AlumnoService.class);
    }

    public static void getAlumnos(final ListView listAlumno, final View view) {
        alumnoService.getAlumnos(new Callback<List<Alumno>>() {
            @Override
            public void success(List<Alumno> alumnos, Response response) {
                listAlumno.setAdapter(new CustomListAdapter(alumnos, R.layout.list_alumno, view.getContext()));
            }

            @Override
            public void failure(RetrofitError error) {
                errorRequest(view, error);
            }
        });
    }

    public static void getAlumnoNombre(String nombre, final ListView listAlumno, final View view) {
        alumnoService.getAlumnoNombre(nombre, new Callback<List<Alumno>>() {
            @Override
            public void success(List<Alumno> alumnos, Response response) {
                listAlumno.setAdapter(new CustomListAdapter(alumnos, R.layout.list_alumno, view.getContext()));
            }

            @Override
            public void failure(RetrofitError error) {
                errorRequest(view, error);
            }
        });
    }

    public static void getAlumnoDni(String dni, final ListView listAlumno, final View view) {
        alumnoService.getAlumnoDni(dni, new Callback<List<Alumno>>() {
            @Override
            public void success(List<Alumno> alumnos, Response response) {
                listAlumno.setAdapter(new CustomListAdapter(alumnos, R.layout.list_alumno, view.getContext()));
            }

            @Override
            public void failure(RetrofitError error) {
                errorRequest(view, error);
            }
        });
    }

    public static void postAlumno(final View view, Alumno alumno) {
        alumnoService.postAlumno(alumno, new Callback<Alumno>() {
            @Override
            public void success(Alumno alumno, Response response) {
            }

            @Override
            public void failure(RetrofitError error) {
                errorRequest(view, error);
            }
        });
    }

    public static void putAlumno(final View view, Alumno alumno) {
        alumnoService.putAlumno(alumno, new Callback<Alumno>() {
            @Override
            public void success(Alumno alumno, Response response) {
            }

            @Override
            public void failure(RetrofitError error) {
                errorRequest(view, error);
            }
        });
    }

    public static void deleteAlumno(MenuItem item, final ListView listAlumno, final View view) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Alumno alumno = (Alumno) listAlumno.getAdapter().getItem(info.position);

        alumnoService.deleteAlumno(alumno.getId(), new Callback<List<Alumno>>() {
            @Override
            public void success(List<Alumno> alumnos, Response response) {
                Snackbar.make(view, "Alumno " + alumno.getNombre() + " " + alumno.getApellidos() + " Eliminado", Snackbar.LENGTH_LONG).show();
                alumnoService.getAlumnos(new Callback<List<Alumno>>() {
                    @Override
                    public void success(List<Alumno> alumnos, Response response) {
                        listAlumno.setAdapter(new CustomListAdapter(alumnos, R.layout.list_alumno, view.getContext()));
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        errorRequest(view, error);
                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {
                errorRequest(view, error);
            }
        });
    }

    private static void errorRequest(View view, RetrofitError error) {
        if (error.getResponse().getStatus() == 400 || error.getResponse().getStatus() == 404)
            Snackbar.make(view, error.getResponse().getReason(), Snackbar.LENGTH_LONG).show();
        else
            Snackbar.make(view, "Error Interno del servidor", Snackbar.LENGTH_LONG).show();
    }
}
