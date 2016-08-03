package com.example.fnp2.assessment4iot.Activity;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.fnp2.assessment4iot.MarkerGoogleMap.MarkerAssignator;
import com.example.fnp2.assessment4iot.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
{
    private GoogleMap _mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        _mMap = googleMap;

        MarkerAssignator    markerAssignator = new MarkerAssignator(_mMap);

        markerAssignator.execute();
        _mMap.moveCamera(CameraUpdateFactory.newLatLng(markerAssignator
                                                        .getCentralMarker()
                                                        .getPosition()));
        _mMap.animateCamera(CameraUpdateFactory.zoomTo(15.5f));
    }
}
