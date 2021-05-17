package com.example.fitassistant.Models;

import java.util.ArrayList;

public class DietModel {
    private String name;
    private String description;
    private boolean isVegan;
    public int image;
    private ArrayList<ReceiptModel> receipts;

    public DietModel(String name, String description, boolean isVegan, int image) {
        this.name = name;
        this.description = description;
        this.isVegan = isVegan;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isVegan() {
        return isVegan;
    }
}