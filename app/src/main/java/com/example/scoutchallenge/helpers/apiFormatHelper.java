package com.example.scoutchallenge.helpers;

import org.json.JSONException;
import org.json.JSONObject;

public class apiFormatHelper {
    public static String formatApi(Object response) {
        if (response != null) {
            if (response instanceof String) {
                try {
                    JSONObject jb = JsonHelper.parse((String) response);
                    if (jb != null) {
                        return jb.toString(3);
                    } else {
                        return "Parsing Not Completed";
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    return ((JSONObject) response).toString(3);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return "Parsing Not Completed";
    }

}
