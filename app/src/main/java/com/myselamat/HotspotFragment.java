package com.myselamat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class HotspotFragment extends Fragment {

    public HotspotFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize view
        View view = inflater.inflate(R.layout.fragment_hotspot, container, false);

        // Initialize map fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);

        // Async map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull @org.jetbrains.annotations.NotNull GoogleMap googleMap) {

                final MarkerOptions markerOptions = new MarkerOptions();

                markerOptions.title("Malaysia");
                markerOptions.position(new LatLng(4.2105, 101.9758));

                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(markerOptions.getPosition(), 7));
                googleMap.addMarker(markerOptions);

                // When map is ready
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng latLng) {
                        // When clicked on map

                        // set marker position
                        markerOptions.position(latLng);

                        // Reemove all marker
                        googleMap.clear();

                        // Animating to zoom the marker
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                latLng, 10
                        ));

                        // Add marker on map
                        googleMap.addMarker(markerOptions);
                    }
                });
            }
        });

        // return view
        return view;
    }
}