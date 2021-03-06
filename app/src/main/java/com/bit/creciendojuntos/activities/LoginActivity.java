package com.bit.creciendojuntos.activities;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bit.creciendojuntos.R;
import com.bit.creciendojuntos.activities.medico.MedicoDAO;
import com.bit.creciendojuntos.activities.medico.PantallaMedicoActivity;
import com.bit.creciendojuntos.activities.usuario.PantallaUsuarioActivity;
import com.bit.creciendojuntos.activities.usuario.RegisterActivity;
import com.bit.creciendojuntos.activities.usuario.UsuarioDAO;
import com.bit.creciendojuntos.includes.MyToolbar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText mTextInputEmail;
    TextInputEditText mTextInputPassword;
    Button mButtonLogin;
    SharedPreferences mPref;
    AlertDialog mDialog;
    AlertDialog mDialogUsuario;
    AlertDialog mDialogMedico;

    FirebaseAuth mAuth;
    public DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        MyToolbar.show(this,"Ingresar",true);
        MedicoDAO.getInstance().subirFotoDefecto();
        UsuarioDAO.getInstance().subirFotoDefecto();

        mTextInputEmail = findViewById(R.id.textInputEmailLog);
        mTextInputPassword = findViewById(R.id.textInputPasswordLog);
        mButtonLogin = (Button) findViewById(R.id.btnLogin);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDialog = new SpotsDialog.Builder().setContext(LoginActivity.this).setMessage("Espere un momento...").build();
        mDialogUsuario = new SpotsDialog.Builder().setContext(LoginActivity.this).setMessage("Usuario NO registrado...").build();
        mDialogMedico = new SpotsDialog.Builder().setContext(LoginActivity.this).setMessage("Medico NO registrado...").build();
        mPref = getApplicationContext().getSharedPreferences("typeUser",MODE_PRIVATE);
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    //otra prueba de push
    private void login() {
        String email = mTextInputEmail.getText().toString();
        String password = mTextInputPassword.getText().toString();


        if (!email.isEmpty() && !password.isEmpty()) {
            if (password.length() >= 6) {

                mDialog.show();

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String user = mPref.getString("user","");
                            if (user.equals("usuario")) {
                                mDatabase.child("Users").child("usuario").child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()){
                                            Intent intent = new Intent(LoginActivity.this, PantallaUsuarioActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                        } else {
                                            mDialogUsuario.show();
                                            new Timer().schedule(new TimerTask() {
                                                public void run() {
                                                    mDialogUsuario.dismiss();
                                                }}, 2000);
                                            mAuth.signOut();
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                            else {
                                mDatabase.child("Users").child("medico").child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            Intent intent = new Intent(LoginActivity.this, PantallaMedicoActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                        } else {
                                            mDialogMedico.show();
                                            new Timer().schedule(new TimerTask() {
                                                public void run() {
                                                    mDialogMedico.dismiss();
                                                }}, 2000);
                                            mAuth.signOut();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                                }

                            }
                        else {
                              Toast.makeText(LoginActivity.this, "La contraseña o el email son incorrectos", Toast.LENGTH_SHORT).show();
                        }
                        mDialog.dismiss();
                    }
                });
            }
            else {
                Toast.makeText(this, "La contraseña debe tener mas de 6 caracteres", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "La contraseña y el email son obligatorios", Toast.LENGTH_SHORT).show();
        }
    }
}
