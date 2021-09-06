package com.example.poolcorunha;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class NuevoUsuario extends AppCompatActivity {
    EditText fecha;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);
        Intent intent = getIntent();
        String nuevoTitulo = getResources().getText(R.string.app_name)
                + ": Registro";
        setTitle(nuevoTitulo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.mAuth = FirebaseAuth.getInstance();
        ProgressBar spinner = (ProgressBar) findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
        xestionarEventos();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean resultado = false;
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                resultado = super.onOptionsItemSelected(item);

        }
        return resultado;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    void xestionarEventos() {
        Button btnNuevoUsuario = (Button) findViewById(R.id.registrar);
        btnNuevoUsuario.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ProgressBar spinner = (ProgressBar) findViewById(R.id.progressBar);
                spinner.setVisibility(View.VISIBLE);
                crearUsuario();
            }
        });
        fecha = (EditText) findViewById(R.id.fecha);
        fecha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDatePickerDialog();
            }
        });


    }


    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                final String selectedDate = day + " / " + (month + 1) + " / " + year;
                fecha.setText(selectedDate);
            }
        });
        newFragment.show(this.getSupportFragmentManager(), "datePicker");

    }

    void crearUsuario() {
        final String email = ((EditText) findViewById(R.id.nombre)).getText().toString();
        final String login = ((EditText) findViewById(R.id.usuario)).getText().toString();
        final String password = ((EditText) findViewById(R.id.password)).getText().toString();
        final String fecha = ((EditText) findViewById(R.id.fecha)).getText().toString();
        final String genero = ((Spinner) findViewById(R.id.spinner)).getSelectedItem().toString();

        EditText emailEd = (EditText) findViewById(R.id.nombre);
    	EditText passwEd = (EditText) findViewById(R.id.password);




        boolean validacion = true;

        if (login.equals("")) {
            validacion = false;
            Toast.makeText(NuevoUsuario.this, "Necesitas escoller un nome de usuario", Toast.LENGTH_LONG).show();

            ProgressBar spinner = (ProgressBar) findViewById(R.id.progressBar);
            spinner.setVisibility(View.GONE);
        }
        if (password.length() < 7) {
            passwEd.setError("Contrasinal moi curta");
            validacion = false;
            Toast.makeText(NuevoUsuario.this, "A contrasinal ten que ter como mínimo 7 caracteres.", Toast.LENGTH_LONG).show();

            ProgressBar spinner = (ProgressBar) findViewById(R.id.progressBar);
            spinner.setVisibility(View.GONE);
        }
        if (genero.equals("Selecciona o teu xénero")) {
            validacion = false;
            Toast.makeText(NuevoUsuario.this, "Debes escoller un xénero.", Toast.LENGTH_LONG).show();

            ProgressBar spinner = (ProgressBar) findViewById(R.id.progressBar);
            spinner.setVisibility(View.GONE);
        }
        if (!validarEmail(email)){
            validacion=false;
            emailEd.setError("Email non válido");
            Toast.makeText(NuevoUsuario.this, "O email non é correcto.", Toast.LENGTH_LONG).show();
            ProgressBar spinner = (ProgressBar) findViewById(R.id.progressBar);
            spinner.setVisibility(View.GONE);
        }
        if (validacion) {
            Task<AuthResult> tar = mAuth.createUserWithEmailAndPassword(email, password);
            tar.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    boolean insercion;
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("DEPURACIÓN", "createUserWithEmail:success");
                        insercion = true;
                        //Crear el objeto Usuario.
                        FirebaseUser fUsr = FirebaseAuth.getInstance().getCurrentUser();
                        Usuario usr = new Usuario(fUsr.getEmail(), password, login, 0, fecha, genero);
                        Log.d("DEPURACIÓN", usr.toString());
                        //Almacenar el objeto Usuario en la base de datos.
                        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference usersRef = rootRef.child("usuarios");
                        usersRef.child(fUsr.getUid()).setValue(usr);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("DEPURACIÓN", "createUserWithEmail:failure", task.getException());
                        insercion = false;
                        ProgressBar spinner = (ProgressBar) findViewById(R.id.progressBar);
                        spinner.setVisibility(View.GONE);
                    }
                    updateUI(insercion);
                }
            });
        }
    }

    void updateUI(boolean insercion) {
        //Notificación de la inserción.
        if (!insercion)
            Toast.makeText(getApplicationContext(), "Error no rexistro de usuario.", Toast.LENGTH_LONG).show();
        else {
            Toast.makeText(getApplicationContext(), "Novo usuario rexistrado.", Toast.LENGTH_LONG).show();
            //Ir a la ventana de inicio de sesión y finalizar la Activity.
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}
