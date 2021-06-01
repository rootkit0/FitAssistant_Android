package com.example.fitassistant.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.fitassistant.Providers.AuthProvider;
import com.example.fitassistant.R;

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
        getActivity().setTitle("Inici");

        TextView home_title = getView().findViewById(R.id.home_title);
        home_title.setText("Hola " + authProvider.getUserEmail());
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
                    ft.setCustomAnimations(
                            R.anim.slide_in,  // enter
                            R.anim.fade_out,  // exit
                            R.anim.fade_in,   // popEnter
                            R.anim.slide_out  // popExit
                    );
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
                    ft.setCustomAnimations(
                            R.anim.slide_in,  // enter
                            R.anim.fade_out,  // exit
                            R.anim.fade_in,   // popEnter
                            R.anim.slide_out  // popExit
                    );
                    ft.replace(R.id.fragment, new WorkoutsFragment());
                    ft.addToBackStack(null);
                    ft.commit();
                }
        );
    }
}
