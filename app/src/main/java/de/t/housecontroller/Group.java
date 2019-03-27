package de.t.housecontroller;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class Group {

    public String g_id;
    public String g_name;
    public JSONObject jsonObject;
    public boolean on;
    public ArrayList<String> lamps;

    public Group(JSONObject array){
        Iterator<String> it = array.keys();

        while(it.hasNext()){
            g_id = it.next();
            try {
                jsonObject = array.getJSONObject(g_id);
                on = jsonObject.getJSONObject("action").getBoolean("on");
                g_name = jsonObject.getString("name");
                lamps = JSONUtility.JSONArrayToStringArray(jsonObject.getJSONArray("lamps"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
