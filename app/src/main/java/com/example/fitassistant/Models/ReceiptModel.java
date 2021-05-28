package com.example.fitassistant.Models;

import java.util.ArrayList;

public class ReceiptModel {
    private int dietId;
    private String name;
    private String description;
    private double time;
    private double servings;
    private ArrayList<String> ingredients;

    //TESTING PURPOSES
    public ReceiptModel(int dietId, String name, String description) {
        this.dietId = dietId;
        this.name = name;
        this.description = description;
    }

    public ReceiptModel(String name, String description, long time, long servings, ArrayList<String> ingredients, NutritionModel nutrition) {
        this.name = name;
        this.description = description;
        this.time = time;
        this.servings = servings;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getTime() {
        return time;
    }

    public double getServings() {
        return servings;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }
}

