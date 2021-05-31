package com.example.fitassistant.Providers;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fitassistant.Models.UserModel;
import com.example.fitassistant.Other.Constants;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class UserProvider {
    private CollectionReference collectionReference;
    private StorageReference storageReference;
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
        return collectionReference.document(user.getId()).update(map);
    }

    public Bitmap getUserImage(String userId, ImageView iv) {
        storageReference.child(userId).getBytes(Long.MAX_VALUE).addOnSuccessListener(
                bytes -> {
                    userImage = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    iv.setImageBitmap(Bitmap.createScaledBitmap(userImage, userImage.getWidth(), userImage.getHeight(), false));
                }
        );
        return userImage;
    }

    public void uploadUserImage(String userId, Uri imageUri, ProgressDialog pd) {
        storageReference.child(userId).putFile(imageUri).addOnCompleteListener(
                task -> {
                    storageReference.getDownloadUrl().addOnSuccessListener(
                            uri -> {
                                pd.dismiss();
                            }
                    );
                }
        );
    }
}
