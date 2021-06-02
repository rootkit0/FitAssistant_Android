package com.example.fitassistant.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.fitassistant.Models.UserModel;
import com.example.fitassistant.Providers.AuthProvider;
import com.example.fitassistant.Providers.UserProvider;
import com.example.fitassistant.R;

import java.util.Objects;

public class SignupActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Button signupButton;
    private AuthProvider authProvider;
    private UserProvider userProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Objects.requireNonNull(getSupportActionBar()).hide();
        //Init providers
        authProvider = new AuthProvider();
        userProvider = new UserProvider();
        //Init layout objects
        initLayoutObjects();

        signupButton.setOnClickListener(
                v -> {
                    //Verify fields
                    if(!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
                        authProvider.signUp(email.getText().toString(), password.getText().toString()).addOnCompleteListener(this, task -> {
                            if(task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), getString(R.string.user_created) + authProvider.getUserEmail(), Toast.LENGTH_SHORT).show();
                                //Create user model
                                UserModel newUser = new UserModel(email.getText().toString());
                                newUser.setId(authProvider.getUserId());
                                userProvider.createUser(newUser);
                                //Redirect to MainActivity
                                Intent i = new Intent(SignupActivity.this, MainActivity.class);
                                startActivity(i);
                                Animatoo.animateCard(this);
                            }
                            else {
                                Toast.makeText(getApplicationContext(), R.string.user_not_created, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else {
                        Toast.makeText(getApplicationContext(), R.string.incorrect_credentials, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initLayoutObjects() {
        email = findViewById(R.id.email_edittext);
        password = findViewById(R.id.password_edittext);
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
        Intent i = new Intent(SignupActivity.this, MainActivity.class);
        startActivity(i);
        Animatoo.animateCard(this);
    }
}
