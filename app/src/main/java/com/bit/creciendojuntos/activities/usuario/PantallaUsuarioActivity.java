package com.bit.creciendojuntos.activities.usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bit.creciendojuntos.R;
import com.bit.creciendojuntos.activities.MainActivity;
import com.bit.creciendojuntos.providers.AuthProvider;

public class PantallaUsuarioActivity extends AppCompatActivity {

    Button mButtonLogoutU;
    AuthProvider mAuthProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_usuario);

        mButtonLogoutU = (Button) findViewById(R.id.btnLogoutU);
        mAuthProvider = new AuthProvider();

        mButtonLogoutU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuthProvider.logout();
                Intent intent = new Intent(PantallaUsuarioActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}