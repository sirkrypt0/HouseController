package de.t.housecontroller;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;


public class WeatherFragment extends Fragment {

    public WeatherFragment() {
        // Required empty public constructor
    }

    private void SetupCurrentView(JSONObject current){
        if(current == null){
            return;
        }

        View view = getView();

        try {
            ((TextView) view.findViewById(R.id.temperatureToday)).setText(current.getString("temperature"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateView();

    }

    private void updateView(){
        JSONObject forecast = WeatherConnector.getForecast();
        //System.out.println("Got forecast: " + forecast);
        JSONObject current = null;
        try {
            current = forecast.getJSONObject("currently");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        SetupCurrentView(current);
    }

}
