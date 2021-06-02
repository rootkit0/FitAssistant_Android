package com.example.fitassistant.Providers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ImageProvider {
    private final StorageReference storageReference;
    private Bitmap image;

    public ImageProvider() {
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    public Bitmap getImage(String path, ImageView iv) {
        storageReference.child(path).getBytes(Long.MAX_VALUE).addOnSuccessListener(
                bytes -> {
                    image = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    iv.setImageBitmap(Bitmap.createScaledBitmap(image, image.getWidth(), image.getHeight(), false));
                }
        );
        return image;
    }
}