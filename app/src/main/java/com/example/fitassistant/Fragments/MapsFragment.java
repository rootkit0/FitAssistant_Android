package com.example.fitassistant.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.fitassistant.Models.UserModel;
import com.example.fitassistant.Providers.AuthProvider;
import com.example.fitassistant.Providers.UserProvider;
import com.example.fitassistant.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MapsFragment extends Fragment {
    private String selectedGym;
    private AuthProvider authProvider;
    private UserProvider userProvider;

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
            googleMap.addMarker(new MarkerOptions().position(ekke).title("Gimnàs Ekke"));
            googleMap.addMarker(new MarkerOptions().position(zona_fitness).title("Gimnàs Zona Fitness"));
            googleMap.addMarker(new MarkerOptions().position(anytime_fitness).title("Gimnàs Anytime Fitness"));
            googleMap.addMarker(new MarkerOptions().position(angelus).title("Gimnàs Angelus"));
            googleMap.addMarker(new MarkerOptions().position(trevol).title("Gimnàs Trevol"));
            googleMap.addMarker(new MarkerOptions().position(royal).title("Gimnàs Royal"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ekke, 15));

            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(@NonNull Marker marker) {
                    selectedGym = marker.getTitle();
                    return false;
                }
            });
        }
    };

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
            mapFragment.getMapAsync(callback);
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
                                    Toast.makeText(getContext(), "Gimnàs guardat!", Toast.LENGTH_SHORT);
                                }
                            }
                     );
                }
        );
    }
}