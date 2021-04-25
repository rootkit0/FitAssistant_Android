package com.example.fitassistant;

public class DietElement {
    public String name;
    public int image;
    public boolean isVegan;
    public String description;


    public DietElement(String name, int image, boolean isVegan, String description) {
        this.name = name;
        this.image = image;
        this.isVegan = isVegan;
        this.description = description;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public boolean isVegan() {
        return isVegan;
    }

    public void setVegan(boolean vegan) {
        isVegan = vegan;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
