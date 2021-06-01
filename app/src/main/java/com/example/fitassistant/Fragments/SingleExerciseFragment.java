package com.example.fitassistant.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitassistant.Models.ExerciseModel;
import com.example.fitassistant.Models.UserModel;
import com.example.fitassistant.Providers.AuthProvider;
import com.example.fitassistant.Providers.RealtimeDBProvider;
import com.example.fitassistant.Providers.UserProvider;
import com.example.fitassistant.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class SingleExerciseFragment extends Fragment {
    private RealtimeDBProvider dbProvider;
    private AuthProvider authProvider;
    private UserProvider userProvider;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbProvider = new RealtimeDBProvider();
        authProvider = new AuthProvider();
        userProvider = new UserProvider();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exercise, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView name = view.findViewById(R.id.exercise_name);
        TextView description = view.findViewById(R.id.exercise_description);
        TextView sets = view.findViewById(R.id.exercise_sets);
        TextView reps = view.findViewById(R.id.exercise_reps);
        TextView intensity = view.findViewById(R.id.exercise_intensity);
        Button addFavorites = view.findViewById(R.id.exercise_favs);
        dbProvider.exercisesReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Objects.requireNonNull(getActivity()).setTitle(getTag());
                ExerciseModel exercise = dbProvider.getExerciseByName(snapshot, getTag());
                name.setText(exercise.getName());
                description.setText(exercise.getDescription());
                sets.setText(String.valueOf(exercise.getSets()));
                reps.setText(String.valueOf(exercise.getReps()));
                intensity.setText(String.valueOf(exercise.getIntensity()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                error.toException().printStackTrace();
            }
        });
        addFavorites.setOnClickListener(
                v -> {
                    userProvider.getUser(authProvider.getUserId()).addOnSuccessListener(
                            documentSnapshot -> {
                                if(documentSnapshot.exists()) {
                                    UserModel actualUser = documentSnapshot.toObject(UserModel.class);
                                    ArrayList<String> favExercises = actualUser.getFavExercises();
                                    favExercises.add(name.getText().toString());
                                    actualUser.setFavExercises(favExercises);
                                    userProvider.updateUser(actualUser);
                                    Toast.makeText(getContext(), R.string.exercise_to_favs, Toast.LENGTH_SHORT);
                                }
                            }
                    );
                }
        );
    }
}
