package vcarmen.es.academia.view.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import vcarmen.es.academia.R;
import vcarmen.es.academia.model.Alumno;
import vcarmen.es.academia.rest.alumno.AlumnoRest;
import vcarmen.es.academia.view.activities.ActivityCreateAlumno;
import vcarmen.es.academia.view.activities.ActivityUpdateAlumno;

public class FragmentAlumno extends Fragment {
    private ListView listAlumno;
    private TabLayout tabLayout;
    private SwipeRefreshLayout refresh;
    private Bundle bundle = new Bundle();
    private ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_listview_alumno, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);

        listAlumno = (ListView) getView().findViewById(R.id.listAlumno);

        viewPager = (ViewPager) getActivity().findViewById(R.id.viewpager);

        AlumnoRest.getAlumnos(listAlumno, viewPager, getView());

        listAlumno.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String nombre = ((Alumno) adapterView.getAdapter().getItem(i)).getNombre();
                String apellidos = ((Alumno) adapterView.getAdapter().getItem(i)).getApellidos();
                Snackbar.make(view, nombre + " " + apellidos, Snackbar.LENGTH_SHORT).show();
            }
        });

        listAlumno.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                MenuInflater inflater = getActivity().getMenuInflater();
                inflater.inflate(R.menu.menu_context_alumno, contextMenu);
            }
        });

        refresh = (SwipeRefreshLayout) getView().findViewById(R.id.refresh_layoutAlumno);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                AlumnoRest.getAlumnos(listAlumno, viewPager, getView());
                if (refresh.isRefreshing()) {
                    refresh.setRefreshing(false);
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_alumno, menu);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setIconifiedByDefault(false);
        }

        SearchView.OnQueryTextListener searchListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (bundle.get("categoria") != null) {
                    if (bundle.get("categoria").toString().equals("dni"))
                        AlumnoRest.getAlumnoDni(query, listAlumno, viewPager, getView());
                    else if (bundle.get("categoria").toString().equals("nombre"))
                        AlumnoRest.getAlumnoNombre(query, listAlumno, viewPager, getView());
                    else
                        AlumnoRest.getAlumnoNombre(query, listAlumno, viewPager, getView());
                }
                else
                    AlumnoRest.getAlumnoNombre(query, listAlumno, viewPager, getView());

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        };

        searchView.setOnQueryTextListener(searchListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        runMenu(item, tabLayout.getSelectedTabPosition());
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.updateAlumno:
                updateActivity(item);

                break;
            case R.id.deleteAlumno:
                AlumnoRest.deleteAlumno(item, listAlumno, viewPager, getView());
        }
        return super.onContextItemSelected(item);
    }

    private void updateActivity(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        Alumno alumno = (Alumno) listAlumno.getAdapter().getItem(info.position);

        Intent intent = new Intent(getActivity(), ActivityUpdateAlumno.class);
        intent.putExtra("id", alumno.getId());
        intent.putExtra("dni", alumno.getDni());
        intent.putExtra("nombre", alumno.getNombre());
        intent.putExtra("apellidos", alumno.getApellidos());
        intent.putExtra("eMail", alumno.getEmail());

        startActivity(intent);
    }

    private void runMenu(MenuItem item, int selectedTabPosition) {
        if (selectedTabPosition == 0) { //Alumno
            switch (item.getItemId()) {
                case R.id.action_add:
                    startActivity(new Intent(getActivity(), ActivityCreateAlumno.class));
                    break;
                case R.id.categoriaDNI:
                    item.setChecked(true);
                    bundle.putString("categoria", "dni");
                    break;
                case R.id.categoriaNombre:
                    item.setChecked(true);
                    bundle.putString("categoria", "nombre");
                    break;
            }
        }
    }

}
