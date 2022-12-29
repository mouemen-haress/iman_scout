package com.example.scoutchallenge;

import com.example.scoutchallenge.models.MemberModule;
import com.example.scoutchallenge.utils.LocalStorage;

import org.json.JSONObject;

public class Core {


    private static Core mSharedInstance;
    private String mCurrentUserPosition = "";
    protected JSONObject mCurentMemberObject;

    public JSONObject getCurentMemberObject() {
        return mCurentMemberObject;
    }

    public void setCurentMemberObject(JSONObject mCurentMemberObject) {
        this.mCurentMemberObject = mCurentMemberObject;
    }

    public static Core getInstance() {
        if (mSharedInstance == null) {
            mSharedInstance = new Core();
        }
        return mSharedInstance;
    }

    public void setmCurrentUserPosition(String position) {
        mCurrentUserPosition = position;
        LocalStorage.setString(LocalStorage.USER_POSITION, position);
    }

    public String getmCurrentUserPosition() {
        return mCurrentUserPosition;
    }

    public boolean isLeader() {
        return mCurrentUserPosition.equalsIgnoreCase(MemberModule.LEADER);
    }

    public boolean isHelpingLeader() {
        return mCurrentUserPosition.equalsIgnoreCase(MemberModule.HELPING_LEADER);
    }
}
