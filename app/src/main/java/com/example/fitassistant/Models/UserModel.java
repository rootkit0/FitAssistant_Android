package com.example.fitassistant.Models;

public class UserModel {
    private String id;
    private String email;
    private String username;
    private String phone;
    private String gym;
    private double height;
    private double weight;

    public UserModel(String email) {
        this.email = email;
    }

    public UserModel(String email, String username, String phone, String gym, double height, double weight) {
        this.email = email;
        this.username = username;
        this.phone = phone;
        this.gym = gym;
        this.height = height;
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPhone() {
        return phone;
    }

    public String getGym() {
        return gym;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }
}
