package vcarmen.es.academia.view.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import vcarmen.es.academia.R;
import vcarmen.es.academia.model.Asignatura;
import vcarmen.es.academia.rest.asignatura.AsignaturaRest;

public class ActivityUpdateAsignatura extends AppCompatActivity {
    private Asignatura asignatura;
    private TextInputEditText nombre, ciclo, curso, horas;
    private Drawable clear;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_asignatura);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Editar Asignatura");

        clear = getDrawable(R.mipmap.ic_clear_black);
        clear.setTint(Color.GRAY);

        nombre = (TextInputEditText) findViewById(R.id.inputNombreAsignatura);
        nombre.setText(getIntent().getStringExtra("nombre"));
        nombre.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b)
                    nombre.setCompoundDrawablesWithIntrinsicBounds(null, null, clear, null);
                else
                    nombre.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
        });
        nombre.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (nombre.getCompoundDrawables()[DRAWABLE_RIGHT] != null) {
                        if (event.getRawX() >= (nombre.getRight() - nombre.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            nombre.getText().clear();
                            return true;
                        }
                    }
                }
                return false;
            }
        });

        ciclo = (TextInputEditText) findViewById(R.id.inputCicloAsigantura);
        ciclo.setText(getIntent().getStringExtra("ciclo"));
        ciclo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b)
                    ciclo.setCompoundDrawablesWithIntrinsicBounds(null, null, clear, null);
                else
                    ciclo.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
        });
        ciclo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (ciclo.getCompoundDrawables()[DRAWABLE_RIGHT] != null) {
                        if (event.getRawX() >= (ciclo.getRight() - ciclo.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            ciclo.getText().clear();
                            return true;
                        }
                    }
                }
                return false;
            }
        });

        curso = (TextInputEditText) findViewById(R.id.inputCursoAsignatura);
        curso.setText(getIntent().getStringExtra("curso"));
        curso.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b)
                    curso.setCompoundDrawablesWithIntrinsicBounds(null, null, clear, null);
                else
                    curso.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
        });
        curso.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (curso.getCompoundDrawables()[DRAWABLE_RIGHT] != null) {
                        if (event.getRawX() >= (curso.getRight() - curso.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            curso.getText().clear();
                            return true;
                        }
                    }
                }
                return false;
            }
        });

        horas = (TextInputEditText) findViewById(R.id.inputHorasAsignatura);
        horas.setText(String.valueOf(getIntent().getIntExtra("horas", 0)));
        horas.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b)
                    horas.setCompoundDrawablesWithIntrinsicBounds(null, null, clear, null);
                else
                    horas.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
        });
        horas.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (horas.getCompoundDrawables()[DRAWABLE_RIGHT] != null) {
                        if (event.getRawX() >= (horas.getRight() - horas.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            horas.getText().clear();
                            return true;
                        }
                    }
                }
                return false;
            }
        });

        FloatingActionButton enviar = (FloatingActionButton) findViewById(R.id.buttonEnviarAsignatura);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                asignatura = new Asignatura(null, nombre.getText().toString(), ciclo.getText().toString(), curso.getText().toString(), Integer.parseInt(horas.getText().toString()));
                AsignaturaRest.putAsigantura(view, asignatura);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("soy", 1);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
