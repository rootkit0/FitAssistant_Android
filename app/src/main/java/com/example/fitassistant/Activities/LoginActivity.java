package com.example.fitassistant.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitassistant.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();

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
                    if(!email.getText().toString().equals("") && !password.getText().toString().equals("")) {
                        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                                .addOnCompleteListener(this, task -> {
                                    if(task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(getApplicationContext(), "Has iniciat sessió com: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(i);
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "Error! Credencials incorrectes!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Error! Credencials incomplets!", Toast.LENGTH_SHORT).show();
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
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //If logged, redirect to main activity
        if(currentUser != null) {
            Toast.makeText(getApplicationContext(), "Has iniciat sessió com: " + currentUser.getEmail(), Toast.LENGTH_SHORT).show();
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
        }
    }
}
