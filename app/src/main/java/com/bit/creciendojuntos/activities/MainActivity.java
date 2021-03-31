package com.bit.creciendojuntos.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bit.creciendojuntos.R;
import com.bit.creciendojuntos.activities.medico.PantallaMedicoActivity;
import com.bit.creciendojuntos.activities.usuario.PantallaUsuarioActivity;
import com.bit.creciendojuntos.includes.MyToolbar;
import com.bit.creciendojuntos.providers.AuthProvider;
import com.bit.creciendojuntos.providers.TokenProvider;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button mButtonMedico;
    private Button mButtonUsuario;
    TokenProvider mTokenProvider;
    AuthProvider mAuthProvider;

    SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPref = getApplicationContext().getSharedPreferences("typeUser",MODE_PRIVATE);
        final SharedPreferences.Editor editor = mPref.edit();

        MyToolbar.show(this,"             Carnét de Salud Digital",false);

        mButtonMedico = (Button) findViewById(R.id.btnMedico);
        mButtonUsuario = (Button) findViewById(R.id.btnUsuario);
        mTokenProvider = new TokenProvider();
        mAuthProvider = new AuthProvider();
        //createToken();
        
        mButtonMedico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("user", "medico");
                editor.apply();
                goToSelectAuth();
            }
        });

        mButtonUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("user", "usuario");
                editor.apply();
                goToSelectAuth();
            }
        });

    }
    

    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() !=null) {
            String user = mPref.getString("user", "");
            if (user.equals("usuario")) {
                Intent intent = new Intent(MainActivity.this, PantallaUsuarioActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            } else {
                Intent intent = new Intent(MainActivity.this, PantallaMedicoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }
    }

    private void goToSelectAuth() {
        Intent intent = new Intent(MainActivity.this, SelectOptionAuthActivity.class);
        startActivity(intent);
    }

    private void createToken(){
        //deberia ser getuid
        mTokenProvider.create(mAuthProvider.getId());
    }
}