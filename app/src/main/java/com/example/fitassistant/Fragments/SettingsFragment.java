package com.example.fitassistant.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.fitassistant.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;

public class SettingsFragment extends Fragment implements View.OnClickListener {
    private Button changeImage;
    private EditText username;
    private EditText email;
    private EditText phone;
    private EditText height;
    private EditText weight;
    private ImageView userImageView;
    private TextView actualGym;
    private Button saveContent;
    private Button changePassword;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    //Database reference
    private DatabaseReference userConfig;
    private StorageReference storageReference;
    String userId;
    private SharedPreferences sharedPreferences;
    private static final int IMAGE_REQUEST = 2;

    //Image upload settings
    private Uri imageURI;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getUid();
        database = FirebaseDatabase.getInstance("https://fitassistant-db0ef-default-rtdb.europe-west1.firebasedatabase.app/");
        //Set database reference
        userConfig = database.getReference(userId);
        storageReference = FirebaseStorage.getInstance().getReference().child("uploads").child(userId);

        sharedPreferences = getActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Configuració");
        username = view.findViewById(R.id.username_et);
        email = view.findViewById(R.id.email_et);
        phone = view.findViewById(R.id.phone_et);
        height = view.findViewById(R.id.height_et);
        weight = view.findViewById(R.id.weight_et);
        actualGym = view.findViewById(R.id.gym_tv2);
        changeImage = view.findViewById(R.id.image_button);
        saveContent = view.findViewById(R.id.save_button);
        changePassword = view.findViewById(R.id.change_password);
        userImageView = view.findViewById(R.id.user_iv);

        loadUserImage();

        //Get data
        //TODO: check the comment below (making app crash when going to settings fragment)
        /*
        userConfig.addValueEventListener((new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                username.setText(snapshot.child("username").getValue().toString());
                email.setText(snapshot.child("email").getValue().toString());
                phone.setText(snapshot.child("phone").getValue().toString());
                height.setText(snapshot.child("height").getValue().toString());
                weight.setText(snapshot.child("weight").getValue().toString());
                actualGym.setText(snapshot.child("actualGym").getValue().toString());

                userImageView.setImageResource();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                error.toException().printStackTrace();
            }
        }));*/

        //Save content on click
        saveContent.setOnClickListener(this);

        changePassword.setOnClickListener(this);

        changeImage.setOnClickListener(this);
    }

    private void loadUserImage() {
        storageReference.getBytes(Long.MAX_VALUE).addOnSuccessListener(bytes -> {
            // Use the bytes to display the image
            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            userImageView.setImageBitmap(Bitmap.createScaledBitmap(bmp, userImageView.getWidth()
                    , userImageView.getHeight(), false));
        }).addOnFailureListener(exception -> {
            // Handle any errors
            Toast.makeText(getContext(), "La imatge no pot ser carregada" + exception.getMessage(), Toast.LENGTH_SHORT).show();
        });

    }

    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK) {
            imageURI = data.getData();

            uploadImage();
        }
    }

    private void uploadImage() {
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Pujant la imatge");
        pd.show();

        if (imageURI != null) {

            storageReference.putFile(imageURI).addOnCompleteListener(task ->
                    storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                        String url = uri.toString();
                        Log.d("DownloadUrl", url);
                        pd.dismiss();

                        Toast.makeText(getContext(), "Imatge pujada satisfactòriament", Toast.LENGTH_LONG).show();
                        loadUserImage();
                    }));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_button:
                setSaveContent();
                break;

            case R.id.change_password:
                setChangePassword();
                break;

            case R.id.image_button:
                openImage();
                break;
        }
    }

    private void setSaveContent() {
        userConfig.child("username").setValue(username.getText().toString());
        //Validation email
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (!email.getText().toString().isEmpty() && !email.getText().toString().equals(null)) {
            if (email.getText().toString().trim().matches(emailPattern)) {
                userConfig.child("email").setValue(email.getText().toString());
                mAuth.getCurrentUser().updateEmail(email.getText().toString());
            } else {
                Toast.makeText(getContext(), "Correu no vàlid!", Toast.LENGTH_SHORT).show();
            }
        }
        //Validation phone
        if (!phone.getText().toString().isEmpty() && !phone.getText().toString().equals(null)) {
            if (phone.getText().toString().length() < 9) {
                Toast.makeText(getContext(), "Telèfon no vàlid!", Toast.LENGTH_SHORT).show();
            } else {
                userConfig.child("phone").setValue(phone.getText().toString());
            }
        }
        userConfig.child("height").setValue(height.getText().toString());
        userConfig.child("weight").setValue(weight.getText().toString());
        Toast.makeText(getContext(), "Guardat correctament", Toast.LENGTH_SHORT).show();

    }

    private void setChangePassword() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment, new ChangePasswordFragment());
        ft.addToBackStack(null);
        ft.commit();
    }
}
