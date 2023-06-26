package com.example.scoutchallenge.views.onsor_views;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.example.scoutchallenge.R;
import com.example.scoutchallenge.helpers.RecyclerItemClickListener;
import com.example.scoutchallenge.views.HomeView;

import org.json.JSONObject;

public class OnsorHomeView extends HomeView {

    @Override
    public void init(Context ctx, View view) {
        super.init(ctx, view);
        mCategoriesList.removeOnItemTouchListener(mRecyclerListener);
        mCategoriesList.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), mCategoriesList, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                JSONObject currentObj = (JSONObject) mAdapter.mDataSource.opt(position);
                Bundle bundle = new Bundle();
                bundle.putString("categoryObj", currentObj.toString());
                if (currentObj != null) {
                    pushView(R.id.statisticOnsorView, bundle);
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        }));


    }


}
