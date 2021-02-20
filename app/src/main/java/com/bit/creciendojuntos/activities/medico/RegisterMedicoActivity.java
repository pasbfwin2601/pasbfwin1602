package com.bit.creciendojuntos.activities.medico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bit.creciendojuntos.R;
import com.bit.creciendojuntos.activities.medico.RegisterMedicoActivity;
import com.bit.creciendojuntos.activities.usuario.PantallaUsuarioActivity;
import com.bit.creciendojuntos.activities.usuario.RegisterActivity;
import com.bit.creciendojuntos.includes.MyToolbar;
import com.bit.creciendojuntos.models.Medico;
import com.bit.creciendojuntos.providers.AuthProvider;
import com.bit.creciendojuntos.providers.MedicoProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import dmax.dialog.SpotsDialog;

public class RegisterMedicoActivity extends AppCompatActivity {

    TextInputEditText mTextInputNombre;
    TextInputEditText mTextInputEmail;
    TextInputEditText mTextInputPassword;
    TextInputEditText mTextInputNroMatricula;
    TextInputEditText mTextInputTelefono;
    TextInputEditText mTextInputEspecialidad;
    Button mButtonRegister;
    // SharedPreferences mPref;

    AlertDialog mDialog;

    // FirebaseAuth mAuth;
    //DatabaseReference mDatabase;
    AuthProvider mAuthProvider;
    MedicoProvider mMedicoProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_medico);
        MyToolbar.show(this,"Registrar de Médico",true);

        mAuthProvider = new AuthProvider();
        mMedicoProvider = new MedicoProvider();

        //mPref = getApplicationContext().getSharedPreferences("typeUser",MODE_PRIVATE);

        mDialog = new SpotsDialog.Builder().setContext(RegisterMedicoActivity.this).setMessage("Espere un momento...").build();
        mTextInputNombre = findViewById(R.id.textInputNombreRegM);
        mTextInputEmail = findViewById(R.id.textInputCorreoRegM);
        mTextInputPassword = findViewById(R.id.textInputPasswordRegM);
        mTextInputNroMatricula = findViewById(R.id.textInputNroMatriculaRegM);
        mTextInputTelefono = findViewById(R.id.textInputTelefonoRegM);
        mTextInputEspecialidad = findViewById(R.id.textInputEspecialidadRegM);
        mButtonRegister = (Button) findViewById(R.id.btnRegister);

        //mAuth = FirebaseAuth.getInstance();
        //mDatabase = FirebaseDatabase.getInstance().getReference();

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clickRegister();
            }
        });

    }

    void clickRegister() {

        final String nombre = mTextInputNombre.getText().toString();
        final String email = mTextInputEmail.getText().toString();
        final String password = mTextInputPassword.getText().toString();
        final String nroMatricula = mTextInputNroMatricula.getText().toString();
        final String telefono = mTextInputTelefono.getText().toString();
        final String especialidad =  mTextInputEspecialidad.getText().toString();

        //Verifica que ningun campo este vacio
        if (!nombre.isEmpty() && !email.isEmpty() && !password.isEmpty() && !nroMatricula.isEmpty() && !telefono.isEmpty() && ! especialidad.isEmpty()) {
            //Verifica que el largo de la contrasenia sea mayor a 6 caracteres
            if (password.length() >= 6) {
                mDialog.show();
                register(nombre, email, password, nroMatricula, telefono, especialidad);
            } else {
                Toast.makeText(RegisterMedicoActivity.this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Debe ingresar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    void register(final String nombre, final String email, String password, final String nroMatricula, final String telefono, final String  especialidad) {
        mAuthProvider.register(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mDialog.hide();
                if (task.isSuccessful()){
                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Medico medico = new Medico(id, nombre, email, nroMatricula, telefono, especialidad);
                    create(medico);
                    //saveUser(id, nombre,email);
                } else {
                    Toast.makeText(RegisterMedicoActivity.this, "El médico ya existe o el registro falló", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    void create(Medico medico){
        mMedicoProvider.create(medico).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(RegisterMedicoActivity.this, PantallaMedicoActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    //Toast.makeText(RegisterMedicoActivity.this, "El registro del médico se realizó correctamente", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(RegisterMedicoActivity.this, "No se pudo crear el cliente", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}