package com.example.fitassistant.Models;

public class ExerciseModel {
    private String name;
    private int sets;
    private int reps;
    private int difficulty;

    public ExerciseModel(String name, int sets, int reps, int difficulty) {
        this.name = name;
        this.sets = sets;
        this.reps = reps;
        this.difficulty = difficulty;
    }
}
