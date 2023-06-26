package com.example.scoutchallenge.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.scoutchallenge.App;

public class LocalStorage {
    public static String USER_POSITION = "USER_POSITION";
    public static String FAWJ = "fawj";
    public static String SQUAD = "squad";
    public static String MOUFAWADIYEH = "moufawadiyeh";

    public static String SELF_ID = "self_id";


    public static void setString(String key, String value) {

        try {
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(App.getSharedInstance().mMyActivity);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(key, value);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void setInt(String key, int value) {

        try {
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(App.getSharedInstance().mMyActivity);
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt(key, value);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String getString(String key) {

        try {
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(App.getSharedInstance().mMyActivity);
            return settings.getString(key, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public static void clearAllLocalStorage() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(App.getSharedInstance().mMyActivity);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();
    }

}
