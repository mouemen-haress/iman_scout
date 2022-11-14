package com.example.scoutchallenge;

import android.app.Activity;
import android.app.Application;

import com.example.scoutchallenge.activities.MainActivity;

import org.json.JSONArray;

public class App extends Application {

    public Activity mMyActivity;
    private static App mSharedInstance;

    private App() {
    }

    public static App getSharedInstance() {
        if (mSharedInstance == null) {
            mSharedInstance = new App();
        }


        return mSharedInstance;
    }

    public MainActivity getMainActivity() {
        return (MainActivity) mMyActivity;
    }


}
