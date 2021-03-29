package com.bit.creciendojuntos.activities.usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.bit.creciendojuntos.R;
import com.bit.creciendojuntos.includes.MyToolbar;
import com.bit.creciendojuntos.models.Dentista;
import com.bit.creciendojuntos.models.Ocular;
import com.bit.creciendojuntos.providers.AuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.bit.creciendojuntos.activities.usuario.PantallaUsuarioActivity.documentoHijo;

public class DentistaActivity extends AppCompatActivity {

    String nombrePacienteDE;
    String documentoPacienteDE;
    String nombreDentistaCV;
    String fechaDentistaCV;
    String id;
    String cariesCV;
    String extraccionesCV;
    String gingivitisCV;
    String tratamientoDentistaCV;
    String proximaVisitaCV;

    TextView mtxViewNombrePacienteDE;
    TextView mtxViewDocumentoPacienteDE;
    TextView mtxViewNombreDentista;
    TextView mtvPFechaDentistaCV;
    TextView mtvPCariesCV;
    TextView mtvPExtraccionesCV;
    TextView mtvPGingivitisCV;
    TextView mtvPTratamientoDentistaCV;
    TextView mtvPProximaDentistaCV;

    ArrayList<Dentista> dentistas = new ArrayList<>();
    RecyclerView listaDentistas;
    DentistaAdaptador adaptadorDentista;

    AuthProvider mAuthProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dentista);
        MyToolbar.show(this,"Historial Odontológico",true);

        mtxViewNombrePacienteDE = findViewById(R.id.txViewNombrePacienteDE);
        mtxViewDocumentoPacienteDE = findViewById(R.id.txViewDocumentoPacienteDE);
        mtxViewNombreDentista = findViewById(R.id.txViewNombreDentista);
        mtvPFechaDentistaCV = findViewById(R.id.tvPFechaDentistaCV);
        mtvPCariesCV = findViewById(R.id.tvPCariesCV);
        mtvPExtraccionesCV = findViewById(R.id.tvPExtraccionesCV);
        mtvPGingivitisCV = findViewById(R.id.tvPGingivitisCV);
        mtvPTratamientoDentistaCV = findViewById(R.id.tvPTratamientoDentistaCV);
        mtvPProximaDentistaCV = findViewById(R.id.tvPProximaDentistaCV);

        nombrePacienteDE = PantallaUsuarioActivity.devolverNombre();
        documentoPacienteDE = PantallaUsuarioActivity.devolverDocumento();
        mtxViewNombrePacienteDE.setText(nombrePacienteDE);
        mtxViewDocumentoPacienteDE.setText(documentoPacienteDE);
        mAuthProvider = new AuthProvider();
        listaDentistas = findViewById(R.id.rvDentista);

        GridLayoutManager glm = new GridLayoutManager(this,1);
        listaDentistas.setLayoutManager(glm);

        inicializarListaDentistas(documentoHijo);
    }

    public void inicializarAdaptador() {
        adaptadorDentista = new DentistaAdaptador(dentistas, R.layout.cardview_dentista);
        listaDentistas.setAdapter(adaptadorDentista);
    }

    public void inicializarListaDentistas(String docuH){

        Query childRef = FirebaseDatabase.getInstance().getReference().child("Users").child("dentista").orderByChild("documentoH").equalTo(docuH);

        childRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        id = ds.getKey();
                        nombreDentistaCV = ds.child("nombreDentista").getValue().toString();
                        cariesCV = ds.child("caries").getValue().toString();
                        extraccionesCV = ds.child("extracciones").getValue().toString();
                        fechaDentistaCV = ds.child("fechaDentista").getValue().toString();
                        gingivitisCV = ds.child("gingivitis").getValue().toString();
                        proximaVisitaCV = ds.child("proximaVisita").getValue().toString();
                        tratamientoDentistaCV = ds.child("tratamiento").getValue().toString();

                        dentistas.add(new Dentista(id, nombreDentistaCV, cariesCV, extraccionesCV, fechaDentistaCV, gingivitisCV, tratamientoDentistaCV, proximaVisitaCV));
                    }
                    inicializarAdaptador();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}