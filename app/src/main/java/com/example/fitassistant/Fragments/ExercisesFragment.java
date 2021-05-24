package com.example.fitassistant.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitassistant.Adapters.GenericListAdapter;
import com.example.fitassistant.Models.ExerciseModel;
import com.example.fitassistant.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ExercisesFragment extends Fragment {
    private List<ExerciseModel> exercises = new ArrayList<>();
    private FirebaseDatabase database;
    private DatabaseReference exercisesReference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance("https://fitassistant-db0ef-default-rtdb.europe-west1.firebasedatabase.app/");
        exercisesReference = database.getReference("/exercises");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Exercicis");
        exercisesReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(int i=0; i<snapshot.getChildrenCount(); ++i) {
                    String name = snapshot.child(String.valueOf(i)).child("name").getValue().toString();
                    String description = snapshot.child(String.valueOf(i)).child("description").getValue().toString();
                    int workoutType = (int) snapshot.child(String.valueOf(i)).child("workoutType").getValue();
                    int sets = (int) snapshot.child(String.valueOf(i)).child("sets").getValue();
                    int reps = (int) snapshot.child(String.valueOf(i)).child("reps").getValue();
                    int intensity = (int) snapshot.child(String.valueOf(i)).child("intensity").getValue();
                    //Only add exercises from the selected workout
                    if(workoutType == Integer.valueOf(getTag())) {
                        exercises.add(new ExerciseModel(name, description, sets, reps, intensity));
                    }
                }

                GenericListAdapter exerciseListAdapter = new GenericListAdapter(exercises, getContext(), getFragmentManager());
                RecyclerView recyclerView = view.findViewById(R.id.list_recyclerview);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(exerciseListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                error.toException().printStackTrace();
            }
        });
    }
}