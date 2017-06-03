package com.GetMeThere.GetMeThereLogin;


import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Makers for Constant Destinations
        LatLng SouthAUT = new LatLng(-36.9855, 174.8819);
        LatLng CityAUT = new LatLng(-36.8532, 174.7673);
        LatLng NorthAUT = new LatLng(-36.7979, 174.7583);

        mMap.addMarker(new MarkerOptions().position(SouthAUT).title("AUT SOUTH CAMPUS"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SouthAUT, 10.2f));

        mMap.addMarker(new MarkerOptions().position(CityAUT).title("AUT CITY CAMPUS"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SouthAUT, 10.2f));

        mMap.addMarker(new MarkerOptions().position(NorthAUT).title("AUT NORTH SHORE CAMPUS"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SouthAUT, 10.2f));
    }
}
