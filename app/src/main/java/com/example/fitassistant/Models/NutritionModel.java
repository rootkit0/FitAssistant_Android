package com.example.fitassistant.Models;

public class NutritionModel {
    private int calories;
    private double protein;
    private double carbs;
    private double fat;

    public NutritionModel(int calories, double protein, double carbs, double fat) {
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
    }

    public int getCalories() {
        return calories;
    }

    public double getProtein() {
        return protein;
    }

    public double getCarbs() {
        return carbs;
    }

    public double getFat() {
        return fat;
    }
}
