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
        ArrayList<DietModel> diets = new ArrayList<>();
        if(snapshot.exists()) {
            for(DataSnapshot messageSnapshot: snapshot.getChildren()) {
                DietModel diet = messageSnapshot.getValue(DietModel.class);
                diets.add(diet);
            }
        }
        return diets;
        /*
        List<DietModel> diets = new ArrayList<>();
        for(int i=0; i<snapshot.getChildrenCount(); ++i) {
            String name = Objects.requireNonNull(snapshot.child(String.valueOf(i)).child("name").getValue()).toString();
            String description = Objects.requireNonNull(snapshot.child(String.valueOf(i)).child("description").getValue()).toString();
            diets.add(new DietModel(name, description, R.drawable.rice));
        }
        */
    }

    public DatabaseReference workoutsReference() {
        return database.getReference(Constants.workoutsPath);
    }

    public List<WorkoutModel> getWorkoutsData(DataSnapshot snapshot) {
        List<WorkoutModel> workouts = new ArrayList<>();
        if(snapshot.exists()) {
            for(DataSnapshot messageSnapshot: snapshot.getChildren()) {
                WorkoutModel workout = messageSnapshot.getValue(WorkoutModel.class);
                workouts.add(workout);
            }
        }
        return workouts;
        /*
        for(int i=0; i<snapshot.getChildrenCount(); ++i) {
            String name = Objects.requireNonNull(snapshot.child(String.valueOf(i)).child("name").getValue()).toString();
            String description = Objects.requireNonNull(snapshot.child(String.valueOf(i)).child("description").getValue()).toString();
            workouts.add(new WorkoutModel(name, description, R.drawable.dumbbell));
        }
        */
    }

    public DatabaseReference receiptsReference() {
        return database.getReference(Constants.receiptsPath);
    }

    public List<ReceiptModel> getReceiptsDataByDietId(DataSnapshot snapshot, int _dietId) {
        List<ReceiptModel> receipts = new ArrayList<>();
        if(snapshot.exists()) {
            for(DataSnapshot messageSnapshot: snapshot.getChildren()) {
                ReceiptModel receipt = messageSnapshot.getValue(ReceiptModel.class);
                if(receipt.getDietId() == _dietId) {
                    receipts.add(receipt);
                }
            }
        }
        return receipts;
        /*
        for(int i=0; i<snapshot.getChildrenCount(); ++i) {
            int dietId = Integer.parseInt(Objects.requireNonNull(snapshot.child(String.valueOf(i)).child("dietId").getValue()).toString());
            if(dietId == _dietId) {
                String name = Objects.requireNonNull(snapshot.child(String.valueOf(i)).child("name").getValue()).toString();
                String description = Objects.requireNonNull(snapshot.child(String.valueOf(i)).child("description").getValue()).toString();
                receipts.add(new ReceiptModel(dietId, name, description));
            }
        }
        */
    }

    public ReceiptModel getReceiptByName(DataSnapshot snapshot, String _name) {
        ReceiptModel receipt = null;
        if(snapshot.exists()) {
            for(DataSnapshot messageSnapshot: snapshot.getChildren()) {
                ReceiptModel actualReceipt = messageSnapshot.getValue(ReceiptModel.class);
                if(actualReceipt.getName().equals(_name)) {
                    receipt = actualReceipt;
                    break;
                }
            }
        }
        return receipt;
        /*
        for(int i=0; i<snapshot.getChildrenCount(); ++i) {
            String name = Objects.requireNonNull(snapshot.child(String.valueOf(i)).child("name").getValue()).toString();
            if(name.equals(_name)) {
                receipt = snapshot.getValue(ReceiptModel.class);

                int dietId = Integer.parseInt(Objects.requireNonNull(snapshot.child(String.valueOf(i)).child("dietId").getValue()).toString());
                String description = Objects.requireNonNull(snapshot.child(String.valueOf(i)).child("description").getValue()).toString();
                receipt = new ReceiptModel(dietId, name, description);
        */
    }

    public DatabaseReference exercisesReference() {
        return database.getReference(Constants.exercisesPath);
    }

    public List<ExerciseModel> getExercisesDataByWorkoutId(DataSnapshot snapshot, int _workoutId) {
        List<ExerciseModel> exercises = new ArrayList<>();
        if(snapshot.exists()) {
            for(DataSnapshot messageSnapshot: snapshot.getChildren()) {
                ExerciseModel exercise = messageSnapshot.getValue(ExerciseModel.class);
                if(exercise.getWorkoutId() == _workoutId) {
                    exercises.add(exercise);
                }
            }
        }
        return exercises;
        /*
        for(int i=0; i<snapshot.getChildrenCount(); ++i) {
            int workoutId = Integer.parseInt(Objects.requireNonNull(snapshot.child(String.valueOf(i)).child("workoutId").getValue()).toString());
            if(workoutId == _workoutId) {
                String name = Objects.requireNonNull(snapshot.child(String.valueOf(i)).child("name").getValue()).toString();
                String description = Objects.requireNonNull(snapshot.child(String.valueOf(i)).child("description").getValue()).toString();
                exercises.add(new ExerciseModel(workoutId, name, description));
            }
        }
        */
    }

    public ExerciseModel getExerciseByName(DataSnapshot snapshot, String _name) {
        ExerciseModel exercise = null;
        if(snapshot.exists()) {
            for(DataSnapshot messageSnapshot: snapshot.getChildren()) {
                ExerciseModel actualExercise = messageSnapshot.getValue(ExerciseModel.class);
                if(actualExercise.getName().equals(_name)) {
                    exercise = actualExercise;
                    break;
                }
            }
        }
        return exercise;
        /*
        for(int i=0; i<snapshot.getChildrenCount(); ++i) {
            String name = Objects.requireNonNull(snapshot.child(String.valueOf(i)).child("name").getValue()).toString();
            if(name.equals(_name)) {
                int workoutId = Integer.parseInt(Objects.requireNonNull(snapshot.child(String.valueOf(i)).child("workoutId").getValue()).toString());
                String description = Objects.requireNonNull(snapshot.child(String.valueOf(i)).child("description").getValue()).toString();
                exercise = new ExerciseModel(workoutId, name, description);
            }
        }
        */
    }
}
