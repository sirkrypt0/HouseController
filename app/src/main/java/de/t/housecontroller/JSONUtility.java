package de.t.housecontroller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class JSONUtility {


    public static ArrayList<String> JSONArrayToStringArray(JSONArray array) throws JSONException {
        ArrayList<String> list = new ArrayList<>();

        for(int i = 0; i < array.length(); i++){
            list.add(array.getString(i));
        }

        return list;
    }

    public static JSONObject getJSONFromStream(InputStream stream) throws IOException, JSONException {
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        br.close();

        String jsonString = sb.toString();
        System.out.println("getJSONFromStream: " + jsonString);

        return new JSONObject(jsonString);
    }

}
