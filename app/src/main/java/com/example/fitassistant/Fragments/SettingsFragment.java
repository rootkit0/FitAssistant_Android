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
import androidx.fragment.app.FragmentTransaction;

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
    private EditText height;
    private EditText weight;
    private TextView actualGym;
    private Button saveContent;
    private Button changePassword;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    //Database reference
    private DatabaseReference userConfig;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        String md5Token = MD5Hash.md5(mAuth.getCurrentUser().getEmail());
        database = FirebaseDatabase.getInstance("https://fitassistant-db0ef-default-rtdb.europe-west1.firebasedatabase.app/");
        //Set database reference
        userConfig = database.getReference(md5Token);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Configuració");
        username = view.findViewById(R.id.username_et);
        email = view.findViewById(R.id.email_et);
        phone = view.findViewById(R.id.phone_et);
        height = view.findViewById(R.id.height_et);
        weight = view.findViewById(R.id.weight_et);
        actualGym = view.findViewById(R.id.gym_tv2);
        changeImage = view.findViewById(R.id.image_button);
        saveContent = view.findViewById(R.id.save_button);
        changePassword = view.findViewById(R.id.change_password);

        //Get data
        userConfig.addValueEventListener((new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                username.setText(snapshot.child("username").getValue().toString());
                email.setText(snapshot.child("email").getValue().toString());
                phone.setText(snapshot.child("phone").getValue().toString());
                height.setText(snapshot.child("height").getValue().toString());
                weight.setText(snapshot.child("weight").getValue().toString());
                actualGym.setText(snapshot.child("actualGym").getValue().toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                error.toException().printStackTrace();
            }
        }));

        //Save content on click
        saveContent.setOnClickListener(
                v -> {
                    userConfig.child("username").setValue(username.getText().toString());
                    //Validation email
                    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                    if(!email.getText().toString().isEmpty() && !email.getText().toString().equals(null)) {
                        if(email.getText().toString().trim().matches(emailPattern)) {
                            userConfig.child("email").setValue(email.getText().toString());
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
                            userConfig.child("phone").setValue(phone.getText().toString());
                        }
                    }
                    userConfig.child("height").setValue(height.getText().toString());
                    userConfig.child("weight").setValue(weight.getText().toString());
                }
        );

        changePassword.setOnClickListener(
                v -> {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment, new ChangePasswordFragment());
                    ft.addToBackStack(null);
                    ft.commit();
                }
        );
    }
}
