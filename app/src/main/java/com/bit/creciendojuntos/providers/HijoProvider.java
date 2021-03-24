package com.bit.creciendojuntos.providers;

import com.bit.creciendojuntos.models.Hijo;
import com.bit.creciendojuntos.models.Medico;
import com.bit.creciendojuntos.models.Usuario;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class HijoProvider {

    DatabaseReference mDatabase;

    public HijoProvider() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("medico").child("hijo");
    }

 /*   public Task<Void> create(Hijo hijo){
        Map<String, Object> map = new HashMap<>();
        map.put("nombre", hijo.getNombreH());
        map.put("documento", hijo.getDocumentoH());
        //map.put("domicilio",hijo.getDomicilio());
        //map.put("telefono", hijo.getTelefono());
        return mDatabase.child(hijo.getId()).setValue(map);
    }

    public Task<Void> update(Hijo hijo){
        Map<String, Object> map = new HashMap<>();
        map.put("id", hijo.getId());
        map.put("nombreH", hijo.getNombreH());
        map.put("documentoH", hijo.getDocumentoH());
        return mDatabase.child(hijo.getId()).updateChildren(map);
    }*/
}
