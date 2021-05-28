package com.example.fitassistant.Providers;

import com.example.fitassistant.Models.DietModel;
import com.example.fitassistant.Models.ExerciseModel;
import com.example.fitassistant.Models.ReceiptModel;
import com.example.fitassistant.Models.WorkoutModel;
import com.example.fitassistant.Other.Constants;
import com.example.fitassistant.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RealtimeDBProvider {
    private FirebaseDatabase database;

    public RealtimeDBProvider() {
        database = FirebaseDatabase.getInstance(Constants.databaseUrl);
    }

    public DatabaseReference dietsReference() {
        return database.getReference(Constants.dietsPath);
    }

    public List<DietModel> getDietsData(DataSnapshot snapshot) {
        List<DietModel> diets = new ArrayList<>();
        for(int i=0; i<snapshot.getChildrenCount(); ++i) {
            String name = Objects.requireNonNull(snapshot.child(String.valueOf(i)).child("name").getValue()).toString();
            String description = Objects.requireNonNull(snapshot.child(String.valueOf(i)).child("description").getValue()).toString();
            diets.add(new DietModel(name, description, R.drawable.rice));
        }
        return diets;
    }

    public DatabaseReference workoutsReference() {
        return database.getReference(Constants.workoutsPath);
    }

    public List<WorkoutModel> getWorkoutsData(DataSnapshot snapshot) {
        List<WorkoutModel> workouts = new ArrayList<>();
        for(int i=0; i<snapshot.getChildrenCount(); ++i) {
            String name = Objects.requireNonNull(snapshot.child(String.valueOf(i)).child("name").getValue()).toString();
            String description = Objects.requireNonNull(snapshot.child(String.valueOf(i)).child("description").getValue()).toString();
            workouts.add(new WorkoutModel(name, description, R.drawable.dumbbell));
        }
        return workouts;
    }

    public DatabaseReference receiptsReference() {
        return database.getReference(Constants.receiptsPath);
    }

    public List<ReceiptModel> getReceiptsDataByDietId(DataSnapshot snapshot, int _dietId) {
        List<ReceiptModel> receipts = new ArrayList<>();
        for(int i=0; i<snapshot.getChildrenCount(); ++i) {
            int dietId = Integer.parseInt(Objects.requireNonNull(snapshot.child(String.valueOf(i)).child("dietId").getValue()).toString());
            String name = Objects.requireNonNull(snapshot.child(String.valueOf(i)).child("name").getValue()).toString();
            String description = Objects.requireNonNull(snapshot.child(String.valueOf(i)).child("description").getValue()).toString();
            if(dietId == _dietId) {
                receipts.add(new ReceiptModel(dietId, name, description));
            }
        }
        return receipts;
    }

    public DatabaseReference exercisesReference() {
        return database.getReference(Constants.exercisesPath);
    }

    public List<ExerciseModel> getExercisesDataByWorkoutId(DataSnapshot snapshot, int _workoutId) {
        List<ExerciseModel> exercises = new ArrayList<>();
        for(int i=0; i<snapshot.getChildrenCount(); ++i) {
            int workoutId = Integer.parseInt(Objects.requireNonNull(snapshot.child(String.valueOf(i)).child("workoutId").getValue()).toString());
            String name = Objects.requireNonNull(snapshot.child(String.valueOf(i)).child("name").getValue()).toString();
            String description = Objects.requireNonNull(snapshot.child(String.valueOf(i)).child("description").getValue()).toString();
            if(workoutId == _workoutId) {
                exercises.add(new ExerciseModel(workoutId, name, description));
            }
        }
        return exercises;
    }
}
