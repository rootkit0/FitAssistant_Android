package com.example.fitassistant.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.fitassistant.Models.UserModel;
import com.example.fitassistant.Providers.AuthProvider;
import com.example.fitassistant.Providers.UserProvider;
import com.example.fitassistant.R;

import java.util.Objects;

public class SignupActivity extends AppCompatActivity {
    private AuthProvider authProvider;
    private UserProvider userProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Objects.requireNonNull(getSupportActionBar()).hide();
        authProvider = new AuthProvider();
        userProvider = new UserProvider();

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
                    //Verify fields
                    if(!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
                        //Call signup method from authprovider
                        authProvider.signUp(email.getText().toString(), password.getText().toString()).addOnCompleteListener(this, task -> {
                            if(task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Usuari creat correctament! " + authProvider.getUserEmail(), Toast.LENGTH_SHORT).show();
                                //Create user model
                                UserModel newUser = new UserModel(email.getText().toString());
                                newUser.setId(authProvider.getUserId());
                                userProvider.createUser(newUser);
                                //Redirect to MainActivity
                                Intent i = new Intent(SignupActivity.this, MainActivity.class);
                                startActivity(i);
                                Animatoo.animateDiagonal(this);
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
        //If logged, redirect to main activity
        if(authProvider.getUserLogged()) {
            Toast.makeText(getApplicationContext(), "Has iniciat sessió com: " + authProvider.getUserEmail(), Toast.LENGTH_SHORT).show();
            Intent i = new Intent(SignupActivity.this, MainActivity.class);
            startActivity(i);
            Animatoo.animateCard(this);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSlideRight(this);

    }
}
