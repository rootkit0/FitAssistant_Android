package com.example.fitassistant.Fragments;

import android.annotation.SuppressLint;
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

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.fitassistant.Models.UserModel;
import com.example.fitassistant.Other.ValidationUtils;
import com.example.fitassistant.Providers.AuthProvider;
import com.example.fitassistant.Providers.UserProvider;
import com.example.fitassistant.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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
    private AuthProvider authProvider;
    private UserProvider userProvider;
    private StorageReference storageReference;
    //TODO: use shared preferences
    private SharedPreferences sharedPreferences;
    private static final int IMAGE_REQUEST = 2;

    //Image upload settings
    private Uri imageURI;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authProvider = new AuthProvider();
        userProvider = new UserProvider();
        storageReference = FirebaseStorage.getInstance().getReference()
                .child("uploads").child(authProvider.getUserId());

        sharedPreferences = getActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @SuppressLint("SetTextI18n")
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
        userProvider.getUser(authProvider.getUserId()).addOnSuccessListener(
                documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        UserModel actualUser = documentSnapshot.toObject(UserModel.class);
                        username.setText(actualUser.getUsername());
                        email.setText(actualUser.getEmail());
                        phone.setText(actualUser.getPhone());
                        height.setText(Double.toString(actualUser.getHeight()));
                        weight.setText(Double.toString(actualUser.getWeight()));
                        actualGym.setText(actualUser.getGym());
                    }
                }
        );

        //Set Listeners
        saveContent.setOnClickListener(this);

        changePassword.setOnClickListener(this);

        changeImage.setOnClickListener(this);
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
        UserModel updatedUser = new UserModel();
        updatedUser.setId(authProvider.getUserId());
        updatedUser.setUsername(username.getText().toString());
        //If email has changed update on authProvider
        if (!email.getText().toString().equals(authProvider.getUserEmail())) {
            if (ValidationUtils.validateEmail(email.getText().toString())) {
                updatedUser.setEmail(email.getText().toString());
                authProvider.changeEmail(email.getText().toString());
                Toast.makeText(getContext(), "Correu actualitzat correctament!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Correu invàlid!", Toast.LENGTH_SHORT);
            }
        }
        updatedUser.setPhone(phone.getText().toString());
        updatedUser.setHeight(Double.parseDouble(height.getText().toString()));
        updatedUser.setWeight(Double.parseDouble(weight.getText().toString()));
        updatedUser.setGym(actualGym.getText().toString());
        userProvider.updateUser(updatedUser);
    }

    private void setChangePassword() {
        assert getFragmentManager() != null;
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment, new ChangePasswordFragment());
        ft.addToBackStack(null);
        ft.commit();
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






}
