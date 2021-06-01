package com.example.fitassistant.Providers;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class AuthProvider {
    private FirebaseAuth firebaseAuth;

    public AuthProvider() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public Task<AuthResult> signIn(String email, String password) {
        return firebaseAuth.signInWithEmailAndPassword(email, password);
    }

    public Task<AuthResult> signInWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        return firebaseAuth.signInWithCredential(credential);
    }

    public Task<AuthResult> signUp(String email, String password) {
        return firebaseAuth.createUserWithEmailAndPassword(email, password);
    }

    public void signOut() {
        firebaseAuth.signOut();
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

    public String getUsername() {
        if(getUserLogged()) {
            return firebaseAuth.getCurrentUser().getDisplayName();
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

    public void changeEmail(String email) {
        firebaseAuth.getCurrentUser().updateEmail(email);
    }

    public void changePassword(String password) {
        firebaseAuth.getCurrentUser().updatePassword(password);
    }
}
