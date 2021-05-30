package com.example.fitassistant.Fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
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
    private CheckBox wifiCheck;
    private AuthProvider authProvider;
    private UserProvider userProvider;
    private StorageReference storageReference;
    //TODO: use shared preferences
    private SharedPreferences sharedPreferences;
    private String networkPreference;
    private static final int IMAGE_REQUEST = 2;

    //Image upload settings
    private Uri imageURI;
    private NetworkReceiver receiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authProvider = new AuthProvider();
        userProvider = new UserProvider();
        storageReference = FirebaseStorage.getInstance().getReference()
                .child("uploads").child(authProvider.getUserId());

        sharedPreferences = getActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        networkPreference = sharedPreferences.getString("networkPreference", "Wi-Fi");

        checkNetStatus();


        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkReceiver();
        getActivity().registerReceiver(receiver, filter);

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
        wifiCheck = view.findViewById(R.id.wifi_check);

        loadUserImage();

        setWifiCheckListener();

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

    private void setWifiCheckListener() {
        if (sharedPreferences.getString("networkPreference", "Wi-Fi").equals("ANY"))
            wifiCheck.setChecked(false);
        wifiCheck.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                sharedPreferences.edit().putString("networkPreference", "Wi-Fi").apply();
            else
                sharedPreferences.edit().putString("networkPreference", "ANY").apply();
        });
    }

    private void checkNetStatus() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isWifiConn = false;
        boolean isMobileConn = false;
        for (Network network : connMgr.getAllNetworks()) {
            NetworkInfo networkInfo = connMgr.getNetworkInfo(network);
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                isWifiConn |= networkInfo.isConnected();
                if (!isWifiConn && networkPreference.equals("Wi-Fi"))
                    Toast.makeText(getContext(), "Siusplau, activi el Wi-Fi o canvii la configuració de xarxa", Toast.LENGTH_LONG).show();
            }
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                isMobileConn |= networkInfo.isConnected();
            }
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

    @Override
    public void onStart() {
        super.onStart();
        sharedPreferences = getActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        networkPreference = sharedPreferences.getString("networkPreference", "Wi-Fi");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Unregisters BroadcastReceiver when app is destroyed.
        if (receiver != null) {
            getActivity().unregisterReceiver(receiver);
        }
    }

    public class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager conn = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = conn.getActiveNetworkInfo();

            // Checks the user prefs and the network connection. Based on the result, decides whether
            // to refresh the display or keep the current display.
            // If the userpref is Wi-Fi only, checks to see if the device has a Wi-Fi connection.
            if (networkPreference.equals("Wi-Fi") && networkInfo != null
                    && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                // If device has its Wi-Fi connection, sets refreshDisplay
                // to true. This causes the display to be refreshed when the user
                // returns to the app.
                Toast.makeText(context, R.string.wifi_connected, Toast.LENGTH_SHORT).show();

                // If the setting is ANY network and there is a network connection
                // (which by process of elimination would be mobile), sets refreshDisplay to true.
            } else if (networkPreference.equals("ANY") && networkInfo != null) {
                Toast.makeText(context, R.string.data_connected, Toast.LENGTH_SHORT).show();

                // Otherwise, the app can't download content--either because there is no network
                // connection (mobile or Wi-Fi), or because the pref setting is WIFI, and there
                // is no Wi-Fi connection.
                // Sets refreshDisplay to false.
            } else {
                Toast.makeText(context, R.string.lost_connection, Toast.LENGTH_SHORT).show();
            }
        }
    }

}
