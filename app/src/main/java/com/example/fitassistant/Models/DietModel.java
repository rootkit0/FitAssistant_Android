package com.example.fitassistant.Models;

public class DietModel {
    private String name;
    private String description;

    public DietModel() { }

    public DietModel(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}