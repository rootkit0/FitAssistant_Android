package com.example.fitassistant;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

public class ConfigurationFragment extends Fragment {


    public ConfigurationFragment() {
    }
    public static ConfigurationFragment newInstance() {
        return new ConfigurationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void loadPreferences() {
        SharedPreferences preferences = Objects.requireNonNull(getActivity())
                .getSharedPreferences("configuration", Context.MODE_PRIVATE);

        String user = preferences.getString("user", "default_user");
        String password = preferences.getString("password", "1234");
        int imageView = preferences.getInt("image_id", R.drawable.ic_userconfig);
        String mail = preferences.getString("mail", "");
        int phoneNumber = preferences.getInt("phone_number", 600000000);





    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_configuration, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadPreferences();


    }
}