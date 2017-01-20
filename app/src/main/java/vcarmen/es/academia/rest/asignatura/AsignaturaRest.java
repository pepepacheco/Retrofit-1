package vcarmen.es.academia.rest.asignatura;

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
import vcarmen.es.academia.model.Asignatura;
import vcarmen.es.academia.rest.ApiClient;
import vcarmen.es.academia.view.CustomAnimation;
import vcarmen.es.academia.view.adapters.CustomListAdapter;

public final class AsignaturaRest {
    private static final RestAdapter restAdapter;
    private static final AsignaturaService asignaturaService;
    private static boolean deshacer = false;

    static {
        restAdapter = ApiClient.getAdapter();
        asignaturaService = restAdapter.create(AsignaturaService.class);
    }

    public static void getAsignaturas(final ListView listAsignatura, final ViewPager viewPager, final View view) {
        asignaturaService.getAsignaturas(new Callback<List<Asignatura>>() {
            @Override
            public void success(List<Asignatura> asignaturas, Response response) {
                listAsignatura.setAdapter(new CustomListAdapter(asignaturas, R.layout.list_asignatura, view.getContext()));
                ViewCompat.setNestedScrollingEnabled(listAsignatura, true);
            }

            @Override
            public void failure(RetrofitError error) {
                errorRequest(view, error);
                ViewCompat.setNestedScrollingEnabled(viewPager, true);

            }
        });
    }

    public static void getAsignaturaNombre(String nombre, final ListView listAsignatura, final ViewPager viewPager, final View view) {
        asignaturaService.getAsignaturaNombre(nombre, new Callback<List<Asignatura>>() {
            @Override
            public void success(List<Asignatura> asignaturas, Response response) {
                listAsignatura.setAdapter(new CustomListAdapter(asignaturas, R.layout.list_asignatura, view.getContext()));
                ViewCompat.setNestedScrollingEnabled(listAsignatura, true);

            }

            @Override
            public void failure(RetrofitError error) {
                errorRequest(view, error);
                ViewCompat.setNestedScrollingEnabled(viewPager, true);

            }
        });
    }

    public static void getAsignaturaCiclo(String ciclo, final ListView listAsignatura, final ViewPager viewPager, final View view) {
        asignaturaService.getAsignaturaCiclo(ciclo, new Callback<List<Asignatura>>() {
            @Override
            public void success(List<Asignatura> asignaturas, Response response) {
                listAsignatura.setAdapter(new CustomListAdapter(asignaturas, R.layout.list_asignatura, view.getContext()));
                ViewCompat.setNestedScrollingEnabled(listAsignatura, true);

            }

            @Override
            public void failure(RetrofitError error) {
                errorRequest(view, error);
                ViewCompat.setNestedScrollingEnabled(viewPager, true);

            }
        });
    }

    public static void postAsigantura(final View view, Asignatura asignatura) {
        asignaturaService.postAsignatura(asignatura, new Callback<Asignatura>() {
            @Override
            public void success(Asignatura asignatura, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {
                errorRequest(view, error);
            }
        });
    }

    public static void putAsigantura(final View view, Asignatura asignatura) {
        asignaturaService.putAsignatura(asignatura, new Callback<Asignatura>() {
            @Override
            public void success(Asignatura asignatura, Response response) {
            }

            @Override
            public void failure(RetrofitError error) {
                errorRequest(view, error);
            }
        });
    }

    public static void deleteAsignatura(MenuItem item, final ListView listAsignatura, final ViewPager viewPager, final View view) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Asignatura asignatura = (Asignatura) listAsignatura.getAdapter().getItem(info.position);

        final View row = CustomAnimation.getViewByPosition(info.position, listAsignatura);

        CustomAnimation.startAnimationRight(view, listAsignatura, row);

        Snackbar.make(view, "Asignatura " + asignatura.getNombre() + " Eliminada", Snackbar.LENGTH_LONG).setAction("Deshacer", new View.OnClickListener() {
            @Override
            public void onClick(View viewClick) {
                CustomAnimation.startAnimationLeft(view, listAsignatura, row);
                deshacer = true;
            }
        }).setCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                super.onDismissed(snackbar, event);
                if (!deshacer) {
                    asignaturaService.deleteAsignatura(asignatura.getId(), new Callback<List<Asignatura>>() {
                        @Override
                        public void success(List<Asignatura> asignaturas, Response response) {
                            getAsignaturas(listAsignatura, viewPager, view);
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
