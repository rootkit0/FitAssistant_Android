package com.example.fitassistant;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

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
                    Intent i = new Intent(SignupActivity.this, MainActivity.class);
                    startActivity(i);
                }
        );
    }
}
