package com.bit.creciendojuntos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
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
    private Toolbar mToolbar;
    SharedPreferences mPref;


    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mPref = getApplicationContext().getSharedPreferences("typeUser",MODE_PRIVATE);
        String selectedUser = mPref.getString("user","");
        Toast.makeText(RegisterActivity.this, "El valor seleccionado fue "+ selectedUser, Toast.LENGTH_SHORT).show();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Registrar");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTextInputNombre = (TextInputEditText) findViewById(R.id.textInputNombre);
        mTextInputEmail = (TextInputEditText) findViewById(R.id.textInputEmail);
        mTextInputPassword = (TextInputEditText) findViewById(R.id.textInputPassword);
        mButtonRegister = (Button) findViewById(R.id.btnRegister);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

    }

    private void register() {
        final String nombre = mTextInputNombre.getText().toString();
        final String email = mTextInputEmail.getText().toString();
        final String password = mTextInputPassword.getText().toString();



    }
}