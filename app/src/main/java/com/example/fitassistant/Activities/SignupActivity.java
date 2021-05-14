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

public class SignupActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();

        TextView signupText = findViewById(R.id.signup_text);
        signupText.setText("Registra't");

        EditText email = findViewById(R.id.email_edittext);
        email.setHint("Correu electrònic");
        email.setHighlightColor(Color.parseColor("#000C66"));

        EditText password = findViewById(R.id.password_edittext);
        password.setHint("Contrasenya");
        password.setHighlightColor(Color.parseColor("#000C66"));

        EditText invitCode = findViewById(R.id.invitcode_edittext);
        invitCode.setHint("Codi d'invitació");

        Button signupButton = findViewById(R.id.signup_button);
        signupButton.setText("Entra");
        signupButton.setBackgroundColor(Color.parseColor("#000C66"));
        signupButton.setOnClickListener(
                v -> {
                    if(!email.getText().toString().equals("") && !password.getText().toString().equals("")) {
                        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                                .addOnCompleteListener(this, task -> {
                                    if(task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(getApplicationContext(), "Usuari creat correctament! " + user.getEmail(), Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(SignupActivity.this, MainActivity.class);
                                        startActivity(i);
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "Error! No s'ha pogut crear l'usuari!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Error! Credencials incomplets!", Toast.LENGTH_SHORT).show();
                    }
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
            Intent i = new Intent(SignupActivity.this, MainActivity.class);
            startActivity(i);
        }
    }
}
