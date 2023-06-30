package com.example.scoutchallenge.views;

import static com.example.scoutchallenge.views.TaliaaView.OTEHER_TALIAA;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.example.scoutchallenge.App;
import com.example.scoutchallenge.R;
import com.example.scoutchallenge.backend.BackendProxy;
import com.example.scoutchallenge.conponents.HeadComponents;
import com.example.scoutchallenge.helpers.JsonHelper;
import com.example.scoutchallenge.helpers.StringHelper;
import com.example.scoutchallenge.helpers.Tools;
import com.example.scoutchallenge.interfaces.CallBack;
import com.example.scoutchallenge.interfaces.DidOnTap;
import com.example.scoutchallenge.models.MemberModule;
import com.example.scoutchallenge.utils.NotificationCenter;

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
    public void onSwipe(RecyclerView.ViewHolder viewHolder, int swipeDir) {
        int position = viewHolder.getAdapterPosition();
        JSONObject user = mAdapter.mDataSource.optJSONObject(position);

        if (mRelatedObj != null) {
            String taliaaName = mRelatedObj.optString("name");
            if (taliaaName.equalsIgnoreCase(OTEHER_TALIAA)) {
                showToast(getString(R.string.can_not_delete_from_this_taliaa));
                mAdapter.notifyDataSetChanged();
                return;
            }
        }
        Tools.showAskingPopup(getString(R.string.do_realy_want_do), new DidOnTap() {
            @Override
            public void onTap(HeadComponents view) {
                if (user != null) {
                    MemberModule userModule = new MemberModule();
                    userModule.setData(user);
                    String id = userModule.getId();
                    showLockedLoading();


                }
            }
        }, new

                DidOnTap() {
                    @Override
                    public void onTap(HeadComponents view) {
                        runOnUiThread(() -> {
                            mAdapter.notifyDataSetChanged();
                        });
                    }
                });


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


                    }

                }
            }
        }
    }

    private void updateRelatedObject() {

    }


    @Override
    public void onNotification(String notificationType, JSONObject data) {
        super.onNotification(notificationType, data);
        if (notificationType.equalsIgnoreCase(NotificationCenter.USERS_LIST_UPDATED)) {
            updateRelatedObject();
            fillUsers();
        }
    }


}
