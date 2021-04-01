package com.bit.creciendojuntos.providers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    public String getEmail() {
        if (mAuth.getCurrentUser() != null) {
            return mAuth.getCurrentUser().getEmail();
        }
        else {
            return null;
        }
    }

    public String getId() {
        if (mAuth.getCurrentUser() !=null) {
            return mAuth.getCurrentUser().getUid();
        }
        else {
            return null;
        }
    }

    public FirebaseUser getUserSession() {
        if (mAuth.getCurrentUser() != null) {
            return mAuth.getCurrentUser();
        }
        else {
            return null;
        }
    }

    public void logout(){
        if (mAuth != null){
            mAuth.signOut();
        }
    }

}
