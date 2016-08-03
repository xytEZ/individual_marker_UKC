package com.example.fnp2.assessment4iot.MarkerGoogleMap;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by xytez on 3/16/16.
 */
public final class MarkerFactory
{
    public final Marker create(double latitude, double longitude, String name)
    {
        return new Marker(new LatLng(latitude, longitude), name);
    }
}
