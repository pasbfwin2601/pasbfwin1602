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
    String nombrePaciente;
    String documentoPaciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generadorqr);
        MyToolbar.show(this,"Codigo QR del Paciente",true);

        mimgQR = findViewById(R.id.imgQR);
        mtxViewNombrPacienteQR = findViewById(R.id.txViewNombrPacienteQR);
        mtxViewDocumentoPacienteQR = findViewById(R.id.txViewDocumentoPacienteQR);
        nombrePaciente = PantallaUsuarioActivity.devolverNombre();
        documentoPaciente = PantallaUsuarioActivity.devolverDocumento();
        mtxViewNombrPacienteQR.setText(nombrePaciente);
        mtxViewDocumentoPacienteQR.setText(documentoPaciente);
        generarQR();;

    }

    public void generarQR(){
        String data = "Paciente: "+ nombrePaciente+ "\nDocumento: " +documentoPaciente;
        QRGEncoder  qrgEncoder = new QRGEncoder(data, null, QRGContents.Type.TEXT,500);
        try {
            Bitmap qrBits = qrgEncoder.encodeAsBitmap();
            mimgQR.setImageBitmap(qrBits);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}