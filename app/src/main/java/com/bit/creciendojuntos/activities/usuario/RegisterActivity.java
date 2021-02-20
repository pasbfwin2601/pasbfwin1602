package com.bit.creciendojuntos.activities.usuario;

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
import com.bit.creciendojuntos.includes.MyToolbar;
import com.bit.creciendojuntos.models.Usuario;
import com.bit.creciendojuntos.providers.AuthProvider;
import com.bit.creciendojuntos.providers.MedicoProvider;
import com.bit.creciendojuntos.providers.UsuarioProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import dmax.dialog.SpotsDialog;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText mTextInputNombre;
    TextInputEditText mTextInputEmail;
    TextInputEditText mTextInputPassword;
    TextInputEditText mTextInputDocumento;
    TextInputEditText mTextInputDomicilio;
    TextInputEditText mTextInputTelefono;
    Button mButtonRegister;


    AlertDialog mDialog;

   // FirebaseAuth mAuth;
    //DatabaseReference mDatabase;
    AuthProvider mAuthProvider;
    UsuarioProvider mUsuarioProvider;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        MyToolbar.show(this,"Registrar",true);

        mAuthProvider = new AuthProvider();
        mUsuarioProvider = new UsuarioProvider();



        mDialog = new SpotsDialog.Builder().setContext(RegisterActivity.this).setMessage("Espere un momento...").build();
        mTextInputNombre = findViewById(R.id.textInputNombreReg);
        mTextInputEmail = findViewById(R.id.textInputCorreoReg);
        mTextInputPassword = findViewById(R.id.textInputPasswordReg);
        mTextInputDocumento = findViewById(R.id.textInputDocumentoReg);
        mTextInputDomicilio = findViewById(R.id.textInputDomicilioReg);
        mTextInputTelefono = findViewById(R.id.textInputTelefonoReg);
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
        final String documento = mTextInputDocumento.getText().toString();
        final String domicilio = mTextInputDomicilio.getText().toString();
        final String telefono = mTextInputTelefono.getText().toString();

        //Verifica que los campos no esten vacios
        if (!nombre.isEmpty() && !email.isEmpty() && !password.isEmpty() && !documento.isEmpty() && !domicilio.isEmpty() && !telefono.isEmpty()) {
           //verifica que la contraseña tenga 6 o mas caracteres
            if (password.length() >= 6) {
                mDialog.show();
                register(nombre, email, password, documento, domicilio, telefono);
            } else {
                Toast.makeText(RegisterActivity.this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Debe ingresar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    void register(final String nombre, final String email, String password, final String documento, final String domicilio, final String telefono) {
        mAuthProvider.register(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mDialog.hide();
                if (task.isSuccessful()){
                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Usuario usuario = new Usuario(id, nombre, email, documento, domicilio, telefono);
                    create(usuario);
                    //saveUser(id, nombre,email);
                } else {
                    Toast.makeText(RegisterActivity.this, "El usuario ya existe o el registro falló", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    void create(Usuario usuario){
        mUsuarioProvider.create(usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(RegisterActivity.this, PantallaUsuarioActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    //Toast.makeText(RegisterActivity.this, "El registro del usuario se realizó correctamente", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(RegisterActivity.this, "No se pudo crear el cliente", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /*
    void saveUser(String id, String nombre, String email) {
        String selectedUser = mPref.getString("user","");
        User user = new User();
        user.setEmail(email);
        user.setNombre(nombre);

        if (selectedUser.equals("medico")){
            mDatabase.child("Users").child("medico").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
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
            mDatabase.child("Users").child("usuario").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
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
    } */
}