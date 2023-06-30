package com.example.scoutchallenge.views.onsor_views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scoutchallenge.R;
import com.example.scoutchallenge.backend.ActivityManager;
import com.example.scoutchallenge.backend.BackendProxy;
import com.example.scoutchallenge.conponents.HeadComponents;
import com.example.scoutchallenge.conponents.MTextView;
import com.example.scoutchallenge.conponents.MyRecyclerView;
import com.example.scoutchallenge.conponents.popups.KoranNotePopup;
import com.example.scoutchallenge.conponents.popups.user_popup.StatisticPopup;
import com.example.scoutchallenge.helpers.JsonHelper;
import com.example.scoutchallenge.helpers.RecyclerItemClickListener;
import com.example.scoutchallenge.interfaces.CallBack;
import com.example.scoutchallenge.interfaces.DidOnFinishWork;
import com.example.scoutchallenge.interfaces.DidOnTap;
import com.example.scoutchallenge.interfaces.OnCellSwipe;
import com.example.scoutchallenge.interfaces.OnObjectCallBack;
import com.example.scoutchallenge.utils.LocalStorage;
import com.example.scoutchallenge.views.HeadView;
import com.example.scoutchallenge.views.HomeView;
import com.example.scoutchallenge.views.cells.users_cells.UserStatisticActivityCell;

import org.json.JSONArray;
import org.json.JSONObject;

public class StatisticOnsorView extends HeadView implements DidOnTap, OnCellSwipe, DidOnFinishWork {

    protected MTextView mHeaderText;
    protected MyRecyclerView mUserList;
    protected StatisticOnsorView.ListAdapter mAdapter;
    protected LinearLayoutManager mMyManager;
    public JSONObject mRelatedObj;
    protected JSONObject mRelatedCategory;
    protected JSONArray mCurrentUserList;
    protected JSONArray mOtherUserList;
    public OnObjectCallBack mDelegate;

    JSONArray mActivitiesList;

    public StatisticOnsorView() {
        // Required empty public constructor
    }

    public static HomeView newInstance(String param1, String param2) {
        HomeView fragment = new HomeView();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    @SuppressLint({"MissingPermission", "ResourceAsColor"})

    public void init(Context ctx, View view) {
        super.init(ctx, view);
        showTabBar(true);
        showHeader();
        setHeadBtn(getImage("add_icon"));

        mAdapter = new StatisticOnsorView.ListAdapter();

        mMyManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        mHeaderText = new MTextView(ctx);
        mHeaderText.setText("هنا كنا و ما كنا");
        mHeaderText.setMultiLine(3);
        mHeaderText.getLabel().setHorizontallyScrolling(true);
        mHeaderText.setTextColor(getColor(R.color.headColor));
        mHeaderText.setBackground(getDrawable(GradientDrawable.RECTANGLE, R.color.white, dpToPx(10), 2, getColor(R.color.headColor)));
        mRootView.addView(mHeaderText);

        mUserList = new MyRecyclerView(ctx);
        mUserList.setAdapter(mAdapter);
        mUserList.setLayoutManager(mMyManager);
        mUserList.setOnSwipeDelegate(this);

        mUserList.mMyList.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), mUserList.mMyList, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
        mRootView.addView(mUserList);

        mDelegate = new OnObjectCallBack() {
            @Override
            public void OnObject(JSONObject object) {
                if (object != null) {
                    JSONObject userData = null;

                    JSONArray users = object.optJSONArray("users");
                    if (users != null) {
                        for (int i = 0; i < users.length(); i++) {
                            JSONObject currentObj = users.optJSONObject(i);
                            if (currentObj != null) {
                                if (currentObj.optString("userId").equalsIgnoreCase(LocalStorage.getString(LocalStorage.SELF_ID))) {
                                    userData = currentObj;
                                    break;
                                }
                            }
                        }
                    }

                    if (userData != null) {
                        KoranNotePopup koranNotePopup = new KoranNotePopup(getContext());
                        koranNotePopup.setData(userData);
                        koranNotePopup.mDelegate = new CallBack() {
                            @Override
                            public void onResult(String response) {
                                hidePopup();
                            }
                        };
                        showPopup(koranNotePopup);
                    }
                }
            }
        };

        setHeadBtn(getImage("analysis"));
        layoutViews();
    }

