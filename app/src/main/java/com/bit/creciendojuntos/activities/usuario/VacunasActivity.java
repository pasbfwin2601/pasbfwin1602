package com.bit.creciendojuntos.activities.usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bit.creciendojuntos.R;
import com.bit.creciendojuntos.activities.MainActivity;
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
    VacunaAdaptador adaptadorVacuna;
    String id;
    String fechaDesde;
    String nombreVacuna;
    String proximaDosis;

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

    }

    public void inicializarAdaptador() {
        adaptadorVacuna = new VacunaAdaptador(vacunas, R.layout.cardview_vacuna);
        listaVacunas.setAdapter(adaptadorVacuna);
    }

    public void inicializarListaVacunas(String docuH){

        Query childRef = FirebaseDatabase.getInstance().getReference().child("Users").child("vacuna").orderByChild("documentoH").equalTo(docuH);

        childRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if (dataSnapshot.exists()) {
                  for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        id = ds.getKey();
                        fechaDesde = ds.child("fechaDesde").getValue().toString();
                        proximaDosis = ds.child("proximaDosis").getValue().toString();
                       nombreVacuna = ds.child("nombreVacuna").getValue().toString();
                       vacunas.add(new Vacuna(id,nombreVacuna,fechaDesde,proximaDosis));
                    }
                   inicializarAdaptador();
               }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
            Intent intent = new Intent(VacunasActivity.this, EsquemaVacunacionActivity.class);
            startActivity(intent);
        }

        if (item.getItemId() == R.id.action_logout) {
            logout();
        }

        return super.onOptionsItemSelected(item);
    }

    void logout(){
        mAuthProvider.logout();
        Intent intent = new Intent( VacunasActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}