package com.example.fitassistant.Models;

public class ExerciseModel {
    private int workoutId;
    private String name;
    private String description;
    private int sets;
    private int reps;
    private int intensity;

    //TESTING PURPOSES
    public ExerciseModel(int workoutId, String name, String description) {
        this.workoutId = workoutId;
        this.name = name;
        this.description = description;
    }

    public ExerciseModel(String name, String description, int sets, int reps, int intensity) {
        this.name = name;
        this.description = description;
        this.sets = sets;
        this.reps = reps;
        this.intensity = intensity;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getSets() {
        return sets;
    }

    public int getReps() {
        return reps;
    }

    public int getIntensity() {
        return intensity;
    }
}
