package com.example.fitassistant.Models;

public class DietModel {
    private String name;
    private String description;
    private int image;

    public DietModel() { }

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