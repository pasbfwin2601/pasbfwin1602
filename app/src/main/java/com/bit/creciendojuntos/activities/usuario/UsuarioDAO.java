package com.bit.creciendojuntos.activities.usuario;

import androidx.annotation.NonNull;

import com.bit.creciendojuntos.Logica.LUsuario;
import com.bit.creciendojuntos.models.Usuario;
import com.bit.creciendojuntos.utils.Constantes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class UsuarioDAO {

    private static UsuarioDAO usuarioDAO;
    private FirebaseDatabase database;
    private DatabaseReference referenceUsuarios;

    public static UsuarioDAO getInstance(){
        if (usuarioDAO==null) usuarioDAO = new UsuarioDAO();
        return usuarioDAO;
    }

    private UsuarioDAO() {

        database = FirebaseDatabase.getInstance();
        referenceUsuarios = database.getReference().child("Users").child("usuario");
    }

    public String getKeyUsuario(){
        
        return FirebaseAuth.getInstance().getUid();
    }

    public void subirFotoDefecto(){

        referenceUsuarios.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<LUsuario> lUsuariosLista = new ArrayList<>();
                for (DataSnapshot childDataSnapShot : dataSnapshot.getChildren()) {
                    Usuario usuario = childDataSnapShot.getValue(Usuario.class);
                    LUsuario lUsuario = new LUsuario(childDataSnapShot.getKey(),usuario);
                    lUsuariosLista.add(lUsuario);
                }
                
                for (LUsuario lUsuario : lUsuariosLista){
                    if (lUsuario.getUsuario().getImage()==null){
                        referenceUsuarios.child(lUsuario.getKey()).child("image").setValue(Constantes.URL_FOTO_DEFECTO_USUARIOS);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
