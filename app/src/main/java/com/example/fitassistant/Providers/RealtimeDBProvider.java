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
            String name = snapshot.child(String.valueOf(i)).child("name").getValue().toString();
            String description = snapshot.child(String.valueOf(i)).child("description").getValue().toString();
            diets.add(new DietModel(name, description, i, R.drawable.rice));
        }
        return diets;
    }

    public DatabaseReference workoutsReference() {
        return database.getReference(Constants.workoutsPath);
    }

    public List<WorkoutModel> getWorkoutsData(DataSnapshot snapshot) {
        List<WorkoutModel> workouts = new ArrayList<>();
        for(int i=0; i<snapshot.getChildrenCount(); ++i) {
            String name = snapshot.child(String.valueOf(i)).child("name").getValue().toString();
            String description = snapshot.child(String.valueOf(i)).child("description").getValue().toString();
            workouts.add(new WorkoutModel(name, description, i, R.drawable.dumbbell));
        }
        return workouts;
    }

    public DatabaseReference receiptsReference() {
        return database.getReference(Constants.receiptsPath);
    }

    public List<ReceiptModel> getReceiptsData(DataSnapshot snapshot) {
        List<ReceiptModel> receipts = new ArrayList<>();
        for(int i=0; i<snapshot.getChildrenCount(); ++i) {
            String name = snapshot.child(String.valueOf(i)).child("name").getValue().toString();
            String description = snapshot.child(String.valueOf(i)).child("description").getValue().toString();
            int dietType = (int) snapshot.child(String.valueOf(i)).child("dietType").getValue();
            int time = (int) snapshot.child(String.valueOf(i)).child("time").getValue();
            int servings = (int) snapshot.child(String.valueOf(i)).child("servings").getValue();
            //Get ingredients
            ArrayList<String> ingredients = new ArrayList<>();
            for(int j=0; j<snapshot.child(String.valueOf(i)).child("ingredients").getChildrenCount(); ++j) {
                ingredients.add(snapshot.child(String.valueOf(i)).child("ingredients").child(String.valueOf(j)).getValue().toString());
            }
            //TODO: Get nutrition object data
        }
        return receipts;
    }

    public DatabaseReference exercisesReference() {
        return database.getReference(Constants.exercisesPath);
    }

    public List<ExerciseModel> getExercisesData(DataSnapshot snapshot) {
        List<ExerciseModel> exercises = new ArrayList<>();
        return exercises;
    }
}
