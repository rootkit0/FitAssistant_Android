package com.example.fitassistant.Providers;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.example.fitassistant.Models.UserModel;
import com.example.fitassistant.Other.Constants;
import com.example.fitassistant.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class UserProvider {
    private final CollectionReference collectionReference;
    private final StorageReference storageReference;
    private Bitmap userImage;

    public UserProvider() {
        collectionReference = FirebaseFirestore.getInstance().collection(Constants.usersPath);
        storageReference = FirebaseStorage.getInstance().getReference().child("uploads");
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
        map.put("favReceipts", user.getFavReceipts());
        map.put("favExercises", user.getFavExercises());
        return collectionReference.document(user.getId()).update(map);
    }

    public Bitmap getUserImage(String userId, ImageView iv) {
        storageReference.child(userId).getBytes(Long.MAX_VALUE).addOnSuccessListener(
                bytes -> {
                    userImage = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    iv.setImageBitmap(Bitmap.createScaledBitmap(userImage, iv.getWidth(), iv.getHeight(), false));
                });
        Log.w("STORAGE IMAGE ROUTE", "GET IMAGE: " + storageReference.child(userId).toString());
        return userImage;
    }
}
