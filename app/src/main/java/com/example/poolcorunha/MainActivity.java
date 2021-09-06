package com.example.poolcorunha;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    public final static String USUARIO ="usuario";
    EditText fecha;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        this.mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        ProgressBar spinner = (ProgressBar) findViewById(R.id.loading);
        spinner.setVisibility(View.GONE);
        if (firebaseUser != null) {
            updateUI(true);
        }
        xestionarEventos();

    }




    private void xestionarEventos() {
        Button btnAbrirBD = (Button) findViewById(R.id.boton);
        btnAbrirBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                EditText editTextBuscar = (EditText) findViewById(R.id.email);
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(editTextBuscar.getWindowToken(), 0);

                //Si email ou contrasinal estan vacios non iniciar sesion
                if ((((EditText) findViewById(R.id.email)).getText().toString().isEmpty())
                        ||(((EditText) findViewById(R.id.password)).getText().toString().isEmpty())) {
                    Toast.makeText(MainActivity.this, "Erro na autentificación.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    ProgressBar spinner = (ProgressBar) findViewById(R.id.loading);
                    spinner.setVisibility(View.VISIBLE);
                    iniciarSesion();
                }

            }
        });
        Button btnNuevoUsuario = (Button) findViewById(R.id.registro);
        btnNuevoUsuario.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ProgressBar spinner = (ProgressBar) findViewById(R.id.loading);
                spinner.setVisibility(View.GONE);
                nuevoUsuario();
            }
        });



    }


    void nuevoUsuario(){
        Toast.makeText(getApplicationContext(), "Formulario de rexistro de novo usuario.", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, NuevoUsuario.class);
        startActivity(intent);
        // No se finaliza la Activity en este caso.
    }



    void iniciarSesion(){
        String email = ((EditText) findViewById(R.id.email)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("DEPURACIÓN", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(true);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("DEPURACIÓN", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Erro na autentificación.",
                                    Toast.LENGTH_SHORT).show();
                            MensajeDialogo d= new MensajeDialogo();
                            d.setCancelable(false);
                            FragmentManager fm= getSupportFragmentManager();
                            d.show(fm,"errorLogin");

                        }

                    }
                });
    }

    void updateUI(boolean inicioSesion){
        //Notificación de la inserción.

        Toast.makeText(getApplicationContext(), "Sesión iniciada.", Toast.LENGTH_LONG).show();

        //Ir a la ventana de inicio de sesión y finalizar la Activity.
        Intent intent = new Intent(this, Principal.class);
        startActivity(intent);
        finish();
    }
}
