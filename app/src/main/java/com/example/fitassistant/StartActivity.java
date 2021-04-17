package com.example.fitassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button welcome_button = findViewById(R.id.welcome_button);
        welcome_button.setBackgroundColor(Color.parseColor("#000C66"));
        welcome_button.setText("Benvingut");
        welcome_button.setOnClickListener(
                v -> {
                    Intent i = new Intent(StartActivity.this, LoginActivity.class);
                    startActivity(i);
                }
        );
    }
}