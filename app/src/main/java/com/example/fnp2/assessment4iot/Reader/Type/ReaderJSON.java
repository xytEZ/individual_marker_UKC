package com.example.fnp2.assessment4iot.Reader.Type;

import com.example.fnp2.assessment4iot.Exception.ReaderException;
import com.example.fnp2.assessment4iot.Helper.IUnaryFunction;
import com.example.fnp2.assessment4iot.Reader.IReaderDataFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xytez on 3/16/16.
 */
public final class ReaderJSON implements IReaderDataFormat<List<String>, String, String, JSONObject>
{
    @Override
    public List<String> read(String src, IUnaryFunction<String, JSONObject> fn)
            throws ReaderException
    {
        List<String>    outputDatas = new ArrayList<>();

        try
        {
            JSONObject  jsonResponse = new JSONObject(src);
            JSONArray   jsonMainNode = jsonResponse.optJSONArray("Users");
            int         jsonArrayLength = jsonMainNode.length();

            for (int i = 0; i < jsonArrayLength; ++i)
                outputDatas.add(fn.execute(jsonMainNode.getJSONObject(i)));
        }
        catch (final JSONException e)
        {
            throw new ReaderException("Error on the JSON Reader");
        }
        return outputDatas;
    }

    @Override
    public String getDataFormat() { return "JSON"; }
}
