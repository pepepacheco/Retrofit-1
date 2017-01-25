package vcarmen.es.academia.rest.matricula;

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
import vcarmen.es.academia.model.Matricula;
import vcarmen.es.academia.model.MatriculaInsert;
import vcarmen.es.academia.rest.ApiClient;
import vcarmen.es.academia.view.CustomAnimation;
import vcarmen.es.academia.view.adapters.CustomListAdapter;

public final class MatriculaRest {
    private static final RestAdapter restAdapter;
    private static final MatriculaService matriculaService;
    private static boolean deshacer = false;

    static {
        restAdapter = ApiClient.getAdapter();
        matriculaService = restAdapter.create(MatriculaService.class);
    }

    public static void getMatriculas(final ListView listMatricula, final ViewPager viewPager, final View view) {
        matriculaService.getMatriculas(new Callback<List<Matricula>>() {
            @Override
            public void success(List<Matricula> matriculas, Response response) {
                listMatricula.setAdapter(new CustomListAdapter(matriculas, R.layout.list_matricula, view.getContext()));
                ViewCompat.setNestedScrollingEnabled(listMatricula, true);
            }

            @Override
            public void failure(RetrofitError error) {
                errorRequest(view, error);
                ViewCompat.setNestedScrollingEnabled(viewPager, true);

            }
        });
    }

    public static void getMatriculaDniAlumno(String dni, final ListView listMatricula, final ViewPager viewPager, final View view) {
        matriculaService.getMatriculaDniAlumno(dni, new Callback<List<Matricula>>() {
            @Override
            public void success(List<Matricula> matriculas, Response response) {
                listMatricula.setAdapter(new CustomListAdapter(matriculas, R.layout.list_matricula, view.getContext()));
                ViewCompat.setNestedScrollingEnabled(listMatricula, true);
            }

            @Override
            public void failure(RetrofitError error) {
                errorRequest(view, error);
                ViewCompat.setNestedScrollingEnabled(viewPager, true);
            }
        });
    }

    public static void getMatriculaNombreAsignatura(String nombreAsignatura, final ListView listMatricula, final ViewPager viewPager, final View view) {
        matriculaService.getMatriculaNombreAsignatura(nombreAsignatura, new Callback<List<Matricula>>() {
            @Override
            public void success(List<Matricula> matriculas, Response response) {
                listMatricula.setAdapter(new CustomListAdapter(matriculas, R.layout.list_matricula, view.getContext()));
                ViewCompat.setNestedScrollingEnabled(listMatricula, true);
            }

            @Override
            public void failure(RetrofitError error) {
                errorRequest(view, error);
                ViewCompat.setNestedScrollingEnabled(viewPager, true);
            }
        });
    }

    public static void postMatricula(final View view, MatriculaInsert matricula) {
        matriculaService.postMatricula(matricula, new Callback<MatriculaInsert>() {
            @Override
            public void success(MatriculaInsert matriculaInsert, Response response) {}

            @Override
            public void failure(RetrofitError error) {
                errorRequest(view, error);
            }
        });
    }

    public static void putMatricula(final View view, MatriculaInsert matriculaInsert) {
        matriculaService.putMatricula(matriculaInsert, new Callback<MatriculaInsert>() {
            @Override
            public void success(MatriculaInsert matriculaInsert, Response response) {}

            @Override
            public void failure(RetrofitError error) {
                errorRequest(view, error );
            }
        });
    }

    public static void deleteMatricula(MenuItem item, final ListView listMatricula, final ViewPager viewPager, final View view) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Matricula matricula = (Matricula) listMatricula.getAdapter().getItem(info.position);

        final View row = CustomAnimation.getViewByPosition(info.position, listMatricula);

        CustomAnimation.startAnimationRight(view, listMatricula, row);

        Snackbar.make(view, "Matricula de " + matricula.getNombre() + " en " + matricula.getAsignatura(), Snackbar.LENGTH_LONG).setAction("Deshacer", new View.OnClickListener() {
            @Override
            public void onClick(View viewClick) {
                CustomAnimation.startAnimationLeft(view, listMatricula, row);
                deshacer = true;
            }
        }).setCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                super.onDismissed(snackbar, event);
                if (!deshacer) {
                    matriculaService.deleteMatricula(matricula.getAlumnoID(), matricula.getAsignaturaID(), new Callback<List<Matricula>>() {
                        @Override
                        public void success(List<Matricula> matriculas, Response response) {
                            getMatriculas(listMatricula, viewPager, view);
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

    public static void errorRequest(View view, RetrofitError error) {
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
