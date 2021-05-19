package com.example.fitassistant.Models;

import java.util.ArrayList;

public class DietModel {
    private String name;
    private String description;
    public int image;
    private ArrayList<ReceiptModel> receipts;

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

    public ArrayList<ReceiptModel> getReceipts() {
        return receipts;
    }
}