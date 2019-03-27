package de.t.housecontroller;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class BridgeConnector extends AsyncTask<String,Void,Object> {

    public static BridgeConnector singleton;

    private static String USERNAME = "hsdu6ma3PSpVm3oOGaHs6Pq3RmsZwgXVbYZ6ilC6";
    private static String BRIDGE_ADDRESS = "http://192.168.178.73";
    private static String USER_ADDRESS = BRIDGE_ADDRESS+"/api/"+USERNAME;
    private static String LIGHTS_ADDRESS = USER_ADDRESS+"/lights";
    private static String GROUPS_ADDRESS = USER_ADDRESS+"/groups";

    public static ArrayList<Light> lights;
    public static ArrayList<Group> groups;

    public static BridgeConnector getBridgeConnector(){
        if(singleton == null)
            singleton = new BridgeConnector();
        return singleton;
    }

    public static ArrayList<Light> getLights(){
        return lights;
    }

    public static void turnLight(String id, String state){
        PUTonBridge(LIGHTS_ADDRESS+"/"+id+"/state", "{\"on\":"+state+"}");

        for(Light l : lights){
            if(l.l_id.equals(id)){
                l.on = Boolean.valueOf(state);
                break;
            }
        }
    }

    public static void updateLights(){
        JSONObject lightsJSON = GETonBridge(LIGHTS_ADDRESS);

        Iterator<String> it = lightsJSON.keys();
        ArrayList<Light> ls = new ArrayList<>();

        while(it.hasNext()){
            String l_id = it.next();
            try {
                Light l = new Light(lightsJSON.getJSONObject(l_id));
                ls.add(l);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        lights = ls;
    }

    public static void updateGroups(){
        JSONObject groupsJSON = GETonBridge(GROUPS_ADDRESS);

        Iterator<String> it = groupsJSON.keys();
        ArrayList<Group> gs = new ArrayList<>();

        while(it.hasNext()){
            String g_id = it.next();
            try {
                Group g = new Group(groupsJSON.getJSONObject(g_id));
                gs.add(g);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        groups = gs;
    }

    public static ArrayList<Group> getGroups(){
        return BridgeConnector.groups;
    }

    public static void turnGroup(String id, String state){
        PUTonBridge(GROUPS_ADDRESS+"/"+id+"/action", "{\"on\":"+state+"}");

        for(Group g : groups){
            if(g.g_id.equals(id)){
                g.on = Boolean.valueOf(state);
                break;
            }
        }
    }

    public static JSONObject GETonBridge(String ip){
        try {
            HttpURLConnection connection = (HttpURLConnection) (new URL(ip)).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            return JSONUtility.getJSONFromStream(connection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void PUTonBridge(String ip, String content){
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) (new URL(ip)).openConnection();
            connection.setRequestMethod("PUT");
            OutputStreamWriter out = new OutputStreamWriter(
                    connection.getOutputStream());
            out.write(content);
            out.close();
            connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Object doInBackground(String... strings) {
        switch (strings[0]){
            case "update_lights":
                updateLights();
            case "update_groups":
                updateGroups();
            case "turnLight":
                turnLight(strings[1],strings[2]);
            case "turnGroup":
                turnGroup(strings[1], strings[2]);
        }

        return null;
    }


}
