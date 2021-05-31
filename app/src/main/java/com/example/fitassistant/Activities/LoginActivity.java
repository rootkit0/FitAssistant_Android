package com.example.fitassistant.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitassistant.Providers.AuthProvider;
import com.example.fitassistant.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 1234;
    private AuthProvider authProvider;
    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        //Init providers
        authProvider = new AuthProvider();
        //Init google sign in
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(getApplicationContext(), googleSignInOptions);

        TextView loginText = findViewById(R.id.login_text);
        loginText.setText("Inicia sessió");

        EditText email = findViewById(R.id.email_edittext);
        email.setHint("Correu electrònic");

        EditText password = findViewById(R.id.password_edittext);
        password.setHint("Contrasenya");

        Button loginButton = findViewById(R.id.login_button);
        loginButton.setText("Entra");
        loginButton.setBackgroundColor(Color.parseColor("#000C66"));
        loginButton.setOnClickListener(
                v -> {
                    //Verify fields
                    if(!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
                        //Call signin method from authprovider
                        authProvider.signIn(email.getText().toString(), password.getText().toString()).addOnCompleteListener(this, task -> {
                            if(task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Has iniciat sessió com: " + authProvider.getUserEmail(), Toast.LENGTH_SHORT).show();
                                //Redirect to MainActivity
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Error! Credencials incorrectes!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
        );

        Button signupButton = findViewById(R.id.signup_button);
        signupButton.setText("Registra't");
        signupButton.setBackgroundColor(Color.parseColor("#000C66"));
        signupButton.setOnClickListener(
                v -> {
                    Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                    startActivity(i);
                }
        );

        SignInButton googleSignIn = findViewById(R.id.sign_in_button);
        googleSignIn.setOnClickListener(
                v -> {
                    Intent signInIntent = googleSignInClient.getSignInIntent();
                    startActivityForResult(signInIntent, RC_SIGN_IN);
                }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                authProvider.signInWithCredential(credential).addOnCompleteListener(this, task -> {
                    if(task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Has iniciat sessió com: " + authProvider.getUserEmail(), Toast.LENGTH_SHORT).show();
                        //Redirect to MainActivity
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Error! Credencials incorrectes!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart() {
        super.onStart();
        //If logged, redirect to main activity
        if(authProvider.getUserLogged() || GoogleSignIn.getLastSignedInAccount(getApplicationContext()) != null) {
            Toast.makeText(getApplicationContext(), "Has iniciat sessió com: " + authProvider.getUserEmail(), Toast.LENGTH_SHORT).show();
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
        }
    }
}
