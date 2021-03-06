package com.example.fitassistant.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.fitassistant.Other.ValidationUtils;
import com.example.fitassistant.Providers.AuthProvider;
import com.example.fitassistant.R;

import java.util.Objects;

public class ChangePasswordFragment extends Fragment {
    private EditText password;
    private EditText confirmPassword;
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
        Objects.requireNonNull(getActivity()).setTitle(getString(R.string.change_password));
        password = view.findViewById(R.id.password_et);
        confirmPassword = view.findViewById(R.id.password_et2);
        Button savePassword = view.findViewById(R.id.save_password);
        savePassword.setOnClickListener(
                v -> {
                    //Check passwords fields contains same content
                    if(password.getText().toString().equals(confirmPassword.getText().toString())) {
                        //Verify password
                        if(ValidationUtils.validatePassword(password.getText().toString())) {
                            //Change password
                            authProvider.changePassword(password.getText().toString());
                            Toast.makeText(getContext(), R.string.correct_password_change, Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getContext(), R.string.short_password, Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getContext(), R.string.password_not_same, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
