package com.example.scoutchallenge.models;

import com.example.scoutchallenge.helpers.JsonHelper;

import org.json.JSONObject;

public class TaliaaModel {
    public static final String OTHER = "أخرى";

    public JSONObject mData;
    protected String name;
    protected String _id;

    public String getName() {
        if (mData != null) {
            return mData.optString("name");
        }
        return name;
    }

    public void setName(String name) {
        if (mData != null) {
            JsonHelper.put(mData, "name", name);
        }
        this.name = name;
    }

    public String get_id() {
        if(mData!=null){
            return mData.optString("_id");
        }
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
