package com.example.moneysaver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ClaseAuxiliar extends AppCompatActivity {

    private Button logout;
    private TextView usr;
    private TextView email;
    private FirebaseAuth auth;
    private DatabaseReference dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clase_auxiliar);

        logout = (Button) findViewById(R.id.logout);
        auth = FirebaseAuth.getInstance();
        usr = (TextView) findViewById(R.id.usr);
        email = (TextView) findViewById(R.id.email);
        dataBase = FirebaseDatabase.getInstance().getReference();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(new Intent(ClaseAuxiliar.this, MainActivity.class));
                finish();
            }
        });

        getUserInfo();
    }

    private void getUserInfo(){
        String id = auth.getCurrentUser().getUid();
        dataBase.child("Usuarios").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String user = snapshot.child("username").getValue().toString();
                    String correo = snapshot.child("correo").getValue().toString();

                    usr.setText(user);
                    email.setText(correo);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}