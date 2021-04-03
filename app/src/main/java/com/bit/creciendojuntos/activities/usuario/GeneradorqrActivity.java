package com.bit.creciendojuntos.activities.usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bit.creciendojuntos.R;
import com.bit.creciendojuntos.includes.MyToolbar;
import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class GeneradorqrActivity extends AppCompatActivity {

    ImageView mimgQR;
    TextView mtxViewNombrPacienteQR;
    TextView mtxViewDocumentoPacienteQR;
    Button mbtnGeneradorQR;
    Button mbtnLectorQR;
    String nombrePaciente;
    String documentoPaciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generadorqr);
        MyToolbar.show(this,"Codigo QR del Paciente",false);

        mimgQR = findViewById(R.id.imgQR);
        mtxViewNombrPacienteQR = findViewById(R.id.txViewNombrPacienteQR);
        mtxViewDocumentoPacienteQR = findViewById(R.id.txViewDocumentoPacienteQR);
        mbtnGeneradorQR = findViewById(R.id.btnGeneradorQR);
        mbtnLectorQR = findViewById(R.id.btnLectorQR);
        nombrePaciente = PantallaUsuarioActivity.devolverNombre();
        documentoPaciente = PantallaUsuarioActivity.devolverDocumento();
        mtxViewNombrPacienteQR.setText(nombrePaciente);
        mtxViewDocumentoPacienteQR.setText(documentoPaciente);

        mbtnGeneradorQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = "Paciente: "+ nombrePaciente+ "\nDocumento: " +documentoPaciente;
                QRGEncoder  qrgEncoder = new QRGEncoder(data, null, QRGContents.Type.TEXT,500);
                try {
                    Bitmap qrBits = qrgEncoder.encodeAsBitmap();
                    mimgQR.setImageBitmap(qrBits);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });

       mbtnLectorQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GeneradorqrActivity.this, ScannerActivity.class);
                startActivity(intent);
            }
        });

    }
}