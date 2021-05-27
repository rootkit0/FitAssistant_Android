package com.example.fitassistant.Other;

public class VerificationUtils {
    public static boolean verifyPassword(String password) {
        String passwordPattern = "[a-zA-Z+]+[0-9+]+";
        return (!password.isEmpty() && password.trim().matches(passwordPattern));
    }

    public static boolean verifyEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return (!email.isEmpty() && email.trim().matches(emailPattern));
    }
}
