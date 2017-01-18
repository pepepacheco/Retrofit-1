package vcarmen.es.academia.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class ApiClient {
    public static final String URL = "35.163.178.255";
    public static final String PORT = "3000";
    private static RestAdapter restAdapter = null;

    public static RestAdapter getAdapter () {
        if (restAdapter == null) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd")
                    .create();

            restAdapter = new RestAdapter.Builder()
                    .setEndpoint("http://" + URL + ":" + PORT)
                    .setConverter(new GsonConverter(gson))
                    .build();
        }
        return restAdapter;
    }

    private ApiClient () {
    }
}
