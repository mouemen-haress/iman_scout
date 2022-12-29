package com.example.scoutchallenge.utils;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavBackStackEntry;

import com.example.scoutchallenge.App;
import com.example.scoutchallenge.views.HeadView;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import kotlin.collections.ArrayDeque;

public class NotificationCenter {

    public final static String USERS_LIST_UPDATED = "USERS_LIST_UPDATED";
    static NotificationCenter mInstance;

    private NotificationCenter() {

    }

    public static NotificationCenter getInstance() {
        if (mInstance == null) {
            mInstance = new NotificationCenter();
        }
        return mInstance;
    }

    public void fireNotificaion(String type) {
        App.getSharedInstance().getMainActivity().runOnUiThread(() -> {

        List<Fragment> fragmentsArray = App.getSharedInstance().getMainActivity().getNavHostFragment().getChildFragmentManager().getFragments();
        for (Fragment fragment : fragmentsArray) {
            if (fragment != null) {
                HeadView currentFetchedView = (HeadView) fragment;
                if (currentFetchedView != null) {
                    currentFetchedView.onNotification(type, new JSONObject());
                }
            }
        }
        });

    }

}
