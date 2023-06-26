package com.example.scoutchallenge;

import android.app.Application;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.scoutchallenge.activities.MainActivity;

public class App extends Application {

    public MainActivity mMyActivity;
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

    public void popBackStack() {
        NavController navController = mMyActivity.getNavController();
        if (navController != null) {
            navController.popBackStack();
        }
    }
}
