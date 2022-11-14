package com.example.scoutchallenge;

import com.example.scoutchallenge.modules.MemberModule;
import com.example.scoutchallenge.utils.LocalStorage;

public class Core {
    public static final String USER = "user";
    public static final String LEADER = "leader";

    private static Core mSharedInstance;
    private String mCurrentUserPosition;

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
}
