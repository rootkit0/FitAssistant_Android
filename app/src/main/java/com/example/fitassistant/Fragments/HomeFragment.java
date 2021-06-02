package com.example.fitassistant.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.fitassistant.Providers.AuthProvider;
import com.example.fitassistant.R;

import java.util.Objects;

public class HomeFragment extends Fragment {
    private AuthProvider authProvider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authProvider = new AuthProvider();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Objects.requireNonNull(getActivity()).setTitle(getString(R.string.init));
        TextView home_title = view.findViewById(R.id.home_title);
        home_title.setText("Hola " + authProvider.getUserEmail());
        Button diets_button = view.findViewById(R.id.home_button);
        diets_button.setOnClickListener(
                v -> {
                    assert getFragmentManager() != null;
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.setCustomAnimations(
                            R.anim.slide_in,
                            R.anim.fade_out,
                            R.anim.fade_in,
                            R.anim.slide_out
                    );
                    ft.replace(R.id.fragment, new DietsFragment());
                    ft.addToBackStack(null);
                    ft.commit();
                });
        Button workouts_button = view.findViewById(R.id.home_button2);
        workouts_button.setOnClickListener(
                v -> {
                    assert getFragmentManager() != null;
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.setCustomAnimations(
                            R.anim.slide_in,
                            R.anim.fade_out,
                            R.anim.fade_in,
                            R.anim.slide_out
                    );
                    ft.replace(R.id.fragment, new WorkoutsFragment());
                    ft.addToBackStack(null);
                    ft.commit();
                });
    }
}
