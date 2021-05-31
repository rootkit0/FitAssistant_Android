package com.example.fitassistant.Models;

import java.util.ArrayList;

public class ReceiptModel {
    public int dietId;
    public String name;
    public String description;
    public double time;
    public double servings;
    public ArrayList<String> ingredients;

    public ReceiptModel(String name, String description, long time, long servings, ArrayList<String> ingredients) {
        this.name = name;
        this.description = description;
        this.time = time;
        this.servings = servings;
        this.ingredients = ingredients;
    }

    public int getDietId() {
        return dietId;
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

