package com.bit.creciendojuntos.providers;

import com.bit.creciendojuntos.models.Usuario;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class UsuarioProvider {

    DatabaseReference mDatabase;

    public UsuarioProvider() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("usuario");
    }

    public Task<Void> create(Usuario usuario){
        Map<String, Object> map = new HashMap<>();
        map.put("nombre", usuario.getNombre());
        map.put("email", usuario.getEmail());
        map.put("documento", usuario.getDocumento());
        map.put("domicilio", usuario.getDomicilio());
        map.put("telefono", usuario.getTelefono());
        return mDatabase.child(usuario.getId()).setValue(map);
    }
}

