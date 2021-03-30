package com.bit.creciendojuntos.activities.usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.bit.creciendojuntos.R;
import com.bit.creciendojuntos.includes.MyToolbar;
import com.bit.creciendojuntos.models.Dentista;
import com.bit.creciendojuntos.models.Pediatra;
import com.bit.creciendojuntos.providers.AuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.bit.creciendojuntos.activities.usuario.PantallaUsuarioActivity.documentoHijo;

//Comentario en clase PediatraActivity
public class PediatraActivity extends AppCompatActivity {

    String nombrePacientePD;
    String documentoPacientePD;
    String nombrePediatraCV;
    String fechaPediatraCV;
    String id;
    String presionCV;
    String pesoCV;
    String tallaCV;
    String imcCV;
    String rangoPesoCV;
    String aptitudCV;
    String proximoControlCV;

    TextView mtxViewNombrePacientePD;
    TextView mtxViewDocumentoPacientePD;
    TextView mtxViewNombrePediatra;
    TextView mtvPFechaPediatraCV;
    TextView mtvPPresionCV;
    TextView mtvPPesoCV;
    TextView mtvPTallaCV;
    TextView mtvPImcCV;
    TextView mtvPRangoPesoCV;
    TextView mtvPAptitudCV;
    TextView mtvPProximoControlCV;

    ArrayList<Pediatra> pediatras = new ArrayList<>();
    RecyclerView listaPediatras;
    PediatraAdaptador adaptadorPediatra;

    AuthProvider mAuthProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pediatra);
        MyToolbar.show(this, "Historial Pedíatrico", true);

        mtxViewNombrePacientePD = findViewById(R.id.txViewNombrePacientePD);
        mtxViewDocumentoPacientePD = findViewById(R.id.txViewDocumentoPacientePD);
        mtxViewNombrePediatra = findViewById(R.id.txViewNombrePediatra);
        mtvPFechaPediatraCV = findViewById(R.id.tvPFechaPediatraCV);
        mtvPPresionCV = findViewById(R.id.tvPPresionCV);
        mtvPPesoCV = findViewById(R.id.tvPPesoCV);
        mtvPTallaCV = findViewById(R.id.tvPTallaCV);
        mtvPImcCV = findViewById(R.id.tvPImcCV);
        mtvPRangoPesoCV = findViewById(R.id.tvPRangoPesoCV);
        mtvPAptitudCV = findViewById(R.id.tvPAptitudCV);
        mtvPProximoControlCV = findViewById(R.id.tvPProximoControlCV);

        nombrePacientePD = PantallaUsuarioActivity.devolverNombre();
        documentoPacientePD = PantallaUsuarioActivity.devolverDocumento();
        mtxViewNombrePacientePD.setText(nombrePacientePD);
        mtxViewDocumentoPacientePD.setText(documentoPacientePD);
        mAuthProvider = new AuthProvider();
        listaPediatras = findViewById(R.id.rvPediatra);

        GridLayoutManager glm = new GridLayoutManager(this,1);
        listaPediatras.setLayoutManager(glm);

        inicializarListaPediatras(documentoHijo);
    }

    public void inicializarAdaptador() {
        adaptadorPediatra = new PediatraAdaptador(pediatras, R.layout.cardview_pediatra);
        listaPediatras.setAdapter(adaptadorPediatra);
    }

    public void inicializarListaPediatras(String docuH){

        Query childRef = FirebaseDatabase.getInstance().getReference().child("Users").child("pediatra").orderByChild("documentoH").equalTo(docuH);

        childRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        id = ds.getKey();
                        nombrePediatraCV = ds.child("nombrePediatra").getValue().toString();
                        presionCV = ds.child("presion").getValue().toString();
                        pesoCV = ds.child("peso").getValue().toString();
                        fechaPediatraCV = ds.child("fechaPediatra").getValue().toString();
                        tallaCV = ds.child("talla").getValue().toString();
                        proximoControlCV = ds.child("proximoControl").getValue().toString();
                        imcCV = String.valueOf(Math.round((Double.parseDouble(pesoCV) / (Double.parseDouble(tallaCV) * Double.parseDouble(tallaCV)))*100.0)/100.0);
                        //imcCV = ds.child("imc").getValue().toString();
                        if ((Math.round((Double.parseDouble(pesoCV) * (Double.parseDouble(tallaCV) * Double.parseDouble(tallaCV)))*100.0)/100.0)<18.5){
                            rangoPesoCV = "Bajo Peso";
                        } else if ((Math.round((Double.parseDouble(pesoCV) * (Double.parseDouble(tallaCV) * Double.parseDouble(tallaCV)))*100.0)/100.0)>=18.5 && (Math.round((Double.parseDouble(pesoCV) * (Double.parseDouble(tallaCV) * Double.parseDouble(tallaCV)))*100.0)/100.0)<25){
                            rangoPesoCV = "Peso Normal";
                        } else if ((Math.round((Double.parseDouble(pesoCV) * (Double.parseDouble(tallaCV) * Double.parseDouble(tallaCV)))*100.0)/100.0)>=25 && (Math.round((Double.parseDouble(pesoCV) * (Double.parseDouble(tallaCV) * Double.parseDouble(tallaCV)))*100.0)/100.0)<30) {
                            rangoPesoCV = "Sobre Peso";
                        } else if ((Math.round((Double.parseDouble(pesoCV) * (Double.parseDouble(tallaCV) * Double.parseDouble(tallaCV)))*100.0)/100.0)>=30) {;
                            rangoPesoCV = "Obesidad";
                        }
                        //rangoPesoCV = String.valueOf(Math.round((Double.parseDouble(pesoCV) * (Double.parseDouble(tallaCV) * Double.parseDouble(tallaCV)))*100.0)/100.0);
                        aptitudCV = ds.child("aptitudFisica").getValue().toString();

                        pediatras.add(new Pediatra(id, nombrePediatraCV, fechaPediatraCV, presionCV, pesoCV, tallaCV, imcCV, rangoPesoCV,aptitudCV,proximoControlCV));
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