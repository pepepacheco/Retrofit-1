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
import vcarmen.es.academia.model.Matricula;
import vcarmen.es.academia.rest.matricula.MatriculaRest;
import vcarmen.es.academia.view.activities.ActivityCreateMatricula;
import vcarmen.es.academia.view.activities.ActivityUpdateMatricula;

public class FragmentMatricula extends Fragment {
    private ListView listMatricula;
    private ViewPager viewPager;
    private SwipeRefreshLayout refresh;
    private TabLayout tabLayout;
    private Bundle bundle = new Bundle();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_listview_matricula, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tabLayout = (TabLayout)  getActivity().findViewById(R.id.tabs);

        listMatricula = (ListView) getView().findViewById(R.id.listMatricula);

        viewPager = (ViewPager) getActivity().findViewById(R.id.viewpager);

        MatriculaRest.getMatriculas(listMatricula, viewPager, getView());

        listMatricula.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String nombre = ((Matricula) listMatricula.getAdapter().getItem(i)).getNombre();
                Snackbar.make(view, nombre, Snackbar.LENGTH_SHORT).show();
            }
        });

        listMatricula.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                MenuInflater menuInflater = getActivity().getMenuInflater();
                menuInflater.inflate(R.menu.menu_context_matricula, contextMenu);
            }
        });

        refresh = (SwipeRefreshLayout) getView().findViewById(R.id.refresh_layoutMatricula);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MatriculaRest.getMatriculas(listMatricula, viewPager, getView());
                if (refresh.isRefreshing()) {
                    refresh.setRefreshing(false);
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_matricula, menu);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setIconifiedByDefault(false);

            SearchView.OnQueryTextListener searchListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    if (bundle.get("categoria") != null) {
                        if (bundle.get("categoria").toString().equals("dniAlumnoMatricula"))
                            MatriculaRest.getMatriculaDniAlumno(query, listMatricula, viewPager, getView());
                        else if (bundle.get("categoria").toString().equals("nombreAsignaturaMatricula"))
                            MatriculaRest.getMatriculaNombreAsignatura(query, listMatricula, viewPager, getView());
                        else
                            MatriculaRest.getMatriculaDniAlumno(query, listMatricula, viewPager, getView());
                    } else
                        MatriculaRest.getMatriculaDniAlumno(query, listMatricula, viewPager, getView());

                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return true;
                }
            };
            searchView.setOnQueryTextListener(searchListener);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        runMenu(item, tabLayout.getSelectedTabPosition());
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.updateMatricula:
                updateActivity(item);
                break;
            case R.id.deleteMatricula:
                MatriculaRest.deleteMatricula(item, listMatricula, viewPager, getView());
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void updateActivity(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        Matricula matricula = (Matricula) listMatricula.getAdapter().getItem(info.position);

        Intent intent = new Intent(getActivity(), ActivityUpdateMatricula.class);
        intent.putExtra("matricula", matricula);

        startActivity(intent);
    }

    private void runMenu(MenuItem item, int selectedTabPosition) {
        if (selectedTabPosition == 2) { //Matricula
            switch (item.getItemId()) {
                case R.id.action_add:
                    startActivity(new Intent(getActivity(), ActivityCreateMatricula.class));
                    break;
                case R.id.categoriaDNIAlumnoMatricula:
                    item.setChecked(true);
                    bundle.putString("categoria", "dniAlumnoMatricula");
                    break;
                case R.id.categoriaNombreAsignaturaMatricula:
                    item.setChecked(true);
                    bundle.putString("categoria", "nombreAsignaturaMatricula");
                    break;
            }
        }
    }
}
