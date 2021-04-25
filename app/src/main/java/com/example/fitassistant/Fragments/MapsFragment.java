package com.example.fitassistant.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitassistant.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment {

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng ekke = new LatLng(41.6230326,0.6133067);
            LatLng zona_fitness = new LatLng(41.6158325,0.6104306);
            LatLng anytime_fitness = new LatLng(41.611216,0.6186137);
            LatLng angelus = new LatLng(41.6216083,0.618901);
            LatLng trevol = new LatLng(41.621376,0.6367173);
            LatLng royal = new LatLng(41.6294928,0.6235627);
            googleMap.addMarker(new MarkerOptions().position(ekke));
            googleMap.addMarker(new MarkerOptions().position(zona_fitness));
            googleMap.addMarker(new MarkerOptions().position(anytime_fitness));
            googleMap.addMarker(new MarkerOptions().position(angelus));
            googleMap.addMarker(new MarkerOptions().position(trevol));
            googleMap.addMarker(new MarkerOptions().position(royal));
        }
    };

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
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_maps);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}