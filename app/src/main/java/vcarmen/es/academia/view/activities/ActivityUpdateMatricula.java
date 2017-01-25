package vcarmen.es.academia.view.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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
import vcarmen.es.academia.model.Matricula;
import vcarmen.es.academia.model.MatriculaInsert;
import vcarmen.es.academia.rest.ApiClient;
import vcarmen.es.academia.rest.alumno.AlumnoService;
import vcarmen.es.academia.rest.asignatura.AsignaturaService;
import vcarmen.es.academia.rest.matricula.MatriculaRest;

public class ActivityUpdateMatricula extends AppCompatActivity  {
    private AppCompatSpinner alumno, asignatura;
    private TextInputEditText fechaInicio, fechaFin;
    private int posicionAlumnoSeleccionado = -1;
    private int posicionAsignaturaSeleccionada = -1;
    private Matricula matricula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_matricula);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Editar Matricula");

        alumno = (AppCompatSpinner) findViewById(R.id.spinnerAlumnoMatricula);
        asignatura = (AppCompatSpinner) findViewById(R.id.spinnerAsignaturaMatricula);
        matricula = getIntent().getParcelableExtra("matricula");

        generateSpinner();
        alumno.setEnabled(false);
        asignatura.setEnabled(false);

        final FloatingActionButton enviar = (FloatingActionButton) findViewById(R.id.buttonEnviarMatricula);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int posicionAlumno = alumno.getSelectedItemPosition();
                Alumno alumnoSeleccionado = (Alumno) alumno.getAdapter().getItem(posicionAlumno);

                int posicionAsignatura = asignatura.getSelectedItemPosition();
                Asignatura asignaturaSeleccionada = (Asignatura) asignatura.getAdapter().getItem(posicionAsignatura);

                MatriculaInsert matriculaInsert = new MatriculaInsert(alumnoSeleccionado.getId(), asignaturaSeleccionada.getId(), fechaInicio.getText().toString(), fechaFin.getText().toString());

                MatriculaRest.putMatricula(view, matriculaInsert);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("soy", 2);
                startActivity(intent);
            }
        });

        fechaInicio = (TextInputEditText) findViewById(R.id.inputFechaInicio);
        fechaInicio.setText(matricula.getFechaInicio());
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
        fechaFin.setText(matricula.getFechaFin());
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

    private void generateSpinner() {
        RestAdapter restAdapter = ApiClient.getAdapter();
        AlumnoService alumnoService = restAdapter.create(AlumnoService.class);

        final List<Alumno> nombreAlumno = new ArrayList<Alumno>();
        final List<Asignatura> nombreAsignatura = new ArrayList<Asignatura>();

        alumnoService.getAlumnos(new Callback<List<Alumno>>() {
            @Override
            public void success(List<Alumno> alumnos, Response response) {

                for (int i = 0; i < alumnos.size(); i++) {
                    nombreAlumno.add(alumnos.get(i));

                    if (alumnos.get(i).getId() == matricula.getAlumnoID()) {
                        posicionAlumnoSeleccionado = i;
                    }
                }
                alumno.setAdapter(new ArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_1, nombreAlumno));

                if (posicionAlumnoSeleccionado != -1)
                    alumno.setSelection(posicionAlumnoSeleccionado);
            }

            @Override
            public void failure(RetrofitError error) {
                MatriculaRest.errorRequest(getCurrentFocus(), error);
            }
        });

        AsignaturaService asignaturaService = restAdapter.create(AsignaturaService.class);
        asignaturaService.getAsignaturas(new Callback<List<Asignatura>>() {
            @Override
            public void success(List<Asignatura> asignaturas, Response response) {

                for (int i = 0; i < asignaturas.size(); i++) {
                    nombreAsignatura.add(asignaturas.get(i));

                    if (asignaturas.get(i).getId() == matricula.getAsignaturaID())
                        posicionAsignaturaSeleccionada = i;
                }
                asignatura.setAdapter(new ArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_1, nombreAsignatura));

                if (posicionAsignaturaSeleccionada != -1)
                    asignatura.setSelection(posicionAsignaturaSeleccionada);
            }

            @Override
            public void failure(RetrofitError error) {
                MatriculaRest.errorRequest(getCurrentFocus(), error);
            }
        });
    }
}
