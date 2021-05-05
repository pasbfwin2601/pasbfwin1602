package com.bit.creciendojuntos.activities.usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bit.creciendojuntos.R;
import com.bit.creciendojuntos.activities.MainActivity;
import com.bit.creciendojuntos.includes.MyToolbar;
import com.bit.creciendojuntos.models.FCMBody;
import com.bit.creciendojuntos.models.FCMResponse;
import com.bit.creciendojuntos.models.Hijo;
import com.bit.creciendojuntos.providers.AuthProvider;
import com.bit.creciendojuntos.providers.NotificationProvider;
import com.bit.creciendojuntos.providers.TokenProvider;
import com.bit.creciendojuntos.providers.UsuarioProvider;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PantallaUsuarioActivity extends AppCompatActivity {

     static String claveHijo;
     static String nombreHijo;
     static String documentoHijo;
     Button mBtnBuscarDocH;
     Button mBtnConsultarEspecialidades;
     AuthProvider mAuthProvider;
     UsuarioProvider mUsuarioProvider;
     DatabaseReference mRootReference;
     TextInputEditText mTextInputDocHBuscar;
     TextView mTxViewNombreHijo;
     TextView mTxViewDocumentoHijo;
     TextView mTxViewDocumentoPadre;
     TextView mTxViewNombrePadre;
     String documentoPadre;
     String nombrePadre;
     String idPadre;
     TokenProvider mTokenProvider;
     private NotificationProvider mNotificationProvider;

     boolean encontroHijos;
     long cantHijos, hijoActual;
     Hijo hijoMio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_usuario);
        MyToolbar.show(this,"Menú del Usuario",false);
        //Subir foto por defecto al usuario
        UsuarioDAO.getInstance().subirFotoDefecto();
        mBtnBuscarDocH = findViewById(R.id.btnBuscarDocH);
        mBtnBuscarDocH.setVisibility(View.INVISIBLE);
        mBtnConsultarEspecialidades = findViewById(R.id.btnConsultarEspecialidades);
        mBtnConsultarEspecialidades.setVisibility(View.INVISIBLE);
        mAuthProvider = new AuthProvider();
        mUsuarioProvider = new UsuarioProvider();

        mTextInputDocHBuscar = findViewById(R.id.textInputDocHBuscar);
        mTxViewNombreHijo = findViewById(R.id.txViewNombreHijo);
        mTxViewDocumentoHijo = findViewById(R.id.txViewDocumentoHijo);
        mTxViewDocumentoPadre = findViewById(R.id.txViewDocumentoPadre);
        mTxViewNombrePadre =  findViewById(R.id.txViewNombrePadre);
        mRootReference = FirebaseDatabase.getInstance().getReference();
        getUsuarioInfo();
        mTokenProvider = new TokenProvider();
        mNotificationProvider = new NotificationProvider();

        mTextInputDocHBuscar.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    mBtnBuscarDocH.setVisibility(View.VISIBLE);
                } else {
                    mBtnBuscarDocH.setVisibility(View.INVISIBLE);
                }
            }
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

           public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });


        mBtnBuscarDocH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                documentoHijo = mTextInputDocHBuscar.getText().toString();
                getHijoInfo(documentoHijo,documentoPadre);
            }
        });

        mBtnConsultarEspecialidades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PantallaUsuarioActivity.this, ConsultarEspecialidadesActivity.class);
                startActivity(intent);
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
        if (item.getItemId() == R.id.action_info) {
            ;Intent intent = new Intent(PantallaUsuarioActivity.this, InfoUsuarioActivity.class);
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


       public void getUsuarioInfo() {
        mUsuarioProvider.getUsuario(mAuthProvider.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    idPadre = mAuthProvider.getId();
                    documentoPadre = dataSnapshot.child("documento").getValue().toString();
                    nombrePadre = dataSnapshot.child("nombre").getValue().toString();
                    mTxViewDocumentoPadre.setText(documentoPadre);
                    mTxViewNombrePadre.setText(nombrePadre);
                    generateToken(idPadre);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    private void getHijoInfo(String docuH,String docuP) {

        Query childRef = FirebaseDatabase.getInstance().getReference().child("Users").child("hijo").orderByChild("documentoH").equalTo(docuH);

        //DatabaseReference childRef = mRootReference.child("Users").child("hijo");
        childRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               encontroHijos = false;
               cantHijos = dataSnapshot.getChildrenCount();
               if (cantHijos==0){
                   Toast.makeText(PantallaUsuarioActivity.this, "Documento no encontrado",Toast.LENGTH_LONG).show();
                   mTxViewNombreHijo.setText("");
                   mTxViewDocumentoHijo.setText("");

               }
               
                hijoActual = 0;
                for(final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    mRootReference.child("Users").child("hijo").child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            hijoActual++;
                            hijoMio = snapshot.getValue(Hijo.class);
                            hijoMio.setClaveH(snapshot.getKey());
                            claveHijo = hijoMio.getClaveH();
                            nombreHijo = hijoMio.getNombreH();
                            String documentoHijoActual = hijoMio.getDocumentoH();
                            String documentoPadreActual = hijoMio.getDocumentoP();

                            if (documentoHijoActual.equals(docuH) && documentoPadreActual.equals(docuP)) {

                                mTxViewNombreHijo.setText(nombreHijo);
                                mTxViewDocumentoHijo.setText(documentoHijoActual);
                                mTxViewDocumentoPadre.setText(documentoPadreActual);
                                encontroHijos = true;
                                mBtnConsultarEspecialidades.setVisibility(View.VISIBLE);
                                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(mBtnConsultarEspecialidades.getWindowToken(), 0);
                            } else {

                                Toast.makeText(PantallaUsuarioActivity.this, "Documento no encontrado",Toast.LENGTH_SHORT).show();
                                mTxViewNombreHijo.setText("");
                                mTxViewDocumentoHijo.setText("");
                            }


                            if (hijoActual == cantHijos){
                                if (!encontroHijos){
                                    Toast.makeText(PantallaUsuarioActivity.this, "Documento no encontrado",Toast.LENGTH_SHORT).show();
                                    mTxViewNombreHijo.setText("");
                                    mTxViewDocumentoHijo.setText("");
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
              }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public static String devolverNombre(){
        return nombreHijo;
    }

    public static String devolverDocumento(){
        return documentoHijo;
    }

    public static String devolverClave(){
        return claveHijo;
    }

    private void generateToken(String idPadre){
        mTokenProvider.create(idPadre);
    }

    private void sendNotification(){
        mTokenProvider.getToken(idPadre).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String token = dataSnapshot.child("token").getValue().toString();
                Map<String, String> map = new HashMap<>();
                map.put("title", "SOLICITUD DE CONSULTA");
                map.put("body","Un usuario esta solicitando una consulta");
                FCMBody fcmBody = new FCMBody(token, "high","4500s",map);
                mNotificationProvider.sendNotification(fcmBody).enqueue(new Callback<FCMResponse>() {
                    @Override
                    public void onResponse(Call<FCMResponse> call, Response<FCMResponse> response) {
                        if (response.body() != null) {
                            if (response.body().getSuccess() == 1){
                                Toast.makeText(PantallaUsuarioActivity.this, "La reserva se ha ingresado correctamente", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(PantallaUsuarioActivity.this, "No se pudo ingresar la reserva", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<FCMResponse> call, Throwable t) {
                        Log.e("Error", "Error"+t.getMessage());
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
