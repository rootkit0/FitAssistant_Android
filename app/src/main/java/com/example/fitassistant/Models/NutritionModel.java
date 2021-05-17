package com.example.fitassistant.Models;

public class NutritionModel {
    private int calories;
    private double protein;
    private double carbs;
    private double fat;
    private double saturated_fat;

    private NutritionModel(int calories, double protein, double carbs, double fat, double saturated_fat) {
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.saturated_fat = saturated_fat;
    }
}
