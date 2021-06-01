package com.example.fitassistant.Models;

public class WorkoutModel {
    private String name;
    private String description;

    public WorkoutModel() { }

    public WorkoutModel(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
