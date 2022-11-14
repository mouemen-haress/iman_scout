package com.example.scoutchallenge.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.scoutchallenge.App;
import com.example.scoutchallenge.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    protected BottomNavigationView mBottomNav;
    protected NavHostFragment mNavHostFragment;
    protected NavController mNavController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.normalTheme);

        setContentView(R.layout.activity_main);
        App.getSharedInstance().mMyActivity = this;


        // force arabic direction
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);


        mNavHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        mNavController = mNavHostFragment.getNavController();

        mBottomNav = findViewById(R.id.tab_Bar);
        NavigationUI.setupWithNavController(mBottomNav, mNavController);
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
    }

    public void pushView(int target) {
        mNavController.navigate(target);
    }
}