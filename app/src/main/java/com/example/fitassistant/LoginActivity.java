package com.example.fitassistant;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
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
}
