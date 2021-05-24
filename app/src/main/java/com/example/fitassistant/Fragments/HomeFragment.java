package com.example.fitassistant.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.fitassistant.MD5Hash;
import com.example.fitassistant.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeFragment extends Fragment {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference usernameRef;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Inici");
        mAuth = FirebaseAuth.getInstance();
        String md5Token = MD5Hash.md5(mAuth.getCurrentUser().getEmail());
        database = FirebaseDatabase.getInstance("https://fitassistant-db0ef-default-rtdb.europe-west1.firebasedatabase.app/");
        usernameRef = database.getReference(md5Token + "/username");

        TextView home_title = getView().findViewById(R.id.home_title);
        home_title.setText("Hola " + mAuth.getCurrentUser().getEmail());
        TextView home_text = getView().findViewById(R.id.home_text);
        home_text.setText("Benvingut/da a FitAssitant!\n" +
                "Fes un seguiment de les teves dietes i rutines\n" +
                "Vols aconseguir els teus objectius fàcilment?\n" +
                "T'ho posem fàcil!");

        TextView home_button = getView().findViewById(R.id.home_button);
        home_button.setText("Explora les dietes");
        home_button.setOnClickListener(
                v -> {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment, new DietsFragment());
                    ft.addToBackStack(null);
                    ft.commit();
                }
        );

        TextView home_button2 = getView().findViewById(R.id.home_button2);
        home_button2.setText("Explora les rutines");
        home_button2.setOnClickListener(
                v -> {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment, new WorkoutFragment());
                    ft.addToBackStack(null);
                    ft.commit();
                }
        );
    }
}
