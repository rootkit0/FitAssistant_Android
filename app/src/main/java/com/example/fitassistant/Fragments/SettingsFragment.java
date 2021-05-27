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

import com.example.fitassistant.Providers.AuthProvider;
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
    private AuthProvider authProvider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authProvider = new AuthProvider();
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
        //TODO: GET DATA FROM "USERS" COLLECTION

        //Save data
        //TODO: SAVE DATA TO "USERS" COLLECTION
        saveContent.setOnClickListener(
                v -> {

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
