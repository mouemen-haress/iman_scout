package com.example.scoutchallenge.backend;

import com.example.scoutchallenge.interfaces.CallBack;

import org.json.JSONObject;

public class MainManager {
    public void doCallBack(CallBack callBack, String data) {
        if (callBack != null) {
            callBack.onResult(data);
        }
    }

    public String getErrorMessage(JSONObject data) {
        if (data != null) {
            JSONObject error = data.optJSONObject("error");
            if (error != null) {
                return error.optString("message");
            }
        }
        return "";
    }
}
