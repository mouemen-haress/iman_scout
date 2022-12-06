package com.example.scoutchallenge.views;

import android.os.Bundle;

import com.example.scoutchallenge.backend.BackendProxy;
import com.example.scoutchallenge.helpers.DateHelper;
import com.example.scoutchallenge.helpers.JsonHelper;
import com.example.scoutchallenge.helpers.StringHelper;
import com.example.scoutchallenge.interfaces.CallBack;

import org.json.JSONArray;
import org.json.JSONObject;

public class TaliaaUserListView extends ActivityUserListView {

    @Override
    public void FillPassedData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String dataString = bundle.getString("relatedTaliaaObj");
            JSONObject obj = JsonHelper.parse(dataString);
            if (obj != null) {
                mRelatedObj = obj;
            }
        }
    }

    @Override
    protected void fillUsers() {
        JSONObject data = mRelatedObj;
        if (data != null) {
            JSONArray userList = data.optJSONArray("users");

            if (userList != null) {
                mCurrentUserList = userList;
                mAdapter.mDataSource = userList;
                mAdapter.notifyDataSetChanged();

            }

            String title = data.optString("name");
            mHeaderText.setText(title);
        }


    }

    @Override
    public void onFinishWork(JSONArray array) {
        if (array != null && mRelatedObj != null) {
            int arrayLenght = array.length();
            showLockedLoading();

            for (int i = 0; i < arrayLenght; i++) {
                String cuurentId = array.optString(i);
                if (!StringHelper.isNullOrEmpty(cuurentId)) {
                    String taliaaId = mRelatedObj.optString("_id");
                    if (!StringHelper.isNullOrEmpty(taliaaId)) {
                        int finalI = i;
                        hidePopup();

                        BackendProxy.getInstance().mTaliaaManager.addUserToTaliaa(cuurentId, taliaaId, new CallBack() {
                            @Override
                            public void onResult(String response) {
                                runOnUiThread(() -> {

                                    if (finalI == arrayLenght - 1) {
                                        hideLockedLoading();
                                    }
                                    if (response != null) {
                                        JSONArray lastUserArray = mRelatedObj.optJSONArray("users");
                                        JSONObject targetUser = BackendProxy.getInstance().mUserManager.getUserById(cuurentId);
                                        if (targetUser != null) {
                                            lastUserArray.put(targetUser);
                                        }
                                        fillUsers();


                                    }
                                });

                            }
                        });
                    }

                }
            }
        }
    }
}
