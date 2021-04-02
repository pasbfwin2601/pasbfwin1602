package com.bit.creciendojuntos.activities.usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    FloatingActionButton mfabLlamar;
    FloatingActionButton mfabEmail;
    FloatingActionButton mfabWhatapp;
    AuthProvider mAuthProvider;
    //FloatingActionButton mfabConsulta;
    FloatingActionButton mfabQRCode;
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
        mfabLlamar = findViewById(R.id.fabLlamar);
        mfabEmail = findViewById(R.id.fabEmail);
        mfabWhatapp = findViewById(R.id.fabWhatapp);
        mAuthProvider = new AuthProvider();
        mfabQRCode = findViewById(R.id.fabQRCode);
        //mfabConsulta = findViewById(R.id.fabConsulta);
        mConsultaProvider = new ConsultaProvider();
        mNotificationProvider = new NotificationProvider();
        mTokenProvider = new TokenProvider();
        mExtraConsultaId = getIntent().getStringExtra("id");

        mfabLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int permiso = ContextCompat.checkSelfPermission(ConsultarEspecialidadesActivity.this, Manifest.permission.CALL_PHONE);
                if (permiso != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(ConsultarEspecialidadesActivity.this, "No tiene permiso para realizar la llamada", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(ConsultarEspecialidadesActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 255);
                } else {
                    String numero = "+59898615671";
                    String inicio = "tel:"+numero;
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse(inicio));
                    startActivity(intent);
                }
            }
        });

        mfabQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConsultarEspecialidadesActivity.this, GeneradorqrActivity.class);
                startActivity(intent);
            }
        });

        /*mfabConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogComment();
            }
        });*/

        mfabWhatapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Intent sendIntent = new Intent(); sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Mensaje a Whatsapp desde Android");
                sendIntent.setType("text/plain");
                sendIntent.setPackage("com.whatsapp");
                startActivity(sendIntent);*/
                String celular = "+59898615671";
                String texto = "Mensaje a Whatsapp desde Android";
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_VIEW);
                String uri = "whatsapp://send?phone="+celular+"&text="+texto;
                sendIntent.setData(Uri.parse(uri));
                startActivity(sendIntent);
            }
        });

        mfabEmail.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("IntentReset")
            @Override
            public void onClick(View view) {
                String email = "federicoguillermomoreiratejera@gmail.com";
                String asunto = "Asunto del email";
                String texto = "Texto del email";
                Intent emailIntent = new Intent((Intent.ACTION_SEND));
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.putExtra(Intent.EXTRA_EMAIL, email);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, asunto);
                emailIntent.putExtra(Intent.EXTRA_TEXT, texto);
                //Intent.EXTRA_CC para enviar COPIA, Intent.EXTRA_SUBJECT para el asunto, Intent.EXTRA_TEXT texto mail
                emailIntent.setType("message/rfc822"); //muestra lista de app
                startActivity(Intent.createChooser(emailIntent, "Email "));
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
        final ImageView imageView = new ImageView(ConsultarEspecialidadesActivity.this);
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
        container.addView(imageView);

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