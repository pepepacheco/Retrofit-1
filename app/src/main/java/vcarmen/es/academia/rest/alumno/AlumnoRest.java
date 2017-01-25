package vcarmen.es.academia.rest.alumno;

import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
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
import vcarmen.es.academia.view.CustomAnimation;
import vcarmen.es.academia.view.adapters.CustomListAdapter;

public final class AlumnoRest {
    private final static RestAdapter restAdapter;
    private final static AlumnoService alumnoService;
    private static boolean deshacer = false;

    static {
        restAdapter = ApiClient.getAdapter();
        alumnoService = restAdapter.create(AlumnoService.class);
    }

    public static void getAlumnos(final ListView listAlumno, final ViewPager viewPager, final View view) {
        alumnoService.getAlumnos(new Callback<List<Alumno>>() {
            @Override
            public void success(List<Alumno> alumnos, Response response) {
                listAlumno.setAdapter(new CustomListAdapter(alumnos, R.layout.list_alumno, view.getContext()));
                ViewCompat.setNestedScrollingEnabled(listAlumno, true);
            }

            @Override
            public void failure(RetrofitError error) {
                errorRequest(view, error);
                ViewCompat.setNestedScrollingEnabled(viewPager, true);
            }
        });
    }

    public static void getAlumnoNombre(String nombre, final ListView listAlumno, final ViewPager viewPager, final View view) {
        alumnoService.getAlumnoNombre(nombre, new Callback<List<Alumno>>() {
            @Override
            public void success(List<Alumno> alumnos, Response response) {
                listAlumno.setAdapter(new CustomListAdapter(alumnos, R.layout.list_alumno, view.getContext()));
                ViewCompat.setNestedScrollingEnabled(listAlumno, true);
            }

            @Override
            public void failure(RetrofitError error) {
                errorRequest(view, error);
                ViewCompat.setNestedScrollingEnabled(viewPager, true);
            }
        });
    }

    public static void getAlumnoDni(String dni, final ListView listAlumno,final ViewPager viewPager, final View view) {
        alumnoService.getAlumnoDni(dni, new Callback<List<Alumno>>() {
            @Override
            public void success(List<Alumno> alumnos, Response response) {
                listAlumno.setAdapter(new CustomListAdapter(alumnos, R.layout.list_alumno, view.getContext()));
                ViewCompat.setNestedScrollingEnabled(listAlumno, true);
            }

            @Override
            public void failure(RetrofitError error) {
                errorRequest(view, error);
                ViewCompat.setNestedScrollingEnabled(viewPager, true);
            }
        });
    }

    public static void postAlumno(final View view, Alumno alumno) {
        alumnoService.postAlumno(alumno, new Callback<Alumno>() {
            @Override
            public void success(Alumno alumno, Response response) {}

            @Override
            public void failure(RetrofitError error) {
                errorRequest(view, error);
            }
        });
    }

    public static void putAlumno(final View view, Alumno alumno) {
        alumnoService.putAlumno(alumno, new Callback<Alumno>() {
            @Override
            public void success(Alumno alumno, Response response) {}

            @Override
            public void failure(RetrofitError error) {
                errorRequest(view, error);
            }
        });
    }

    public static void deleteAlumno(MenuItem item, final ListView listAlumno, final ViewPager viewPager, final View view) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Alumno alumno = (Alumno) listAlumno.getAdapter().getItem(info.position);

        final View row = CustomAnimation.getViewByPosition(info.position, listAlumno);

        CustomAnimation.startAnimationRight(view, listAlumno, row);

        Snackbar.make(view, "Alumno " + alumno.getNombre() + " " + alumno.getApellidos() + " Eliminado", Snackbar.LENGTH_LONG).setAction("Deshacer", new View.OnClickListener() {
            @Override
            public void onClick(View viewClick) {
                CustomAnimation.startAnimationLeft(view, listAlumno, row);
                deshacer = true;
            }
        }).setCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                super.onDismissed(snackbar, event);
                if (!deshacer) {
                    alumnoService.deleteAlumno(alumno.getId(), new Callback<List<Alumno>>() {
                        @Override
                        public void success(List<Alumno> alumnos, Response response) {
                            getAlumnos(listAlumno, viewPager, view);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            errorRequest(view, error);
                        }
                    });
                }
                else
                    deshacer = false;
            }
        }).show();
    }

    private static void errorRequest(View view, RetrofitError error) {
        if (error.getResponse() != null) {
            if (error.getResponse().getStatus() == 400 || error.getResponse().getStatus() == 404)
                Snackbar.make(view, error.getResponse().getReason(), Snackbar.LENGTH_LONG).show();
            else
                Snackbar.make(view, "Error Interno del servidor", Snackbar.LENGTH_LONG).show();
        }
        else
            Snackbar.make(view, "No ha sido posible conectar al servidor", Snackbar.LENGTH_LONG).show();

    }
}
