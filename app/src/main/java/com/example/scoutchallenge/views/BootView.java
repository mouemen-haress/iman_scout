package com.example.scoutchallenge.views;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scoutchallenge.App;
import com.example.scoutchallenge.R;
import com.example.scoutchallenge.backend.BackendProxy;
import com.example.scoutchallenge.helpers.JsonHelper;
import com.example.scoutchallenge.helpers.Tools;
import com.example.scoutchallenge.interfaces.ArrayCallBack;
import com.example.scoutchallenge.interfaces.CallBack;

import org.json.JSONArray;
import org.json.JSONObject;


public class BootView extends HeadView {


    private String mParam1;
    private String mParam2;

    public BootView() {
        // Required empty public constructor
    }

    @Override
    public void init(Context ctx, View view) {
        super.init(ctx, view);
        showLockedLoading();
        mRootView.setBackgroundColor(getColor(R.color.headColor));
        fetchData();
    }

    private void fetchData() {
        BackendProxy.getInstance().mTaliaaManager.getTaliaaList(new ArrayCallBack() {
            @Override
            public void onResult(JSONArray response) {
                if (response != null) {
                    BackendProxy.getInstance().mUserManager.getAllUser(new ArrayCallBack() {
                        @Override
                        public void onResult(JSONArray array) {
                            if (response != null) {
                                injectTaliaaUSerData();
                                pushAndSetRootView(R.id.bootView, R.id.homeView);
                            } else {
                                Tools.showSimplePopup(getString(R.string.server_error));

                            }
                        }
                    });
                } else {
                    Tools.showSimplePopup(getString(R.string.server_error));
                }
            }
        });
    }

    private void injectTaliaaUSerData() {
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

    public static BootView newInstance(String param1, String param2) {
        BootView fragment = new BootView();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }


}