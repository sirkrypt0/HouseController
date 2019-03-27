package de.t.housecontroller;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class Light {

    public String l_id;
    public JSONObject jsonObject;
    public String l_name;
    public boolean on;

    public Light(JSONObject object){
        Iterator<String> it = object.keys();

        while(it.hasNext()){
            l_id = it.next();
            try {
                jsonObject = object.getJSONObject(l_id);
                l_name = jsonObject.getString("name");
                on = jsonObject.getJSONObject("state").getBoolean("on");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

}
