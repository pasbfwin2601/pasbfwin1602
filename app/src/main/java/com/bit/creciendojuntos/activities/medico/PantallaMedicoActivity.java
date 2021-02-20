package com.bit.creciendojuntos.activities.medico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bit.creciendojuntos.R;
import com.bit.creciendojuntos.activities.MainActivity;
import com.bit.creciendojuntos.activities.usuario.PantallaUsuarioActivity;
import com.bit.creciendojuntos.providers.AuthProvider;

public class PantallaMedicoActivity extends AppCompatActivity {

    Button mButtonLogoutM;
    AuthProvider mAuthProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_medico);

        mButtonLogoutM = (Button) findViewById(R.id.btnLogoutM);
        mAuthProvider = new AuthProvider();

        mButtonLogoutM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuthProvider.logout();
                Intent intent = new Intent(PantallaMedicoActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}