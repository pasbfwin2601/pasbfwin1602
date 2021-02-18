package com.bit.creciendojuntos.providers;

import com.bit.creciendojuntos.models.Medico;
import com.bit.creciendojuntos.models.Usuario;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MedicoProvider {

    DatabaseReference mDatabase;

    public MedicoProvider() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("medico");
    }

    public Task<Void> create(Medico medico){
        Map<String, Object> map = new HashMap<>();
        map.put("nombre", medico.getNombre());
        map.put("email", medico.getEmail());
        map.put("nroMatricula", medico.getNroMatricula());
        map.put("telefono", medico.getTelefono());
        map.put("especialidad", medico.getEspecialidad());
        return mDatabase.child(medico.getId()).setValue(map);
    }

}
