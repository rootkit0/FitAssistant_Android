package com.example.fitassistant.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.fitassistant.MD5Hash;
import com.example.fitassistant.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangePasswordFragment extends Fragment {
    private EditText password;
    private EditText confirmPassword;
    private Button savePassword;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference userConfig;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        String md5Token = MD5Hash.md5(mAuth.getCurrentUser().getEmail());
        database = FirebaseDatabase.getInstance("https://fitassistant-db0ef-default-rtdb.europe-west1.firebasedatabase.app/");
        userConfig = database.getReference(md5Token);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_password, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Canviar contrasenya");
        password = view.findViewById(R.id.password_et);
        confirmPassword = view.findViewById(R.id.password_et2);
        savePassword = view.findViewById(R.id.save_password);
        savePassword.setOnClickListener(
                v -> {
                    if(password.getText().toString().length() >= 8) {
                        if(password.getText().toString().equals(confirmPassword.getText().toString())) {
                            mAuth.getCurrentUser().updatePassword(password.getText().toString());
                            Toast.makeText(getContext(), "Contrasenya canviada correctament!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            //Contrasenyes diferents
                            Toast.makeText(getContext(), "Les contrasenyes no coincideixen!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        //Contrasenya massa curta
                        Toast.makeText(getContext(), "Contrasenya massa curta!", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}
