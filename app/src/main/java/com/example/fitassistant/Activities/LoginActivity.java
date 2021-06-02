package com.example.fitassistant.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.fitassistant.Providers.AuthProvider;
import com.example.fitassistant.R;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Button loginButton;
    private Button signupButton;
    private AuthProvider authProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).hide();
        //Init providers
        authProvider = new AuthProvider();
        //Init layout objects
        initLayoutObjects();

        loginButton.setOnClickListener(
                v -> {
                    //Verify fields
                    if(!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
                        authProvider.signIn(email.getText().toString(), password.getText().toString()).addOnCompleteListener(this, task -> {
                            if(task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), getString(R.string.session_init_as) + authProvider.getUserEmail(), Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                                Animatoo.animateCard(this);
                            }
                            else {
                                Toast.makeText(getApplicationContext(), R.string.incorrect_credentials, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

        signupButton.setOnClickListener(
                v -> {
                    Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                    startActivity(i);
                    Animatoo.animateCard(this);
                });
    }

    public void initLayoutObjects() {
        email = findViewById(R.id.email_edittext);
        password = findViewById(R.id.password_edittext);
        loginButton = findViewById(R.id.login_button);
        signupButton = findViewById(R.id.signup_button);
    }

    @Override
    public void onStart() {
        super.onStart();
        //If logged, redirect to main activity
        if(authProvider.getUserLogged()) {
            startMainActivity();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSlideRight(this);
    }

    public void startMainActivity() {
        Toast.makeText(getApplicationContext(), getString(R.string.session_init_as) + authProvider.getUserEmail(), Toast.LENGTH_SHORT).show();
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
        Animatoo.animateCard(this);
    }
}
