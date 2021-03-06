package com.bit.creciendojuntos.providers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class AuthProvider {

    FirebaseAuth mAuth;

    public AuthProvider(){
        mAuth = FirebaseAuth.getInstance();
    }

    public Task<AuthResult> register(String email, String password){
       return mAuth.createUserWithEmailAndPassword(email,  password);
    }

    public Task<AuthResult> login(String email, String password){
        return mAuth.signInWithEmailAndPassword(email, password);
    }

    public void logout(){
        mAuth.signOut();
    }

    public String getId() {
        return mAuth.getCurrentUser().getUid();
    }

}
