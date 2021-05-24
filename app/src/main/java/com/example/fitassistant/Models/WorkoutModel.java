package com.example.fitassistant.Models;

public class WorkoutModel {
    private String name;
    private String description;
    private int workoutType;
    public int image;

    public WorkoutModel(String name, String description, int workoutType, int image) {
        this.name = name;
        this.description = description;
        this.workoutType = workoutType;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
