package com.example.fitassistant.Models;

public class WorkoutModel {
    private String name;
    private String description;
    private int image;

    public WorkoutModel() { }

    public WorkoutModel(String name, String description, int image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
