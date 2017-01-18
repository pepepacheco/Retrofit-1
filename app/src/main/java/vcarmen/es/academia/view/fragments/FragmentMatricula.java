package vcarmen.es.academia.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import java.util.List;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import vcarmen.es.academia.R;
import vcarmen.es.academia.model.Matricula;
import vcarmen.es.academia.rest.ApiClient;
import vcarmen.es.academia.rest.matricula.MatriculaService;
import vcarmen.es.academia.view.adapters.CustomListAdapter;

public class FragmentMatricula extends Fragment {
    private ListView listMatricula;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_listview_matricula, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listMatricula = (ListView) getView().findViewById(R.id.listMatricula);

        RestAdapter restAdapter = ApiClient.getAdapter();

        MatriculaService matriculaService = restAdapter.create(MatriculaService.class);
        matriculaService.getMatriculas(new Callback<List<Matricula>>() {
            @Override
            public void success(List<Matricula> matriculas, Response response) {
                listMatricula.setAdapter(new CustomListAdapter(matriculas, R.layout.list_matricula, getContext()));
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