    private void updateUserLocaly(JSONObject userData, String current, String rate, String next) {
        JsonHelper.put(userData, "note", current);
        JsonHelper.put(userData, "rate", rate);
        JsonHelper.put(userData, "next", next);
        mAdapter.notifyDataSetChanged();

    }


    private void layoutViews() {
        int logoSize = dpToPx(100);
        int headerSize = logoSize / 2 + dpToPx(16);
        int margin = dpToPx(16);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = getHeadSize() + margin;
        params.setMarginEnd(margin);
        params.setMarginStart(margin);
        params.bottomMargin = getBottomNavHeightWithMargin() + margin / 2;
        mHeaderText.getLabel().setGravity(Gravity.CENTER);
//        mHeaderText.setMinimumHeight(headerSize);
        mHeaderText.setLayoutParams(params);

        mHeaderText.measure(0, 0);
        int textHeight = mHeaderText.getMeasuredHeight();

        params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.topMargin = getHeadSize() + textHeight + margin;
        params.bottomMargin = getBottomNavHeightWithMargin() + margin;
        mUserList.setLayoutParams(params);

    }

    @Override
    public void FillPassedData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String dataString = bundle.getString("categoryObj");
            JSONObject obj = JsonHelper.parse(dataString);
            if (obj != null) {
                mRelatedObj = obj;
            }

            fillRelatedGeneralCategory(bundle);
        }

    }

    private void fillRelatedGeneralCategory(Bundle bundle) {
        String dataString = bundle.getString("categoryObj");
        JSONObject obj = JsonHelper.parse(dataString);
        if (obj != null) {
            mRelatedCategory = obj;
        }
    }

    @Override
    public void onTap(HeadComponents view) {

    }

    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }

    @Override
    public void onSwipe(RecyclerView.ViewHolder viewHolder, int swipeDir) {
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onFinishWork(JSONArray array) {

    }

    public void handleIconCellClick(JSONObject data) {

    }

    public class ListAdapter extends RecyclerView.Adapter<StatisticOnsorView.ListAdapter.CellViewHolder> {


        JSONArray mDataSource;

        @Override
        public int getItemViewType(int position) {

            return 1;
        }

        @NonNull
        @Override
        public StatisticOnsorView.ListAdapter.CellViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            StatisticOnsorView.ListAdapter.CellViewHolder viewHolder = null;
            switch (viewType) {


            }
            UserStatisticActivityCell userStatisticActivityCell = new UserStatisticActivityCell(getContext());
            viewHolder = new StatisticOnsorView.ListAdapter.CellViewHolder(userStatisticActivityCell);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull StatisticOnsorView.ListAdapter.CellViewHolder holder, int position) {
            if (mDataSource != null) {
                JSONObject object = mDataSource.optJSONObject(position);
                if (mRelatedCategory != null) {
                    holder.mCell.isKoraanCell = mRelatedCategory.optString("title").
                            equalsIgnoreCase(ActivityManager.QURAAN_CATEGORY);
                }
                holder.mCell.mDelegate = mDelegate;
                holder.mCell.setData(object);

            }
        }

        @Override
        public int getItemCount() {
            if (mDataSource == null) {
                return 0;
            }
            return mDataSource.length();
        }


        public class CellViewHolder extends RecyclerView.ViewHolder {
            protected UserStatisticActivityCell mCell;

            public CellViewHolder(View itemView) {
                super(itemView);
                mCell = (UserStatisticActivityCell) itemView;
            }
        }

    }


    @Override
    public void onHeadBtnClicked(HeadComponents view1) {
        super.onHeadBtnClicked(view1);
        StatisticPopup statisticPopup = new StatisticPopup(getContext());
        if (mActivitiesList != null) {
            JSONObject data = new JSONObject();
            JsonHelper.put(data, "data", mActivitiesList);
            statisticPopup.setData(data);
        }
        showPopup(statisticPopup);


    }


}
