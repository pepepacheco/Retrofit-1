package vcarmen.es.academia.view.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import vcarmen.es.academia.R;
import vcarmen.es.academia.model.Alumno;
import vcarmen.es.academia.model.Asignatura;
import vcarmen.es.academia.model.MatriculaInsert;
import vcarmen.es.academia.rest.ApiClient;
import vcarmen.es.academia.rest.alumno.AlumnoService;
import vcarmen.es.academia.rest.asignatura.AsignaturaService;
import vcarmen.es.academia.rest.matricula.MatriculaRest;

public class ActivityCreateMatricula extends AppCompatActivity  {
    private AppCompatSpinner alumno, asignatura;
    private TextInputEditText fechaInicio, fechaFin;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_matricula);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayoutInputMatricula);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Nueva Matricula");

        alumno = (AppCompatSpinner) findViewById(R.id.spinnerAlumnoMatricula);
        asignatura = (AppCompatSpinner) findViewById(R.id.spinnerAsignaturaMatricula);

        generateSpinner(coordinatorLayout);


        final FloatingActionButton enviar = (FloatingActionButton) findViewById(R.id.buttonEnviarMatricula);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int posicionAlumno = alumno.getSelectedItemPosition();
                Alumno alumnoSeleccionado = (Alumno) alumno.getAdapter().getItem(posicionAlumno);

                int posicionAsignatura = asignatura.getSelectedItemPosition();
                Asignatura asignaturaSeleccionada = (Asignatura) asignatura.getAdapter().getItem(posicionAsignatura);

                MatriculaInsert matricula = new MatriculaInsert(alumnoSeleccionado.getId(), asignaturaSeleccionada.getId(), fechaInicio.getText().toString(), fechaFin.getText().toString());

                MatriculaRest.postMatricula(view, matricula);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("soy", 2);
                startActivity(intent);
            }
        });

        fechaInicio = (TextInputEditText) findViewById(R.id.inputFechaInicio);
        fechaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                        Calendar date = Calendar.getInstance();
                        date.set(year, monthOfYear, dayOfMonth);

                        fechaInicio.setText(new SimpleDateFormat("yyyy-MM-dd").format(date.getTime()));
                        fechaInicio.setNextFocusDownId(enviar.getId());
                    }
                };

                Calendar localDate = Calendar.getInstance();

                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), listener, localDate.get(Calendar.YEAR), localDate.get(Calendar.MONTH), localDate.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        fechaFin = (TextInputEditText) findViewById(R.id.inputFechaFin);
        fechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                        Calendar date = Calendar.getInstance();
                        date.set(year, monthOfYear, dayOfMonth);

                        fechaFin.setText(new SimpleDateFormat("yyyy-MM-dd").format(date.getTime()));
                        fechaFin.setNextFocusDownId(enviar.getId());
                    }
                };

                Calendar localDate = Calendar.getInstance();

                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), listener, localDate.get(Calendar.YEAR), localDate.get(Calendar.MONTH), localDate.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private void generateSpinner(final View view) {
        RestAdapter restAdapter = ApiClient.getAdapter();
        AlumnoService alumnoService = restAdapter.create(AlumnoService.class);

        final List<Alumno> nombreAlumno = new ArrayList<>();
        final List<Asignatura> nombreAsignatura = new ArrayList<>();

        alumnoService.getAlumnos(new Callback<List<Alumno>>() {
            @Override
            public void success(List<Alumno> alumnos, Response response) {

                for (Alumno alumno : alumnos) {
                    nombreAlumno.add(alumno);
                }
                alumno.setAdapter(new ArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_1, nombreAlumno));

            }

            @Override
            public void failure(RetrofitError error) {
                MatriculaRest.errorRequest(view, error);
            }
        });

        AsignaturaService asignaturaService = restAdapter.create(AsignaturaService.class);
        asignaturaService.getAsignaturas(new Callback<List<Asignatura>>() {
            @Override
            public void success(List<Asignatura> asignaturas, Response response) {

                for (Asignatura asignatura : asignaturas) {
                    nombreAsignatura.add(asignatura);
                }
                asignatura.setAdapter(new ArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_1, nombreAsignatura));
            }

            @Override
            public void failure(RetrofitError error) {
                MatriculaRest.errorRequest(view, error);
            }
        });
    }
}
