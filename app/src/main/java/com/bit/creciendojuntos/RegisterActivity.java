package com.bit.creciendojuntos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText mTextInputNombre;
    private TextInputEditText mTextInputEmail;
    private TextInputEditText mTextInputPassword;
    private Button mButtonRegister;
    private String nombre;
    private String email;
    private String password;


    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mTextInputNombre = (TextInputEditText) findViewById(R.id.textInputNombre);
        mTextInputEmail = (TextInputEditText) findViewById(R.id.textInputEmail);
        mTextInputPassword = (TextInputEditText) findViewById(R.id.textInputPassword);
        mButtonRegister = (Button) findViewById(R.id.btnRegister);

        nombre = mTextInputNombre.getText().toString();
        email = mTextInputEmail.getText().toString();
        password = mTextInputPassword.getText().toString();

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nombre.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    if (mTextInputPassword.length() >= 6) {
                        register();
                    } else {
                        Toast.makeText(RegisterActivity.this, "El password debe tener al menos 6 caracteres", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Debe completar todos los campos", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void register() {

    }
}