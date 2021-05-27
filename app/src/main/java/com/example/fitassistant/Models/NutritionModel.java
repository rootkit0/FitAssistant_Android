package com.example.fitassistant.Models;

public class NutritionModel {
    private String id;
    private long calories;
    private long protein;
    private long carbs;
    private long fat;

    public NutritionModel(long calories, long protein, long carbs, long fat) {
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getCalories() {
        return calories;
    }

    public long getProtein() {
        return protein;
    }

    public long getCarbs() {
        return carbs;
    }

    public long getFat() {
        return fat;
    }
}
