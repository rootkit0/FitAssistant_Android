package com.example.fitassistant.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.fitassistant.Models.UserModel;
import com.example.fitassistant.Other.Constants;
import com.example.fitassistant.Other.ValidationUtils;
import com.example.fitassistant.Providers.AuthProvider;
import com.example.fitassistant.Providers.UserProvider;
import com.example.fitassistant.R;

public class SettingsFragment extends Fragment {
    private EditText username;
    private EditText email;
    private EditText phone;
    private EditText height;
    private EditText weight;
    private TextView actualGym;
    private TextView activeNetwork;
    private Button saveContent;
    private Button changePassword;
    private Button changeImage;
    private AuthProvider authProvider;
    private UserProvider userProvider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authProvider = new AuthProvider();
        userProvider = new UserProvider();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @SuppressLint("SetTextI18n")
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
        activeNetwork = view.findViewById(R.id.network_tv2);
        changeImage = view.findViewById(R.id.image_button);
        saveContent = view.findViewById(R.id.save_button);
        changePassword = view.findViewById(R.id.change_password);

        //Set active network
        activeNetwork.setText(Constants.getNetworkState());

        //Get data
        userProvider.getUser(authProvider.getUserId()).addOnSuccessListener(
                documentSnapshot -> {
                    if(documentSnapshot.exists()) {
                        UserModel actualUser = documentSnapshot.toObject(UserModel.class);
                        username.setText(actualUser.getUsername());
                        email.setText(actualUser.getEmail());
                        phone.setText(actualUser.getPhone());
                        height.setText(Double.toString(actualUser.getHeight()));
                        weight.setText(Double.toString(actualUser.getWeight()));
                        actualGym.setText(actualUser.getGym());
                    }
                }
        );

        //Save data
        saveContent.setOnClickListener(
                v -> {
                    UserModel updatedUser = new UserModel();
                    updatedUser.setId(authProvider.getUserId());
                    updatedUser.setUsername(username.getText().toString());
                    //If email has changed update on authProvider
                    if(!email.getText().toString().equals(authProvider.getUserEmail())) {
                        if(ValidationUtils.validateEmail(email.getText().toString())) {
                            updatedUser.setEmail(email.getText().toString());
                            authProvider.changeEmail(email.getText().toString());
                            Toast.makeText(getContext(), "Correu actualitzat correctament!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getContext(), "Correu invàlid!", Toast.LENGTH_SHORT);
                        }
                    }
                    updatedUser.setPhone(phone.getText().toString());
                    updatedUser.setHeight(Double.parseDouble(height.getText().toString()));
                    updatedUser.setWeight(Double.parseDouble(weight.getText().toString()));
                    updatedUser.setGym(actualGym.getText().toString());
                    userProvider.updateUser(updatedUser);
                }
        );

        changePassword.setOnClickListener(
                v -> {
                    assert getFragmentManager() != null;
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment, new ChangePasswordFragment());
                    ft.addToBackStack(null);
                    ft.commit();
                }
        );
    }
}
