package com.bit.creciendojuntos.providers;

import com.bit.creciendojuntos.models.Consulta;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ConsultaProvider {

    CollectionReference mCollection;

    public ConsultaProvider() {
        mCollection = FirebaseFirestore.getInstance().collection("Consultas");
    }

    public Task<Void> create(Consulta consulta){
        return mCollection.document().set(consulta);
    }
}
