package com.example.fitassistant.Fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import com.example.fitassistant.Models.UserModel;
import com.example.fitassistant.Other.Constants;
import com.example.fitassistant.Other.ValidationUtils;
import com.example.fitassistant.Providers.AuthProvider;
import com.example.fitassistant.Providers.UserProvider;
import com.example.fitassistant.R;

import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class SettingsFragment extends Fragment {
    private EditText username;
    private EditText email;
    private EditText phone;
    private EditText height;
    private EditText weight;
    private TextView actualGym;
    private TextView activeNetwork;
    private Button saveContent;
    private Button changePassword;
    private Button changeImage;
    private ImageView userImage;
    private Uri imageURI;
    private AuthProvider authProvider;
    private UserProvider userProvider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authProvider = new AuthProvider();
        userProvider = new UserProvider();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Objects.requireNonNull(getActivity()).setTitle("Configuració");
        initLayoutObjects(view);
        setUserImage();
        //Set active network
        activeNetwork.setText(Constants.getNetworkState());
        //Get data
        userProvider.getUser(authProvider.getUserId()).addOnSuccessListener(
                documentSnapshot -> {
                    if(documentSnapshot.exists()) {
                        UserModel actualUser = documentSnapshot.toObject(UserModel.class);
                        if(actualUser != null) {
                            username.setText(actualUser.getUsername());
                            email.setText(actualUser.getEmail());
                            phone.setText(actualUser.getPhone());
                            height.setText(Double.toString(actualUser.getHeight()));
                            weight.setText(Double.toString(actualUser.getWeight()));
                            actualGym.setText(actualUser.getGym());
                        }
                    }
                });
        //Save data
        saveContent.setOnClickListener(
                v -> {
                    userProvider.getUser(authProvider.getUserId()).addOnSuccessListener(
                            documentSnapshot -> {
                                if(documentSnapshot.exists()) {
                                    UserModel actualUser = documentSnapshot.toObject(UserModel.class);
                                    if(actualUser != null) {
                                        if(!username.getText().toString().equals(actualUser.getUsername())) {
                                            actualUser.setUsername(username.getText().toString());
                                        }
                                        if(!email.getText().toString().equals(actualUser.getEmail())) {
                                            //Verify email
                                            if(ValidationUtils.validateEmail(email.getText().toString())) {
                                                actualUser.setEmail(email.getText().toString());
                                                authProvider.changeEmail(email.getText().toString());
                                            }
                                        }
                                        if(!phone.getText().toString().equals(actualUser.getPhone())) {
                                            actualUser.setPhone(phone.getText().toString());
                                        }
                                        if(Double.parseDouble(height.getText().toString()) != actualUser.getHeight()) {
                                            actualUser.setHeight(Double.parseDouble(height.getText().toString()));
                                        }
                                        if(Double.parseDouble(weight.getText().toString()) != actualUser.getWeight()) {
                                            actualUser.setWeight(Double.parseDouble(weight.getText().toString()));
                                        }
                                        userProvider.updateUser(actualUser);
                                    }
                                }
                            }
                    );
                });
        //Change password
        changePassword.setOnClickListener(
                v -> {
                    assert getFragmentManager() != null;
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.setCustomAnimations(
                            R.anim.slide_in,
                            R.anim.fade_out,
                            R.anim.fade_in,
                            R.anim.slide_out
                    );
                    ft.replace(R.id.fragment, new ChangePasswordFragment());
                    ft.addToBackStack(null);
                    ft.commit();
                });
        //Change image
        changeImage.setOnClickListener(
                v -> {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, 2);
                });
    }

    private void initLayoutObjects(@NonNull View view) {
        username = view.findViewById(R.id.username_et);
        email = view.findViewById(R.id.email_et);
        phone = view.findViewById(R.id.phone_et);
        height = view.findViewById(R.id.height_et);
        weight = view.findViewById(R.id.weight_et);
        actualGym = view.findViewById(R.id.gym_tv2);
        activeNetwork = view.findViewById(R.id.network_tv2);
        saveContent = view.findViewById(R.id.save_button);
        changePassword = view.findViewById(R.id.change_password);
        changeImage = view.findViewById(R.id.image_button);
        userImage = view.findViewById(R.id.user_iv);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK) {
            imageURI = data.getData();
            uploadImage();
        }
    }

    private void uploadImage() {
        ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage(getString(R.string.uploading_image));
        pd.show();

        if(imageURI != null) {
            userProvider.uploadUserImage(authProvider.getUserId(), imageURI, pd);
            Toast.makeText(getContext(), R.string.image_uploaded_ok, Toast.LENGTH_SHORT).show();
            setUserImage();
        }
    }

    private void setUserImage() {
        userProvider.getUserImage(authProvider.getUserId(), userImage);
    }
}
