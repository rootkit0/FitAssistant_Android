package com.example.fitassistant.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.fitassistant.Other.VerificationUtils;
import com.example.fitassistant.Providers.AuthProvider;
import com.example.fitassistant.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangePasswordFragment extends Fragment {
    private EditText password;
    private EditText confirmPassword;
    private Button savePassword;
    private AuthProvider authProvider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authProvider = new AuthProvider();
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
                    //Check passwords fields contains same content
                    if(password.getText().toString().equals(confirmPassword.getText().toString())) {
                        //Verify password
                        if(VerificationUtils.verifyPassword(password.getText().toString())) {
                            //Change password
                            authProvider.changePassword(password.getText().toString());
                        }
                        else {
                            Toast.makeText(getContext(), "Contrasenya massa curta o bé poc segura!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getContext(), "Les contrasenyes no coincideixen!", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}
