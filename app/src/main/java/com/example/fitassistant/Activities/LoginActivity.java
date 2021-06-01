package com.example.fitassistant.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitassistant.Models.UserModel;
import com.example.fitassistant.Other.Constants;
import com.example.fitassistant.Providers.AuthProvider;
import com.example.fitassistant.Providers.UserProvider;
import com.example.fitassistant.R;
import com.example.fitassistant.Services.MyFirebaseMessagingService;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class LoginActivity extends AppCompatActivity {
    private AuthProvider authProvider;
    private UserProvider userProvider;
    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        //Init providers
        authProvider = new AuthProvider();
        userProvider = new UserProvider();
        //Init google sign in stuff
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        TextView loginText = findViewById(R.id.login_text);
        loginText.setText(R.string.log_in);
        EditText email = findViewById(R.id.email_edittext);
        email.setHint(R.string.email);
        EditText password = findViewById(R.id.password_edittext);
        password.setHint(R.string.password);
        Button loginButton = findViewById(R.id.login_button);
        loginButton.setText(R.string.enter);
        loginButton.setBackgroundColor(Color.parseColor("#000C66"));
        loginButton.setOnClickListener(
                v -> {
                    //Verify fields
                    if(!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
                        //Call signin method from authprovider
                        authProvider.signIn(email.getText().toString(), password.getText().toString()).addOnCompleteListener(this, task -> {
                            if(task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), getString(R.string.session_init_as) + authProvider.getUserEmail(), Toast.LENGTH_SHORT).show();
                                //Redirect to MainActivity
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                            }
                            else {
                                Toast.makeText(getApplicationContext(), R.string.incorrect_credentials, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
        );

        Button signupButton = findViewById(R.id.signup_button);
        signupButton.setText(R.string.register);
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
                    startActivityForResult(signInIntent, Constants.RC_SIGN_IN);
                }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constants.RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                authProvider.signInWithGoogle(account).addOnCompleteListener(this, task1 -> {
                    if(task1.isSuccessful()) {
                        userProvider.getUser(authProvider.getUserId()).addOnSuccessListener(
                                documentSnapshot -> {
                                    if(documentSnapshot.exists()) {
                                        startMainActivity();
                                    }
                                    else {
                                        UserModel newUser = new UserModel();
                                        newUser.setUsername(authProvider.getUsername());
                                        newUser.setEmail(authProvider.getUserEmail());
                                        userProvider.createUser(newUser).addOnCompleteListener(
                                                task2 -> {
                                                    if(task2.isSuccessful()) {
                                                        startMainActivity();
                                                    }
                                                    else {
                                                        Toast.makeText(getApplicationContext(), R.string.user_not_created, Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                        );
                                    }
                                }
                        );
                    }
                });
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    public void startMainActivity() {
        Toast.makeText(getApplicationContext(), getString(R.string.session_init_as) + authProvider.getUserEmail(), Toast.LENGTH_SHORT).show();
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onStart() {
        super.onStart();
        //If logged, redirect to main activity
        if(authProvider.getUserLogged() || GoogleSignIn.getLastSignedInAccount(getApplicationContext()) != null) {
            startMainActivity();
        }
    }
}
