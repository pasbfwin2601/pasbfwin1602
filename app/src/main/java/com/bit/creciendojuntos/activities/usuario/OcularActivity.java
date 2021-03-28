package com.bit.creciendojuntos.activities.usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.bit.creciendojuntos.R;
import com.bit.creciendojuntos.includes.MyToolbar;
import com.bit.creciendojuntos.models.Ocular;
import com.bit.creciendojuntos.models.Vacuna;
import com.bit.creciendojuntos.providers.AuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.bit.creciendojuntos.activities.usuario.PantallaUsuarioActivity.documentoHijo;

public class OcularActivity extends AppCompatActivity {

    String nombrePacienteOC;
    String documentoPacienteOC;
    String nombreOculista;
    String id;
    String astigmatismoCV;
    String diagnosticoOcularCV;
    String fechaControlOcularCV;
    String graduacionODCV;
    String graduacionOICV;
    String hipermetropiaCV;
    String proximoControlOcularCV;
    String tratamientoOcularCV;

    TextView mTxViewNombrePacienteOC;
    TextView mTxViewDocumentoPacienteOC;
    TextView mTxViewNombreOculista;
    TextView mTvPAstigmatismoCV;
    TextView mTvPDiagnosticoOcularCV;
    TextView mTvPFechaControlOcularCV;
    TextView mTvPGraduacionODCV;
    TextView mTvPGraduacionOICV;
    TextView mTvPHipermetropiaCV;
    TextView mTvPProximoControlOcularCV;
    TextView mTvPTratamientoOcularCV;

    ArrayList<Ocular> oculares = new ArrayList<>();
    RecyclerView listaOculares;
    OcularAdaptador adaptadorOcular;

    AuthProvider mAuthProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocular);
        MyToolbar.show(this,"Historial Oftalmológico",true);

        mTxViewNombrePacienteOC = findViewById(R.id.txViewNombrePacienteOC);
        mTxViewDocumentoPacienteOC = findViewById(R.id.txViewDocumentoPacienteOC);
        mTxViewNombreOculista = findViewById(R.id.txViewNombreOculista);
        mTvPAstigmatismoCV = findViewById(R.id.tvPAstigmatismoCV);
        mTvPDiagnosticoOcularCV = findViewById(R.id.tvPDiagnosticoOcularCV);
        mTvPFechaControlOcularCV = findViewById(R.id.tvPFechaControlOcularCV);
        mTvPGraduacionODCV = findViewById(R.id.tvPGraduacionODCV);
        mTvPGraduacionOICV = findViewById(R.id.tvPGraduacionOICV);
        mTvPHipermetropiaCV = findViewById(R.id.tvPHipermetropiaCV);
        mTvPProximoControlOcularCV = findViewById(R.id.tvPProximoControlOcularCV);

        nombrePacienteOC = PantallaUsuarioActivity.devolverNombre();
        documentoPacienteOC = PantallaUsuarioActivity.devolverDocumento();
        mTxViewNombrePacienteOC.setText(nombrePacienteOC);
        mTxViewDocumentoPacienteOC.setText(documentoPacienteOC);
        mAuthProvider = new AuthProvider();
        listaOculares = findViewById(R.id.rvOculares);

        GridLayoutManager glm = new GridLayoutManager(this,1);
        listaOculares.setLayoutManager(glm);

        inicializarListaOculares(documentoHijo);
    }


    public void inicializarAdaptador() {
        adaptadorOcular = new OcularAdaptador(oculares, R.layout.cardview_ocular);
        listaOculares.setAdapter(adaptadorOcular);
    }

    public void inicializarListaOculares(String docuH){

        Query childRef = FirebaseDatabase.getInstance().getReference().child("Users").child("ocular").orderByChild("documentoH").equalTo(docuH);

        childRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        id = ds.getKey();
                        nombreOculista = ds.child("nombreOculista").getValue().toString();
                        astigmatismoCV = ds.child("astigmatismo").getValue().toString();
                        diagnosticoOcularCV = ds.child("diagnostico").getValue().toString();
                        fechaControlOcularCV = ds.child("fechaControl").getValue().toString();
                        graduacionODCV = ds.child("graduacionOD").getValue().toString();
                        graduacionOICV = ds.child("graduacionOI").getValue().toString();
                        hipermetropiaCV = ds.child("hipermetropia").getValue().toString();
                        proximoControlOcularCV = ds.child("proximoControl").getValue().toString();
                        tratamientoOcularCV = ds.child("tratamiento").getValue().toString();


                    oculares.add(new Ocular(id, nombreOculista, astigmatismoCV, diagnosticoOcularCV, fechaControlOcularCV, graduacionODCV, graduacionOICV, hipermetropiaCV,proximoControlOcularCV,tratamientoOcularCV ));
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