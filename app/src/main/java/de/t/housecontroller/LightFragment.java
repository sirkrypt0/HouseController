package de.t.housecontroller;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class LightFragment extends Fragment {

    private LinearLayout lightsLayout;
    private LinearLayout groupsLayout;
    private BridgeConnector bridgeConnector;

    public LightFragment() {
        // Required empty public constructor

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.bridgeConnector = BridgeConnector.getBridgeConnector();
        lightsLayout = this.getView().findViewById(R.id.lightsLayout);
        groupsLayout = this.getView().findViewById(R.id.groupsLayout);
    }

    @Override
    public void onStart() {
        super.onStart();

        InitLights();
        InitGroups();
    }

    void InitLights(){
        bridgeConnector.execute("update_lights");
        ArrayList<Light> lights = BridgeConnector.getLights();

        for(final Light l : lights){
            Switch b = new Switch(MainActivity.singleton);
            b.setText(l.l_name);
            b.setChecked(l.on);
            b.setHeight(64);
            b.setTextSize(20);
            b.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    new BridgeConnector().execute("turnLight", l.l_id, String.valueOf(isChecked));
                }
            });
            lightsLayout.addView(b);
        }

    }

    void InitGroups(){
        bridgeConnector.execute("update_groups");
        ArrayList<Group> groups = BridgeConnector.getGroups();

        for(final Group g : groups){
            Switch b = new Switch(MainActivity.singleton);
            b.setText(g.g_name);
            b.setChecked(g.on);
            b.setHeight(64);
            b.setTextSize(20);
            b.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    new BridgeConnector().execute("turnGroup", g.g_id, String.valueOf(isChecked));
                }
            });
            groupsLayout.addView(b);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_light, container, false);
    }
}
