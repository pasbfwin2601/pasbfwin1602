package com.bit.creciendojuntos.activities.medico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.bit.creciendojuntos.R;
import com.bit.creciendojuntos.activities.usuario.InfoUsuarioActivity;
import com.bit.creciendojuntos.activities.MainActivity;
import com.bit.creciendojuntos.includes.MyToolbar;
import com.bit.creciendojuntos.providers.AuthProvider;

public class PantallaMedicoActivity extends AppCompatActivity {

    Button mButtonLogoutM;
    AuthProvider mAuthProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_medico);

        MyToolbar.show(this,"Menú del Médico",false);
        MedicoDAO.getInstance().subirFotoDefecto();

        // Boton para salir de la sesion de medico
        mButtonLogoutM = (Button) findViewById(R.id.btnLogoutM);
        mAuthProvider = new AuthProvider();

        mButtonLogoutM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_medico, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            logout();
        }
        if (item.getItemId() == R.id.action_Perfil) {
            ;Intent intent = new Intent(PantallaMedicoActivity.this, UpdateProfileMedicoActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.action_info) {
            ;Intent intent = new Intent(PantallaMedicoActivity.this, InfoUsuarioActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    void logout(){
        mAuthProvider.logout();
        Intent intent = new Intent(PantallaMedicoActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}