package com.bit.creciendojuntos.activities.medico;

import androidx.annotation.NonNull;

import com.bit.creciendojuntos.Logica.LMedico;

import com.bit.creciendojuntos.models.Medico;

import com.bit.creciendojuntos.utils.Constantes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MedicoDAO {

    private static MedicoDAO medicoDAO;
    private FirebaseDatabase database;
    private DatabaseReference referenceMedicos;

    public static MedicoDAO getInstance(){
        if (medicoDAO==null) medicoDAO = new MedicoDAO();
        return medicoDAO;
    }

    public MedicoDAO() {
        database = FirebaseDatabase.getInstance();
        referenceMedicos = database.getReference().child("Users").child("medico");;
    }

    public String getKeyMedico(){

        return FirebaseAuth.getInstance().getUid();
    }

    public void subirFotoDefecto(){
        referenceMedicos.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<LMedico> lMedicosLista = new ArrayList<>();
                for (DataSnapshot childDataSnapShot : dataSnapshot.getChildren()) {
                    Medico medico = childDataSnapShot.getValue(Medico.class);
                    LMedico lMedico = new LMedico(childDataSnapShot.getKey(),medico);
                    lMedicosLista.add(lMedico);
                }

                for (LMedico lMedico : lMedicosLista){
                    if (lMedico.getMedico().getImage()==null){
                        referenceMedicos.child(lMedico.getKey()).child("image").setValue(Constantes.URL_FOTO_DEFECTO_MEDICOS);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
