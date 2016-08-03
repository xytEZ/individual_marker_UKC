package com.example.fnp2.assessment4iot.MarkerGoogleMap;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by xytez on 3/17/16.
 */
public final class Marker
{
    private LatLng  _position;
    private String  _name;

    public Marker(LatLng position, String name)
    {
        _position = position;
        _name = name;
    }

    public LatLng getPosition() { return _position; }
    public String getName() { return _name; }
}
