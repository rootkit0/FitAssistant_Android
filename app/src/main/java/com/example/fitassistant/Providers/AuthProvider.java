package com.example.fitassistant.Providers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthProvider {
    private FirebaseAuth firebaseAuth;

    public AuthProvider() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public Task<AuthResult> signIn(String email, String password) {
        return firebaseAuth.signInWithEmailAndPassword(email, password);
    }

    public Task<AuthResult> signUp(String email, String password) {
        return firebaseAuth.createUserWithEmailAndPassword(email, password);
    }

    public void changePassword(String password) {
        firebaseAuth.getCurrentUser().updatePassword(password);
    }

    public boolean getUserLogged() {
        return firebaseAuth.getCurrentUser() != null;
    }

    public String getUserId() {
        if(getUserLogged()) {
            return firebaseAuth.getCurrentUser().getUid();
        }
        else {
            return null;
        }
    }

    public String getUserEmail() {
        if(getUserLogged()) {
            return firebaseAuth.getCurrentUser().getEmail();
        }
        else {
            return null;
        }
    }
}
