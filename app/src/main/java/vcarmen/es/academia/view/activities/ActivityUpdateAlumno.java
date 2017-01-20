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
import vcarmen.es.academia.model.Alumno;
import vcarmen.es.academia.rest.alumno.AlumnoRest;

public class ActivityUpdateAlumno extends AppCompatActivity {
    private Alumno alumno;
    private TextInputEditText dni, nombre, apellidos, eMail;
    private Drawable clear;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_alumno);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Editar Alumno");

        clear = getDrawable(R.mipmap.ic_clear_black);
        clear.setTint(Color.GRAY);

        dni = (TextInputEditText) findViewById(R.id.inputDni);
        dni.setText(getIntent().getStringExtra("dni"));
        dni.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b)
                    dni.setCompoundDrawablesWithIntrinsicBounds(null, null, clear, null);
                else
                    dni.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
        });
        dni.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (dni.getCompoundDrawables()[DRAWABLE_RIGHT] != null) {
                        if (event.getRawX() >= (dni.getRight() - dni.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            dni.getText().clear();
                            return true;
                        }
                    }
                }
                return false;
            }
        });

        nombre = (TextInputEditText) findViewById(R.id.inputNombre);
        nombre.setText(getIntent().getStringExtra("nombre"));
        nombre.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b)
                    nombre.setCompoundDrawablesWithIntrinsicBounds(null, null, clear, null);
                else
                    nombre.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);            }
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

        apellidos = (TextInputEditText) findViewById(R.id.inputApellidos);
        apellidos.setText(getIntent().getStringExtra("apellidos"));
        apellidos.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b)
                    apellidos.setCompoundDrawablesWithIntrinsicBounds(null, null, clear, null);
                else
                    apellidos.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
        });
        apellidos.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (apellidos.getCompoundDrawables()[DRAWABLE_RIGHT] != null) {
                        if (event.getRawX() >= (apellidos.getRight() - apellidos.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            apellidos.getText().clear();
                            return true;
                        }
                    }
                }
                return false;
            }
        });

        eMail = (TextInputEditText) findViewById(R.id.inputEmail);
        eMail.setText(getIntent().getStringExtra("eMail"));
        eMail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b)
                    eMail.setCompoundDrawablesWithIntrinsicBounds(null, null, clear, null);
                else
                    eMail.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
        });
        eMail.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (eMail.getCompoundDrawables()[DRAWABLE_RIGHT] != null) {
                        if (event.getRawX() >= (eMail.getRight() - eMail.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            eMail.getText().clear();
                            return true;
                        }
                    }
                }
                return false;
            }
        });

        FloatingActionButton enviar = (FloatingActionButton) findViewById(R.id.buttonEnviar);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alumno = new Alumno(getIntent().getIntExtra("id", 0), dni.getText().toString(), nombre.getText().toString(), apellidos.getText().toString(), eMail.getText().toString());
                AlumnoRest.putAlumno(view, alumno);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

}
