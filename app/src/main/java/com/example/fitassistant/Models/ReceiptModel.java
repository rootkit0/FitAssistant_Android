package com.example.fitassistant.Models;

import java.util.ArrayList;

public class ReceiptModel {
    private String name;
    private String description;
    private int time;
    private int servings;
    private ArrayList<String> ingredients;
    private NutritionModel nutrition;

    public ReceiptModel(String name, String description, int time, int servings, ArrayList<String> ingredients, NutritionModel nutrition) {
        this.name = name;
        this.description = description;
        this.time = time;
        this.servings = servings;
        this.ingredients = ingredients;
        this.nutrition = nutrition;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getTime() {
        return time;
    }

    public int getServings() {
        return servings;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public NutritionModel getNutrition() {
        return nutrition;
    }
}

