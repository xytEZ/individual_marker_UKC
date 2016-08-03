package com.example.fnp2.assessment4iot.Handler;

import com.example.fnp2.assessment4iot.Exception.ErrorContentException;
import com.example.fnp2.assessment4iot.Exception.NoURLException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by xytez on 3/16/16.
 */
public class URLConnectionHandler
{
    private URL             _url;
    private URLConnection   _urlConnection;
    private String          _content;
    private boolean         _error;

    public URLConnectionHandler()
    {
        _url = null;
        _urlConnection = null;
        _content = "";
        _error = true;
    }

    public URLConnectionHandler(URL url)
    {
        this();
        _url = url;
    }

    public void setURL(URL url)
    {
        _url = url;
        _error = false;
    }

    public void flushContent() { _content = ""; }

    public String getContent()
            throws ErrorContentException
    {
        if (_error == true)
            throw new ErrorContentException("Content may be corrupted");
        return _content;
    }

    public void initialize()
    {
        try
        {
            if (_url == null)
                throw new NoURLException();

            _urlConnection = _url.openConnection();
            _urlConnection.setDoOutput(true);
        }
        catch (final Exception e)
        {
            _error = true;
        }
    }

    public void readServerResponse()
    {
        BufferedReader reader = null;

        try
        {
            reader = new BufferedReader(new InputStreamReader(_urlConnection.getInputStream()));

            StringBuilder   sb = new StringBuilder();
            String          line;

            while ((line = reader.readLine()) != null)
                sb.append(line + '\n');
            _content = sb.toString();
        }
        catch (final Exception e)
        {
            _error = true;
        }
        finally
        {
            try
            {
                reader.close();
            }
            catch (final Exception e)
            {
                _error = true;
            }
        }
    }
}
