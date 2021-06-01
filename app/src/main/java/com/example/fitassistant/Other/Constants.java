package com.example.fitassistant.Other;

public class Constants {
    public static String databaseUrl = "https://fitassistant-db0ef-default-rtdb.europe-west1.firebasedatabase.app/";
    public static String dietsPath = "/diets";
    public static String exercisesPath = "/exercises";
    public static String receiptsPath = "/receipts";
    public static String workoutsPath = "/workouts";
    public static String usersPath = "/users";
    public static String messagesPath = "/messages";
    public static final int RC_SIGN_IN = 1;

    private static String networkState;

    public static String getNetworkState() {
        return networkState;
    }

    public static void setNetworkState(String state) {
        networkState = state;
    }
}
