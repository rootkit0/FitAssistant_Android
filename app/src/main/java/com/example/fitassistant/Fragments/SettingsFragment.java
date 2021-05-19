package com.example.fitassistant.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.fitassistant.MD5Hash;
import com.example.fitassistant.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsFragment extends Fragment {
    private Button changeImage;
    private EditText username;
    private EditText email;
    private EditText phone;
    private EditText password;
    private EditText height;
    private EditText weight;
    private TextView actualGym;
    private Button saveContent;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    //Database references
    private DatabaseReference usernameRef;
    private DatabaseReference emailRef;
    private DatabaseReference phoneRef;
    private DatabaseReference passwordRef;
    private DatabaseReference heightRef;
    private DatabaseReference weightRef;
    private DatabaseReference gymRef;
    private DatabaseReference imageRef;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        String md5Token = MD5Hash.md5(mAuth.getCurrentUser().getEmail());
        database = FirebaseDatabase.getInstance("https://fitassistant-db0ef-default-rtdb.europe-west1.firebasedatabase.app/");

        //Set database references
        usernameRef = database.getReference(md5Token + "/username");
        emailRef = database.getReference(md5Token + "/email");
        phoneRef = database.getReference(md5Token + "/phone");
        passwordRef = database.getReference(md5Token + "/password");
        heightRef = database.getReference(md5Token + "/height");
        weightRef = database.getReference(md5Token + "/weight");
        gymRef = database.getReference(md5Token + "/actualGym");
        imageRef = database.getReference(md5Token + "/image");

        //Get data
        usernameRef.addValueEventListener((new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                username.setText(snapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                error.toException().printStackTrace();
            }
        }));
        emailRef.addValueEventListener((new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                email.setText(snapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                error.toException().printStackTrace();
            }
        }));
        phoneRef.addValueEventListener((new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                phone.setText(snapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                error.toException().printStackTrace();
            }
        }));
        passwordRef.addValueEventListener((new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                password.setText(snapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                error.toException().printStackTrace();
            }
        }));
        heightRef.addValueEventListener((new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                height.setText(snapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                error.toException().printStackTrace();
            }
        }));
        weightRef.addValueEventListener((new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                weight.setText(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                error.toException().printStackTrace();
            }
        }));
        gymRef.addValueEventListener((new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                actualGym.setText(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                error.toException().printStackTrace();
            }
        }));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        username = view.findViewById(R.id.username_et);
        email = view.findViewById(R.id.email_et);
        phone = view.findViewById(R.id.phone_et);
        password = view.findViewById(R.id.password_et);
        height = view.findViewById(R.id.height_et);
        weight = view.findViewById(R.id.weight_et);
        actualGym = view.findViewById(R.id.gym_tv2);
        changeImage = view.findViewById(R.id.image_button);
        saveContent = view.findViewById(R.id.save_button);
        //Save content on click
        saveContent.setOnClickListener(
                v -> {
                    usernameRef.setValue(username.getText().toString());
                    //Validation email
                    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                    if(!email.getText().toString().isEmpty() && !email.getText().toString().equals(null)) {
                        if(email.getText().toString().trim().matches(emailPattern)) {
                            emailRef.setValue(email.getText().toString());
                            mAuth.getCurrentUser().updateEmail(email.getText().toString());
                        }
                        else {
                            Toast.makeText(getContext(), "Correu no vàlid!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    //Validation phone
                    if(!phone.getText().toString().isEmpty() && !phone.getText().toString().equals(null)) {
                        if(phone.getText().toString().length() < 9) {
                            Toast.makeText(getContext(), "Telèfon no vàlid!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            phoneRef.setValue(phone.getText().toString());
                        }
                    }
                    //Validation password
                    if(!password.getText().toString().isEmpty() && !password.getText().toString().equals(null)) {
                        if(password.getText().toString().length() < 8) {
                            Toast.makeText(getContext(), "Contrasenya massa curta. Mínim 8 caràcters!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            passwordRef.setValue(password.getText().toString());
                            mAuth.getCurrentUser().updatePassword(password.getText().toString());
                        }
                    }
                    heightRef.setValue(height.getText().toString());
                    weightRef.setValue(weight.getText().toString());
                }
        );
    }
}
