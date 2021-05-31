package com.example.fitassistant.Models;

public class ExerciseModel {
    public int workoutId;
    public String name;
    public String description;
    public int sets;
    public int reps;
    public int intensity;

    public ExerciseModel(String name, String description, int sets, int reps, int intensity) {
        this.name = name;
        this.description = description;
        this.sets = sets;
        this.reps = reps;
        this.intensity = intensity;
    }

    public int getWorkoutId() {
        return workoutId;
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
