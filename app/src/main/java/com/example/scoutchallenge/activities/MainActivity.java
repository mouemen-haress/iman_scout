package com.example.scoutchallenge.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.scoutchallenge.App;
import com.example.scoutchallenge.R;
import com.example.scoutchallenge.backend.BackendProxy;
import com.example.scoutchallenge.helpers.JsonHelper;
import com.example.scoutchallenge.helpers.PermissionsManager;
import com.example.scoutchallenge.helpers.Tools;
import com.example.scoutchallenge.interfaces.ArrayCallBack;
import com.example.scoutchallenge.interfaces.CallBack;
import com.example.scoutchallenge.utils.NotificationCenter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

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

    public void injectTaliaaUSerData(CallBack callBack) {
        BackendProxy.getInstance().mTaliaaManager.getTaliaaList(new ArrayCallBack() {
            @Override
            public void onResult(JSONArray response) {
                if (response != null) {
                    BackendProxy.getInstance().mUserManager.getAllUser(new ArrayCallBack() {
                        @Override
                        public void onResult(JSONArray array) {
                            if (response != null) {
                                JSONArray userList = BackendProxy.getInstance().mUserManager.mAllUserList;
                                JSONArray taliaaList = BackendProxy.getInstance().mTaliaaManager.mTaliaaList;
                                if (userList != null && taliaaList != null) {
                                    for (int i = 0; i < taliaaList.length(); i++) {
                                        JSONObject currentTaliaaObj = taliaaList.optJSONObject(i);
                                        if (currentTaliaaObj != null) {
                                            String taliaaId = currentTaliaaObj.optString("_id");
                                            JSONArray users = new JSONArray();
                                            JsonHelper.put(currentTaliaaObj, "users", users);
                                            for (int j = 0; j < userList.length(); j++) {
                                                JSONObject currentUSerObj = userList.optJSONObject(j);
                                                if (currentUSerObj != null) {
                                                    JSONObject taliaa = currentUSerObj.optJSONObject("taliaa");
                                                    if (taliaa != null) {
                                                        String currentUserTaliaaId = taliaa.optString("_id");
                                                        if (currentUserTaliaaId.equalsIgnoreCase(taliaaId)) {
                                                            users.put(currentUSerObj);
                                                        }
                                                    }
                                                }

                                            }
                                        }
                                    }
                                    if (callBack != null) {
                                        callBack.onResult("");
                                    }
                                    NotificationCenter.getInstance().fireNotificaion(NotificationCenter.USERS_LIST_UPDATED);

                                }
                            } else {
                                Tools.showSimplePopup(getString(R.string.server_error));
                                if (callBack != null) {
                                    callBack.onResult(null);
                                }
                            }
                        }
                    });
                } else {
                    Tools.showSimplePopup(getString(R.string.server_error));
                    if (callBack != null) {
                        callBack.onResult(null);
                    }
                }
            }
        });


    }

    public void injectTaliaaUSerDataLocaly(CallBack callBack) {
        JSONArray userList = BackendProxy.getInstance().mUserManager.mAllUserList;
        JSONArray taliaaList = BackendProxy.getInstance().mTaliaaManager.mTaliaaList;
        if (userList != null && taliaaList != null) {
            for (int i = 0; i < taliaaList.length(); i++) {
                JSONObject currentTaliaaObj = taliaaList.optJSONObject(i);
                if (currentTaliaaObj != null) {
                    String taliaaId = currentTaliaaObj.optString("_id");
                    JSONArray users = new JSONArray();
                    JsonHelper.put(currentTaliaaObj, "users", users);
                    for (int j = 0; j < userList.length(); j++) {
                        JSONObject currentUSerObj = userList.optJSONObject(j);
                        if (currentUSerObj != null) {
                            JSONObject taliaa = currentUSerObj.optJSONObject("taliaa");
                            if (taliaa != null) {
                                String currentUserTaliaaId = taliaa.optString("_id");
                                if (currentUserTaliaaId.equalsIgnoreCase(taliaaId)) {
                                    users.put(currentUSerObj);
                                }
                            }
                        }

                    }
                }
            }
        }


    }

    public NavHostFragment getNavHostFragment() {
        if (mNavHostFragment == null) {
            mNavHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        }
        return mNavHostFragment;
    }

    public NavController getNavController() {
        if (mNavController != null) {
            return mNavController;
        }
        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsManager.getInstance().handlePermissionResult(requestCode, permissions, grantResults);
    }
}