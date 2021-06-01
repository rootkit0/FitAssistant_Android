package com.example.fitassistant.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fitassistant.Models.UserModel;
import com.example.fitassistant.Other.Constants;
import com.example.fitassistant.Other.PermissionUtils;
import com.example.fitassistant.Providers.AuthProvider;
import com.example.fitassistant.Providers.UserProvider;
import com.example.fitassistant.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnMyLocationClickListener,
        GoogleMap.OnMyLocationButtonClickListener {

    private boolean permissionDenied;
    private boolean mShowPermissionDeniedDialog;
    private GoogleMap gMap;
    private String selectedGym;
    private AuthProvider authProvider;
    private UserProvider userProvider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authProvider = new AuthProvider();
        userProvider = new UserProvider();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Mapa");
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_maps);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        Button saveLocation = view.findViewById(R.id.save_location);
        saveLocation.setText("Guardar el meu gimnàs!");
        saveLocation.setOnClickListener(
                v -> {
                    userProvider.getUser(authProvider.getUserId()).addOnSuccessListener(
                            documentSnapshot -> {
                                if (documentSnapshot.exists()) {
                                    UserModel actualUser = documentSnapshot.toObject(UserModel.class);
                                    actualUser.setGym(selectedGym);
                                    userProvider.updateUser(actualUser);
                                }
                            }
                    );
                }
        );
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        LatLng ekke = new LatLng(41.6230326,0.6133067);
        LatLng zona_fitness = new LatLng(41.6158325,0.6104306);
        LatLng anytime_fitness = new LatLng(41.611216,0.6186137);
        LatLng angelus = new LatLng(41.6216083,0.618901);
        LatLng trevol = new LatLng(41.621376,0.6367173);
        LatLng royal = new LatLng(41.6294928,0.6235627);
        googleMap.addMarker(new MarkerOptions().position(ekke).title("Gimnàs Ekke"));
        googleMap.addMarker(new MarkerOptions().position(zona_fitness).title("Gimnàs Zona Fitness"));
        googleMap.addMarker(new MarkerOptions().position(anytime_fitness).title("Gimnàs Anytime Fitness"));
        googleMap.addMarker(new MarkerOptions().position(angelus).title("Gimnàs Angelus"));
        googleMap.addMarker(new MarkerOptions().position(trevol).title("Gimnàs Trevol"));
        googleMap.addMarker(new MarkerOptions().position(royal).title("Gimnàs Royal"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ekke, 15));
        googleMap.setOnMarkerClickListener(marker -> {
            selectedGym = marker.getTitle();
            return false;
        });

        gMap.setOnMyLocationButtonClickListener(this);
        gMap.setOnMyLocationClickListener(this);
        enableLocation();
    };

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }

    private void enableLocation() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if(gMap != null) {

            }
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            PermissionUtils.requestPermission(this, Constants.LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(permissionDenied) {
            showMissingPermissionError();
            permissionDenied = false;
        }
        if (mShowPermissionDeniedDialog) {
            PermissionUtils.PermissionDeniedDialog
                    .newInstance(false).show(getFragmentManager(), "dialog");
            mShowPermissionDeniedDialog = false;
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != Constants.LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }
        if (PermissionUtils.isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableLocation();
            gMap.setMyLocationEnabled(true);
        } else {
            permissionDenied = true;
            mShowPermissionDeniedDialog = true;
        }
    }

    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getFragmentManager(), "dialog");
    }
}