package com.bit.creciendojuntos.activities.hijo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bit.creciendojuntos.R;
import com.bit.creciendojuntos.includes.MyToolbar;
import com.bit.creciendojuntos.models.Hijo;
import com.bit.creciendojuntos.providers.AuthProvider;
import com.bit.creciendojuntos.providers.HijoProvider;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class AltaHijoActivity extends AppCompatActivity {

    AlertDialog mDialog;
    AuthProvider mAuthProvider;
    HijoProvider mHijoProvider;
    TextInputEditText mtextInputNombreHijo;
    TextInputEditText mtextInputDocumentoHijo;
    //TextView mtxViewUsuarioSeleccionado;
    //Spinner mspinnerUsuario;
    Button mbtnRegistroHijoM;
    //String mUsuarioSeleccionado = "";
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_hijo);

        MyToolbar.show(this,"Alta de Hijo/a",true);
        mAuthProvider = new AuthProvider();
        mHijoProvider = new HijoProvider();

        mtextInputNombreHijo = findViewById(R.id.textInputNombreHijo);
        mtextInputDocumentoHijo = findViewById(R.id.textInputDocumentoHijo);
        //mtxViewUsuarioSeleccionado = findViewById(R.id.txViewUsuarioSeleccionado);
       // mspinnerUsuario = findViewById(R.id.spinnerUsuario);
        mbtnRegistroHijoM = findViewById(R.id.btnRegistroHijoM);

        mProgressDialog = new ProgressDialog(this);

        mbtnRegistroHijoM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clickRegister();
            }
        });
    }

    void clickRegister() {
        final String nombre =  mtextInputNombreHijo.getText().toString();
        final String documento =  mtextInputDocumentoHijo.getText().toString();

        if (!nombre.isEmpty() && !documento.isEmpty()) {




               //register(nombre, documento);
        } else {
            Toast.makeText(this, "Debe ingresar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    /*void register(final String nombre, final String  documento) {
        String pruebasa = HijoDAO.getKeyHijo().toString();
        Log.d("prueba", pruebasa);
        HijoDAO.getInstance().crearHijo();


        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Hijo hijo = new Hijo(id, nombre, documento);
        hijo.setId(id);
        hijo.setNombre(nombre);
        hijo.setDocumento(documento);
        mHijoProvider.update(hijo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //mProgressDialog.dismiss();
                Toast.makeText(AltaHijoActivity.this, "La informacion se actualizo correctamente", Toast.LENGTH_SHORT).show();
            }
        });
        //saveUser(id, nombre,email);
    }*/

}