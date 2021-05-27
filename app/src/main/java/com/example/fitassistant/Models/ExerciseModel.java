package com.example.fitassistant.Models;

public class ExerciseModel {
    private String id;
    private String name;
    private String description;
    private int sets;
    private int reps;
    private int intensity;

    public ExerciseModel(String name, String description, int sets, int reps, int intensity) {
        this.name = name;
        this.description = description;
        this.sets = sets;
        this.reps = reps;
        this.intensity = intensity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
