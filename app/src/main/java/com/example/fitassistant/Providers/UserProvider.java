package com.example.fitassistant.Providers;

import com.example.fitassistant.Models.UserModel;
import com.example.fitassistant.Other.Constants;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserProvider {
    private CollectionReference collectionReference;

    public UserProvider() {
        collectionReference = FirebaseFirestore.getInstance().collection(Constants.usersPath);
    }

    public Task<DocumentSnapshot> getUser(String userId) {
        return collectionReference.document(userId).get();
    }

    public Task<Void> createUser(UserModel user) {
        return collectionReference.document(user.getId()).set(user);
    }

    public Task<Void> updateUser(UserModel user) {
        Map<String, Object> map = new HashMap<>();
        map.put("email", user.getEmail());
        map.put("username", user.getUsername());
        map.put("phone", user.getPhone());
        map.put("gym", user.getGym());
        map.put("height", user.getHeight());
        map.put("weight", user.getWeight());
        return collectionReference.document(user.getId()).update(map);
    }
}
