package com.bit.creciendojuntos.activities.usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bit.creciendojuntos.R;
import com.bit.creciendojuntos.includes.MyToolbar;
import com.bit.creciendojuntos.models.Vacuna;
import com.bit.creciendojuntos.providers.AuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.bit.creciendojuntos.activities.usuario.PantallaUsuarioActivity.documentoHijo;

public class VacunasActivity extends AppCompatActivity {

    String clavePaciente;
    String nombrePaciente;
    String documentoPaciente;
    TextView mtxViewNombrPaciente;
    TextView mTxViewDocumentoPaciente;
    AuthProvider mAuthProvider;
    ArrayList<Vacuna> vacunas = new ArrayList<>();
    RecyclerView listaVacunas;
    VacunaAdaptador adaptador;
    String id;
    String fechaDesde;
    String nombreVacuna;
    String proximaDosis;
    ArrayAdapter<Vacuna> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacunas);
        MyToolbar.show(this,"Historial de Vacunación",true);
        mtxViewNombrPaciente = findViewById(R.id.txViewNombrPaciente);
        mTxViewDocumentoPaciente = findViewById(R.id.txViewDocumentoPaciente);
        clavePaciente = PantallaUsuarioActivity.devolverClave();
        nombrePaciente = PantallaUsuarioActivity.devolverNombre();
        documentoPaciente = PantallaUsuarioActivity.devolverDocumento();
        mtxViewNombrPaciente.setText(nombrePaciente);
        mTxViewDocumentoPaciente.setText(documentoPaciente);
        mAuthProvider = new AuthProvider();
        listaVacunas = findViewById(R.id.rvVacunas);

        //LinearLayoutManager llm = new LinearLayoutManager(this);
       // llm.setOrientation(LinearLayoutManager.VERTICAL);
        GridLayoutManager glm = new GridLayoutManager(this,1);
        listaVacunas.setLayoutManager(glm);

        inicializarListaVacunas(documentoHijo);
        inicializarAdaptador();
    }

    public void inicializarAdaptador() {
        adaptador = new VacunaAdaptador(vacunas);
        listaVacunas.setAdapter(adaptador);

    }

    public void inicializarListaVacunas(String docuH){


        Query childRef = FirebaseDatabase.getInstance().getReference().child("Users").child("vacuna").orderByChild("documentoH").equalTo(docuH);

        childRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if (dataSnapshot.exists()) {
                  for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        id = ds.getKey();
                        fechaDesde = ds.child("fechaDesde").getValue().toString();
                        proximaDosis = ds.child("proximaDosis").getValue().toString();
                       nombreVacuna = ds.child("nombreVacuna").getValue().toString();
                       vacunas.add(new Vacuna(id,nombreVacuna,fechaDesde,proximaDosis));
                      //Log.e("nombre vacuna","nombre vacuna"+nombreVacuna);
                    }

                   arrayAdapter=new ArrayAdapter<>(VacunasActivity.this, android.R.layout.simple_dropdown_item_1line, vacunas);

                  for (int i = 0; i < arrayAdapter.getCount(); i++) {
                      Vacuna va2 = arrayAdapter.getItem(i);
                      String nombreVac1 = va2.getNombreVacuna();;
                      String fechaVac1 = va2.getFechaDesde();
                      String proximaV1 = va2.getProximaDosis();

                      Log.e("nombre vacuna va1","nombre vacunava1 "+nombreVac1+ " fecha desdeva1 "+fechaVac1+" proxima dosis va1 "+proximaV1);

                  }


               }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }





}