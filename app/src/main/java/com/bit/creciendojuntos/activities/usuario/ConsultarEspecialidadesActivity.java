package com.bit.creciendojuntos.activities.usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bit.creciendojuntos.R;
import com.bit.creciendojuntos.activities.MainActivity;
import com.bit.creciendojuntos.includes.MyToolbar;
import com.bit.creciendojuntos.models.Consulta;
import com.bit.creciendojuntos.models.Hijo;
import com.bit.creciendojuntos.providers.AuthProvider;
import com.bit.creciendojuntos.providers.ConsultaProvider;
import com.bit.creciendojuntos.providers.NotificationProvider;
import com.bit.creciendojuntos.providers.TokenProvider;
import com.bit.creciendojuntos.providers.UsuarioProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Date;

public class ConsultarEspecialidadesActivity extends AppCompatActivity {

    String clavePaciente;
    String nombrePaciente;
    String documentoPaciente;
    TextView mtxViewNombrPaciente;
    TextView mTxViewDocumentoPaciente;
    Button mBtnVacunas;
    Button mBtnOculista;
    Button mBtnDentista;
    Button mBtnPediatra;
    AuthProvider mAuthProvider;
    FloatingActionButton mfabConsulta;
    ConsultaProvider mConsultaProvider;
    NotificationProvider mNotificationProvider;
    TokenProvider mTokenProvider;
    String mExtraConsultaId;
    String mIdUser;
    UsuarioProvider mUsuarioProvider;


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
        mBtnOculista = findViewById(R.id.btnOculista);
        mBtnDentista = findViewById(R.id.btnDentista);
        mBtnPediatra = findViewById(R.id.btnPediatra);
        mAuthProvider = new AuthProvider();
        mfabConsulta = findViewById(R.id.fabConsulta);
        mConsultaProvider = new ConsultaProvider();
        mNotificationProvider = new NotificationProvider();
        mTokenProvider = new TokenProvider();
        mExtraConsultaId = getIntent().getStringExtra("id");


        mfabConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogComment();
            }
        });
        mBtnVacunas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConsultarEspecialidadesActivity.this, VacunasActivity.class);
                startActivity(intent);
            }
        });

        mBtnOculista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConsultarEspecialidadesActivity.this, OcularActivity.class);
                startActivity(intent);
            }
        });

        mBtnDentista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConsultarEspecialidadesActivity.this, DentistaActivity.class);
                startActivity(intent);
            }
        });

        mBtnPediatra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConsultarEspecialidadesActivity.this, PediatraActivity.class);
                startActivity(intent);
            }
        });

    }


    private void showDialogComment() {
        AlertDialog.Builder alert = new AlertDialog.Builder(ConsultarEspecialidadesActivity.this);
        alert.setTitle("Solicitar una Consulta");
        ///alert.setMessage("Selecciona Fecha:");
        final EditText editText = new EditText(ConsultarEspecialidadesActivity.this);
        editText.setHint("Ingrese: dd/mm/aaaa");

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(50,50,50,50);
        editText.setLayoutParams(params);
        RelativeLayout container = new RelativeLayout(ConsultarEspecialidadesActivity.this);
        RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        container.setLayoutParams(relativeParams);
        container.addView(editText);

        alert.setView(container);

        alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String value = editText.getText().toString();
                if (!value.isEmpty()) {
                    createConsulta(value);
                } else {
                    Toast.makeText(ConsultarEspecialidadesActivity.this, "Debe ingresar la fecha de la consulta", Toast.LENGTH_SHORT).show();
                }

            }
        });
        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alert.show();
    }

    private void createConsulta(final String value) {
        Consulta consulta = new Consulta();
        consulta.setId(mAuthProvider.getId());
        consulta.setFechaConsulta(value);
        consulta.setDocumentoH(documentoPaciente);
        mConsultaProvider.create(consulta).addOnCompleteListener((task) -> {
                if (task.isSuccessful()) {
                    sendNotificacion(value);
                    Toast.makeText(ConsultarEspecialidadesActivity.this, "La consulta se registro correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ConsultarEspecialidadesActivity.this, "No se pudo registrar la consulta", Toast.LENGTH_SHORT).show();
                }
                });
    }

    private void sendNotificacion(String consulta) {
       // mTokenProvider.getToken()
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