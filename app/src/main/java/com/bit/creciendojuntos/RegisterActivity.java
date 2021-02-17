package com.bit.creciendojuntos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bit.creciendojuntos.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText mTextInputNombre;
    TextInputEditText mTextInputEmail;
    TextInputEditText mTextInputPassword;
    Button mButtonRegister;
    Toolbar mToolbar;
    SharedPreferences mPref;


    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mPref = getApplicationContext().getSharedPreferences("typeUser",MODE_PRIVATE);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Registrar");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTextInputNombre = findViewById(R.id.textInputNombre);
        mTextInputEmail = findViewById(R.id.textInputEmail);
        mTextInputPassword = findViewById(R.id.textInputPassword);
        mButtonRegister = (Button) findViewById(R.id.btnRegister);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RegisterActivity.this, "pepito", Toast.LENGTH_SHORT).show();
               register();
            }
        });

    }

    void register() {

        final String nombre = mTextInputNombre.getText().toString();
        Log.d("nombre",nombre);
        final String email = mTextInputEmail.getText().toString();
        Log.d("prueba2","prueba2");
        final String password = mTextInputPassword.getText().toString();
        Log.d("prueba3","prueba3");

        if (!email.isEmpty() && !password.isEmpty()) {
       //if (!nombre.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
            if (password.length() >= 6) {
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            String id = mAuth.getCurrentUser().getUid();
                            Toast.makeText(RegisterActivity.this, "pepito", Toast.LENGTH_SHORT).show();
                            //saveUser(nombre,email);
                        } else {
                            Toast.makeText(RegisterActivity.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                Toast.makeText(RegisterActivity.this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Debe ingresar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    void saveUser(String nombre, String email) {
        String selectedUser = mPref.getString("user","");
        User user = new User();
        user.setEmail(email);
        user.setNombre(nombre);

        if (selectedUser.equals("medico")){
            mDatabase.child("Users").child("Medico").push().setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, "Falló el registro", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else if (selectedUser.equals("usuario")){
            mDatabase.child("Users").child("Usuario").push().setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Fallo el Registro", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}