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
    private final FirebaseDatabase database;

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
    }

    public DatabaseReference receiptsReference() {
        return database.getReference(Constants.receiptsPath);
    }

    public List<ReceiptModel> getReceiptsDataByDietId(DataSnapshot snapshot, String _dietId) {
        List<ReceiptModel> receipts = new ArrayList<>();
        if(snapshot.exists()) {
            for(DataSnapshot messageSnapshot: snapshot.getChildren()) {
                ReceiptModel receipt = messageSnapshot.getValue(ReceiptModel.class);
                if(receipt.getDietId().equals(_dietId)) {
                    receipts.add(receipt);
                }
            }
        }
        return receipts;
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
    }

    public DatabaseReference exercisesReference() {
        return database.getReference(Constants.exercisesPath);
    }

    public List<ExerciseModel> getExercisesDataByWorkoutId(DataSnapshot snapshot, String _workoutId) {
        List<ExerciseModel> exercises = new ArrayList<>();
        if(snapshot.exists()) {
            for(DataSnapshot messageSnapshot: snapshot.getChildren()) {
                ExerciseModel exercise = messageSnapshot.getValue(ExerciseModel.class);
                if(exercise.getWorkoutId().equals(_workoutId)) {
                    exercises.add(exercise);
                }
            }
        }
        return exercises;
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
    }
}
