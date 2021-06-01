package com.example.fitassistant.Models;

import java.util.ArrayList;

public class UserModel {
    private String id;
    private String username;
    private String email;
    private String phone;
    private double height;
    private double weight;
    private String gym;
    private ArrayList<String> favReceipts;
    private ArrayList<String> favExercises;

    public UserModel() { }

    public UserModel(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getGym() {
        return gym;
    }

    public void setGym(String gym) {
        this.gym = gym;
    }

    public ArrayList<String> getFavReceipts() {
        return favReceipts;
    }

    public void setFavReceipts(ArrayList<String> favReceipts) {
        this.favReceipts = favReceipts;
    }

    public ArrayList<String> getFavExercises() {
        return favExercises;
    }

    public void setFavExercises(ArrayList<String> favExercises) {
        this.favExercises = favExercises;
    }
}
