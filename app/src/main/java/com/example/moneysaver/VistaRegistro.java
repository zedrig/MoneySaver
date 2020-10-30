package com.example.moneysaver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class VistaRegistro extends AppCompatActivity {

    private EditText editNombre;
    private EditText editCorreo;
    private EditText editPass1;
    private EditText editPass2;
    private Button botonRegistro;
    private TextView textoLogin;

    private String name = "";
    private String correo = "";
    private String pass1 = "";
    private String pass2 = "";

    FirebaseAuth auth;
    DatabaseReference dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_registro);

        auth = FirebaseAuth.getInstance();
        dataBase = FirebaseDatabase.getInstance().getReference();

        editNombre = (EditText) findViewById(R.id.editNombre);
        editCorreo = (EditText) findViewById(R.id.editCorreo);
        editPass1 = (EditText) findViewById(R.id.editPass1);
        editPass2 = (EditText) findViewById(R.id.editPass2);
        botonRegistro = (Button) findViewById(R.id.botonRegistro);
        textoLogin = (TextView) findViewById(R.id.textoLogin);
        final String getEmailId = editCorreo.getText().toString();

        botonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //InputMethodManager inputManager = (InputMethodManager)
                //        getSystemService(Context.INPUT_METHOD_SERVICE);

                //inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                //        InputMethodManager.HIDE_NOT_ALWAYS);

                name = editNombre.getText().toString();
                correo = editCorreo.getText().toString();
                pass1 = editPass1.getText().toString();
                pass2 = editPass2.getText().toString();

                if (!name.isEmpty() && !correo.isEmpty() && !pass1.isEmpty() && !pass2.isEmpty()){
                    if (pass1.length() >= 6 && pass2.length() >=6){

                        if (pass1.equals(pass2)){
                                registrarUsuario();
                        }
                        else {
                            Snackbar.make(view,"Las contraseñas no coinciden",Snackbar.LENGTH_SHORT).show();
                        }

                    }
                    else {
                        Snackbar.make(view, "La contraseña debe ser de al menos 6 caracteres", Snackbar.LENGTH_SHORT)
                                .show();
                    }
                }
                else {
                    Snackbar.make(view, "Debe llenar todos los campos", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        textoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //boolean isEmailValid(CharSequence email){
    //    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    //}

    private void registrarUsuario(){
        auth.createUserWithEmailAndPassword(correo, pass1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull final Task<AuthResult> task) {
                if (task.isSuccessful()){

                    Map<String, Object> map = new HashMap<>();
                    map.put("username", name);
                    map.put("correo", correo);
                    map.put("password", pass1);

                    String id = auth.getCurrentUser().getUid();

                    dataBase.child("Usuarios").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()){
                                Intent intent = new Intent(VistaRegistro.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Toast.makeText(VistaRegistro.this, "Registro erróneo", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else{
                    Toast.makeText(VistaRegistro.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}