package com.example.moneysaver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText editCorreo;
    private TextInputEditText editPass;
    private Button botonLogin;
    private TextView textoRegistro;

    private String correo = "";
    private String password = "";

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editCorreo = (TextInputEditText) findViewById(R.id.editCorreo);
        editPass = (TextInputEditText) findViewById(R.id.editPassword);
        botonLogin = (Button) findViewById(R.id.botonLogin);
        textoRegistro = (TextView) findViewById(R.id.textoRegistro);
        auth = FirebaseAuth.getInstance();

        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                correo = editCorreo.getText().toString();
                password = editPass.getText().toString();

                if (!correo.isEmpty() && !password.isEmpty()){
                    loginUser();
                }
                else{
                    Snackbar.make(view, "Ingrese sus datos", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        textoRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, VistaRegistro.class);
                startActivity(intent);
            }
        });
    }
    private void loginUser(){
        auth.signInWithEmailAndPassword(correo, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent intent = new Intent(MainActivity.this, VistaNavigationPrincipal.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(MainActivity.this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (auth.getCurrentUser() != null){
            startActivity(new Intent(MainActivity.this, VistaNavigationPrincipal.class));
            finish();
        }
    }
}