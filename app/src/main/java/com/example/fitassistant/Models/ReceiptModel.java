package com.example.fitassistant.Models;

import java.util.ArrayList;

public class ReceiptModel {
    private String id;
    private String name;
    private String description;
    private long time;
    private long servings;
    private ArrayList<String> ingredients;
    private NutritionModel nutrition;

    public ReceiptModel(String name, String description, long time, long servings, ArrayList<String> ingredients, NutritionModel nutrition) {
        this.name = name;
        this.description = description;
        this.time = time;
        this.servings = servings;
        this.ingredients = ingredients;
        this.nutrition = nutrition;
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

    public long getTime() {
        return time;
    }

    public long getServings() {
        return servings;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public NutritionModel getNutrition() {
        return nutrition;
    }
}

