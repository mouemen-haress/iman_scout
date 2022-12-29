package com.example.scoutchallenge.views;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scoutchallenge.App;
import com.example.scoutchallenge.Core;
import com.example.scoutchallenge.R;
import com.example.scoutchallenge.backend.BackendProxy;
import com.example.scoutchallenge.helpers.JsonHelper;
import com.example.scoutchallenge.helpers.Tools;
import com.example.scoutchallenge.interfaces.ArrayCallBack;
import com.example.scoutchallenge.interfaces.CallBack;
import com.example.scoutchallenge.models.MemberModule;

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
        String position = Core.getInstance().getmCurrentUserPosition();
        switch (position) {
            case MemberModule.LEADER:
                fetchLeaderData();
                break;

            case MemberModule.HELPING_LEADER:
                fetchHelpingLeaderData();
                break;
        }
    }

    private void fetchHelpingLeaderData() {

        App.getSharedInstance().getMainActivity().injectTaliaaUSerData(new CallBack() {
            @Override
            public void onResult(String response) {
                if (response != null) {
                    pushAndSetRootView(R.id.bootView, R.id.homeView);
                } else {
                    Tools.showSimplePopup(getString(R.string.server_error));
                }
            }
        });

    }

    private void fetchLeaderData() {
        App.getSharedInstance().getMainActivity().injectTaliaaUSerData(new CallBack() {
            @Override
            public void onResult(String response) {
                if (response != null) {
                    pushAndSetRootView(R.id.bootView, R.id.homeView);
                } else {
                    Tools.showSimplePopup(getString(R.string.server_error));
                }
            }
        });
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