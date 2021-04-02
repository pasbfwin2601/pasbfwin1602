package com.bit.creciendojuntos.providers;

import com.bit.creciendojuntos.models.Token;
import com.bit.creciendojuntos.models.Usuario;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;


public class TokenProvider {

    Usuario usuario;
    DatabaseReference mDatabase;


    public TokenProvider() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Tokens");
    }


    public void create(final String idPadre){
        if (idPadre == null) return;
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                Token token = new Token(instanceIdResult.getToken());
                mDatabase.child(idPadre).setValue(token);
            }
        });
    }

    public DatabaseReference getToken(String idPadre){
        return mDatabase.child(idPadre);
    }


}
