package com.bit.creciendojuntos.activities.usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.bit.creciendojuntos.R;
import com.bit.creciendojuntos.activities.MainActivity;
import com.bit.creciendojuntos.activities.medico.PantallaMedicoActivity;
import com.bit.creciendojuntos.activities.medico.UpdateProfileMedicoActivity;
import com.bit.creciendojuntos.includes.MyToolbar;
import com.bit.creciendojuntos.providers.AuthProvider;

public class PantallaUsuarioActivity extends AppCompatActivity {

    Button mButtonLogoutU;
    AuthProvider mAuthProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_usuario);
        MyToolbar.show(this,"Menú del Usuario",false);

        mButtonLogoutU = (Button) findViewById(R.id.btnLogoutU);
        mAuthProvider = new AuthProvider();

        mButtonLogoutU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_usuario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            logout();
        }
        if (item.getItemId() == R.id.action_Perfil_Usuario) {
            ;Intent intent = new Intent(PantallaUsuarioActivity.this, UpdateProfileUsuarioActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    void logout(){
        mAuthProvider.logout();
        Intent intent = new Intent(PantallaUsuarioActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}