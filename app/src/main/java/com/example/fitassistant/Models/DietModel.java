package com.example.fitassistant.Models;

public class DietModel {
    public String name;
    public String description;
    public int image;

    public DietModel(String name, String description, int image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}