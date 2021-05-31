package com.example.fitassistant.Models;

import java.util.ArrayList;

public class ReceiptModel {
    private String dietId;
    private String name;
    private String description;
    private double time;
    private double servings;
    private ArrayList<String> ingredients;

    public ReceiptModel() { }

    public ReceiptModel(String name, String description, long time, long servings, ArrayList<String> ingredients) {
        this.name = name;
        this.description = description;
        this.time = time;
        this.servings = servings;
        this.ingredients = ingredients;
    }

    public String getDietId() {
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

