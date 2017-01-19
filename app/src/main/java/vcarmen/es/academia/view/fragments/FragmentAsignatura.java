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
import vcarmen.es.academia.model.Asignatura;
import vcarmen.es.academia.rest.asignatura.AsignaturaRest;
import vcarmen.es.academia.view.activities.ActivityCreateAsignatura;
import vcarmen.es.academia.view.activities.ActivityUpdateAsignatura;

public class FragmentAsignatura extends Fragment {
    private ListView listAsignatura;
    private TabLayout tabLayout;
    private SwipeRefreshLayout refesh;
    private Bundle bundle = new Bundle();
    private ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_listview_asignatura, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tabLayout = (TabLayout)  getActivity().findViewById(R.id.tabs);

        listAsignatura = (ListView) getView().findViewById(R.id.listAsignatura);

        viewPager = (ViewPager) getActivity().findViewById(R.id.viewpager);

        AsignaturaRest.getAsignaturas(listAsignatura, viewPager, getView());

        listAsignatura.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String nombre = ((Asignatura) listAsignatura.getAdapter().getItem(i)).getNombre();
                Snackbar.make(view, nombre, Snackbar.LENGTH_LONG).show();
            }
        });

        listAsignatura.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                MenuInflater menuInflater = getActivity().getMenuInflater();
                menuInflater.inflate(R.menu.menu_context_asignatura, contextMenu);
            }
        });

        refesh = (SwipeRefreshLayout) getView().findViewById(R.id.refesh_layout);
        refesh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                AsignaturaRest.getAsignaturas(listAsignatura, viewPager, getView());
                if (refesh.isRefreshing()) {
                    refesh.setRefreshing(false);
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_asignatura, menu);

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
                    if (bundle.get("categoria").toString() == "nombre")
                        AsignaturaRest.getAsignaturaNombre(query, listAsignatura, viewPager, getView());
                    else if (bundle.get("categoria").toString() == "ciclo")
                        AsignaturaRest.getAsignaturaCiclo(query, listAsignatura, viewPager, getView());
                    else
                        AsignaturaRest.getAsignaturaNombre(query, listAsignatura, viewPager, getView());
                }
                else
                    AsignaturaRest.getAsignaturaNombre(query, listAsignatura, viewPager, getView());

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
            case R.id.updateAsignatura:
                updateActivity(item);

                break;
            case R.id.deleteAsignatura:
                AsignaturaRest.deleteAsignatura(item, listAsignatura, viewPager, getView());
        }
        return super.onContextItemSelected(item);
    }

    private void updateActivity(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        Asignatura asignatura = (Asignatura) listAsignatura.getAdapter().getItem(info.position);

        Intent intent = new Intent(getActivity(), ActivityUpdateAsignatura.class);
        intent.putExtra("id", asignatura.getId());
        intent.putExtra("nombre", asignatura.getNombre());
        intent.putExtra("ciclo", asignatura.getCiclo());
        intent.putExtra("curso", asignatura.getCurso());
        intent.putExtra("horas", asignatura.getHoras());

        startActivity(intent);
    }

    private void runMenu(MenuItem item, int selectedTabPosition) {
        if (selectedTabPosition == 1) { //Asignatura
            switch (item.getItemId()) {
                case R.id.action_add:
                    startActivity(new Intent(getActivity(), ActivityCreateAsignatura.class));
                    break;
                case R.id.categoriaNombreAsignatura:
                    item.setChecked(true);
                    bundle.putString("categoria", "nombre");
                    break;
                case R.id.categoriaCicloAsignatura:
                    item.setChecked(true);
                    bundle.putString("categoria", "ciclo");
                    break;
            }
        }
    }
}
