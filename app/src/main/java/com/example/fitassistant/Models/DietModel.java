package com.example.fitassistant.Models;

public class DietModel {
    private String name;
    private String description;
    private int dietType;
    public int image;

    public DietModel(String name, String description, int dietType, int image) {
        this.name = name;
        this.description = description;
        this.dietType = dietType;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}