package com.example.fitassistant.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitassistant.Models.ExerciseModel;
import com.example.fitassistant.Models.UserModel;
import com.example.fitassistant.Providers.AuthProvider;
import com.example.fitassistant.Providers.ImageProvider;
import com.example.fitassistant.Providers.RealtimeDBProvider;
import com.example.fitassistant.Providers.UserProvider;
import com.example.fitassistant.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class SingleExerciseFragment extends Fragment {
    private TextView name;
    private TextView description;
    private TextView sets;
    private TextView reps;
    private TextView intensity;
    private Button addFavorites;
    private ImageView exercise_iv;
    private RealtimeDBProvider dbProvider;
    private AuthProvider authProvider;
    private UserProvider userProvider;
    private ImageProvider imageProvider;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbProvider = new RealtimeDBProvider();
        authProvider = new AuthProvider();
        userProvider = new UserProvider();
        imageProvider = new ImageProvider();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exercise, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initLayoutObjects(view);

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
                imageProvider.getImage("exercise", exercise_iv);
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
                                    if(actualUser != null) {
                                        ArrayList<String> favExercises = actualUser.getFavExercises();
                                        if(favExercises == null) {
                                            favExercises = new ArrayList<>();
                                        }
                                        if(!favExercises.contains(name.getText().toString())) {
                                            favExercises.add(name.getText().toString());
                                            actualUser.setFavExercises(favExercises);
                                            userProvider.updateUser(actualUser);
                                            Toast.makeText(getContext(), R.string.exercise_to_favs, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
                    });
    }

    private void initLayoutObjects(@NonNull View view) {
        name = view.findViewById(R.id.exercise_name);
        description = view.findViewById(R.id.exercise_description);
        sets = view.findViewById(R.id.exercise_sets);
        reps = view.findViewById(R.id.exercise_reps);
        intensity = view.findViewById(R.id.exercise_intensity);
        addFavorites = view.findViewById(R.id.exercise_favs);
        exercise_iv = view.findViewById(R.id.exercise_iv);
    }
}
