package com.bit.creciendojuntos.providers;

import com.bit.creciendojuntos.models.Medico;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MedicoProvider {

    DatabaseReference mDatabase;

    public MedicoProvider() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("medico");
    }

    public Task<Void> create(Medico medico){
        return mDatabase.child(medico.getId()).setValue(medico);
    }
}
