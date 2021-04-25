package com.example.fitassistant.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitassistant.R;

import java.util.Objects;

public class SettingsFragment extends Fragment {

    private Button saveButton;
    private EditText user;
    private ImageView image;
    private EditText password;
    private EditText mail;
    private EditText phone;
    private EditText weight;
    private EditText height;

    private void loadPreferences() {
        SharedPreferences preferences = Objects.requireNonNull(getActivity())
                .getSharedPreferences("configuration", Context.MODE_PRIVATE);

        this.user.setText(preferences.getString("user", "default_user"));
        this.image.setImageResource(preferences.getInt("image_id", R.drawable.ic_userconfig));
        this.mail.setText(preferences.getString("mail", "exemple@gmail.com"));
        this.phone.setText(String.valueOf(preferences.getInt("phone_number", 0)));
        this.weight.setText(String.valueOf(preferences.getInt("weight", 0)));
        this.height.setText(String.valueOf(preferences.getInt("height", 0)));
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindViews(view);
        loadPreferences();

        saveButton.setOnClickListener(v -> savePreferences(v));
    }

    private void bindViews(View view) {

        this.saveButton = (Button) view.findViewById(R.id.save_button);

        this.user = view.findViewById(R.id.user_et);
        this.password = view.findViewById(R.id.password_et);
        this.mail = view.findViewById(R.id.email_et);
        this.phone = view.findViewById(R.id.phone_et);
        this.height = view.findViewById(R.id.height_et);
        this.weight = view.findViewById(R.id.weight_et);
        this.image = view.findViewById(R.id.user_imageView);
    }

    private void savePreferences(View v) {
        SharedPreferences preferences = Objects.requireNonNull(this.getActivity())
                .getSharedPreferences("configuration", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("user", this.user.getText().toString());
        editor.putString("password", this.password.getText().toString());
        editor.putString("mail", this.mail.getText().toString());
        editor.putString("phone", this.phone.getText().toString());
        editor.putString("weight", this.weight.getText().toString());
        editor.putString("height", this.height.getText().toString());
        editor.putString("image_id", String.valueOf(this.image.getId()));

        editor.apply();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }
}
