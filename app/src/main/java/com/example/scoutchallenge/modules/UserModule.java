package com.example.scoutchallenge.modules;

import android.net.Uri;

import com.example.scoutchallenge.helpers.JsonHelper;
import com.example.scoutchallenge.helpers.StringHelper;

import org.json.JSONObject;

public class UserModule extends MemberModule {
    JSONObject data;
    protected String mFawjeName;
    protected String mFerkaName;
    protected JSONObject mTaliaa;
    protected Uri mImageUri;

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public void setTaliaaName(String mTaliaaName) {
        JsonHelper.put(mTaliaa, "name", mTaliaaName);
    }


    public String getTaliaaId() {
        return mTaliaa.optString("id");
    }

    public void setTaliaaId(String id) {
        if (mTaliaa == null) {
            mTaliaa = new JSONObject();
        }
        JsonHelper.put(mTaliaa, "id", id);
    }


    public String getTaliaaName() {
        return mTaliaa.optString("name");
    }


    public Uri getmImageUri() {
        return mImageUri;
    }

    public void setmImageUri(Uri mImageUri) {
        this.mImageUri = mImageUri;
    }

    public String getmProfileUrl() {
        return mProfileUrl;
    }

    public void setmProfileUrl(String mProfileUrl) {
        this.mProfileUrl = mProfileUrl;
    }

    protected String mProfileUrl;


    public String getmFawjeName() {
        return mFawjeName;
    }

    public void setmFawjeName(String mFawjeName) {
        this.mFawjeName = mFawjeName;
    }

    public String getmFerkaName() {
        return mFerkaName;
    }

    public void setmFerkaName(String mFerkaName) {
        this.mFerkaName = mFerkaName;
    }


    public String getmId() {
        String id = mId;
        if (StringHelper.isNullOrEmpty(mId)) {
            if(mData!=null){
                id = mData.optString("_id");
            }
        }
        return id;
    }
}
