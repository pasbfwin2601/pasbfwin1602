package com.bit.creciendojuntos.activities.usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bit.creciendojuntos.R;
import com.bit.creciendojuntos.activities.MainActivity;
import com.bit.creciendojuntos.includes.MyToolbar;
import com.bit.creciendojuntos.models.Hijo;
import com.bit.creciendojuntos.providers.AuthProvider;

public class ConsultarEspecialidadesActivity extends AppCompatActivity {

    String clavePaciente;
    String nombrePaciente;
    String documentoPaciente;
    TextView mtxViewNombrPaciente;
    TextView mTxViewDocumentoPaciente;
    Button mBtnVacunas;
    AuthProvider mAuthProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_especialidades);
        MyToolbar.show(this,"Consultar Especialidades",true);
        mtxViewNombrPaciente = findViewById(R.id.txViewNombrPaciente);
        mTxViewDocumentoPaciente = findViewById(R.id.txViewDocumentoPaciente);
        clavePaciente = PantallaUsuarioActivity.devolverClave();
        nombrePaciente = PantallaUsuarioActivity.devolverNombre();
        documentoPaciente = PantallaUsuarioActivity.devolverDocumento();
        mtxViewNombrPaciente.setText(nombrePaciente);
        mTxViewDocumentoPaciente.setText(documentoPaciente);
        mBtnVacunas = findViewById(R.id.btnVacunas);
        mAuthProvider = new AuthProvider();

        mBtnVacunas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConsultarEspecialidadesActivity.this, VacunasActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacunas, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.esquema_vacuna) {
            Intent intent = new Intent(ConsultarEspecialidadesActivity.this, EsquemaVacunacionActivity.class);
            startActivity(intent);
        }

        if (item.getItemId() == R.id.action_logout) {
            logout();
        }

        return super.onOptionsItemSelected(item);
    }

    void logout(){
        mAuthProvider.logout();
        Intent intent = new Intent(ConsultarEspecialidadesActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}