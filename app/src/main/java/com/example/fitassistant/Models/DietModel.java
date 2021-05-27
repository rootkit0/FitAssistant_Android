package com.example.fitassistant.Models;

public class DietModel {
    private String id;
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
}