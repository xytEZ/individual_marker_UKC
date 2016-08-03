package com.example.fnp2.assessment4iot.MarkerGoogleMap;

import android.os.AsyncTask;

import com.example.fnp2.assessment4iot.Exception.ErrorContentException;
import com.example.fnp2.assessment4iot.Exception.ReaderException;
import com.example.fnp2.assessment4iot.Handler.URLConnectionHandler;
import com.example.fnp2.assessment4iot.Helper.IUnaryFunction;
import com.example.fnp2.assessment4iot.Reader.IReaderDataFormat;
import com.example.fnp2.assessment4iot.Reader.Type.ReaderJSON;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

/**
 * Created by xytez on 3/16/16.
 */
public final class MarkerAssignator extends AsyncTask<Void, Void, List<String>>
{
    private static final String     URL_SERVER = "https://www.cs.kent.ac.uk/people/staff/iau/LocalUsers.php";
    private static final double     CENTRAL_MARKER_LATITUDE = 51.297282384433345;
    private static final double     CENTRAL_MARKER_LONGITUDE = 1.0697400569915771;
    private static final String     CENTRAL_MARKER_NAME = "Senate Building";

    private URLConnectionHandler    _urlConnectionHandler;
    private IReaderDataFormat       _readerDataFormat;
    private MarkerFactory           _markerFactory;
    private Marker                  _centralMarker;
    private GoogleMap               _mMap;

    public MarkerAssignator(GoogleMap googleMap)
    {
        _urlConnectionHandler = new URLConnectionHandler();
        _readerDataFormat = new ReaderJSON();
        _markerFactory = new MarkerFactory();
        _centralMarker = new Marker(new LatLng(CENTRAL_MARKER_LATITUDE,
                                                CENTRAL_MARKER_LONGITUDE),
                                    CENTRAL_MARKER_NAME);
        _mMap = googleMap;
        _mMap.addMarker(new MarkerOptions()
                .position(_centralMarker.getPosition())
                .title(_centralMarker.getName()));
    }

    public Marker getCentralMarker() { return _centralMarker; }

    @Override
    protected List<String> doInBackground(Void... params)
    {
        List<String>    users = null;

        try
        {
            _urlConnectionHandler.setURL(new URL(URL_SERVER));
            _urlConnectionHandler.initialize();
            _urlConnectionHandler.readServerResponse();
            users = readDataFromWebServer();
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }
        return users;
    }

    @SuppressWarnings("unchecked")
    private List<String> readDataFromWebServer()
            throws ErrorContentException,
                    ReaderException
    {
        return (List<String>)_readerDataFormat.read(_urlConnectionHandler.getContent(),
                new IUnaryFunction<String, JSONObject>()
                {
                    @Override
                    public String execute(JSONObject param)
                    {
                        String name = param.optString("name").toString();
                        String longitude = param.optString("lon").toString();
                        String latitude = param.optString("lat").toString();

                        return name + ',' + longitude + ',' + latitude;
                    }
                });
    }

    @Override
    protected void onPostExecute(List<String> users)
    {
        Iterator<String> iterator = users.iterator();

        while (iterator.hasNext())
            setMarkerInGoogleMap(iterator.next().split(","));
    }

    private void setMarkerInGoogleMap(String[] userInformations)
    {
        String      userName = userInformations[0];
        double      userLongitude = Double.parseDouble(userInformations[1]);
        double      userLatitude = Double.parseDouble(userInformations[2]);
        Marker      marker = _markerFactory.create(userLatitude, userLongitude, userName);

        _mMap.addMarker(new MarkerOptions()
                            .position(marker.getPosition())
                            .title(marker.getName())
                            .icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
    }
}
