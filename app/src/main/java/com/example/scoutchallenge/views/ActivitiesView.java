package com.example.scoutchallenge.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scoutchallenge.R;
import com.example.scoutchallenge.backend.BackendProxy;
import com.example.scoutchallenge.conponents.HeadComponents;
import com.example.scoutchallenge.conponents.MDrawableEditText;
import com.example.scoutchallenge.conponents.MTextView;
import com.example.scoutchallenge.conponents.MyRecyclerView;
import com.example.scoutchallenge.conponents.popups.AddPopup;
import com.example.scoutchallenge.helpers.D;
import com.example.scoutchallenge.helpers.DateHelper;
import com.example.scoutchallenge.helpers.JsonHelper;
import com.example.scoutchallenge.helpers.RecyclerItemClickListener;
import com.example.scoutchallenge.helpers.StringHelper;
import com.example.scoutchallenge.helpers.Tools;
import com.example.scoutchallenge.interfaces.CallBack;
import com.example.scoutchallenge.interfaces.DidOnTap;
import com.example.scoutchallenge.interfaces.OnCellSwipe;

import org.json.JSONArray;
import org.json.JSONObject;

public class ActivitiesView extends HeadView implements DidOnTap, OnCellSwipe {

    protected MyRecyclerView mAvtivitiesList;
    protected ListAdapter mAdapter;
    protected LinearLayoutManager mMyManager;
    private JSONObject mRelatedCategoryObj;

    public ActivitiesView() {
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

        mAdapter = new ListAdapter();

        mMyManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        mAvtivitiesList = new MyRecyclerView(ctx);
        mAvtivitiesList.setAdapter(mAdapter);
        mAvtivitiesList.setOnSwipeDelegate(this);
        mAvtivitiesList.setLayoutManager(mMyManager);
        mAvtivitiesList.mMyList.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), mAvtivitiesList.mMyList, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                JSONObject currentObj = (JSONObject) mAdapter.mDataSource.opt(position);
                if (currentObj != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("activityObj", currentObj.toString());
                    pushView(R.id.ActivityuserListView, bundle);

                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
        mRootView.addView(mAvtivitiesList);


        fillActivities();
        layoutViews();
    }

    @Override
    public void FillPassedData() {
        String dataString = getArguments().getString("categoryObj");
        JSONObject obj = JsonHelper.parse(dataString);
        if (obj != null) {
            mRelatedCategoryObj = obj;
        }

    }

    private void fillActivities() {
        if (mRelatedCategoryObj != null) {
            String categoryId = mRelatedCategoryObj.optString("_id");
            if (!StringHelper.isNullOrEmpty(categoryId)) {
                BackendProxy.getInstance().mActivityManager.getActivitiesOfCategory(categoryId, new CallBack() {
                    @Override
                    public void onResult(String response) {
                        if (response != null) {
                            JSONObject respObject = JsonHelper.parse(response);
                            JSONArray result = respObject.optJSONArray("activities");
                            mAdapter.mDataSource = result;
                            runOnUiThread(() -> {
                                mAdapter.notifyDataSetChanged();

                            });
                        }

                    }
                });
            }
        }
//        JSONArray array = new JSONArray();
//
//        JSONObject obj = new JSONObject();
//        JsonHelper.put(obj, "date", "22/2/2022");
//        JsonHelper.put(obj, "description", "لقاء العقدة 6 ");
//        array.put(obj);
//
//        obj = new JSONObject();
//        JsonHelper.put(obj, "date", "22/2/2022");
//        JsonHelper.put(obj, "description", "لقاء العقدة 6 ");
//        array.put(obj);
//
//        obj = new JSONObject();
//        JsonHelper.put(obj, "date", "22/2/2022");
//        JsonHelper.put(obj, "description", "لقاء العقدة 6 ");
//        array.put(obj);
//
//        obj = new JSONObject();
//        JsonHelper.put(obj, "date", "22/2/2022");
//        JsonHelper.put(obj, "description", "لقاء العقدة 6 ");
//        array.put(obj);
//
//        mAdapter.mDataSource = array;
    }

    private void layoutViews() {
        int logoSize = dpToPx(100);
        int headerSize = logoSize / 2 + dpToPx(16);
        int margin = dpToPx(16);


        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.topMargin = getHeadSize();
        mAvtivitiesList.setLayoutParams(params);

    }

    @Override
    public void onTap(HeadComponents view) {
        switch (view.getId()) {
            case AddPopup.ADD_TALIAA_POPUP:
                MDrawableEditText mDrawableEditText = (MDrawableEditText) view;
                showLockedLoading();
                BackendProxy.getInstance().mActivityManager.addActivities(mDrawableEditText.getText(), D.t
                        , new CallBack() {
                            @Override
                            public void onResult(String response) {
                                runOnUiThread(() -> {

                                    hideLockedLoading();
                                    hidePopup();
                                    if (response != null) {
                                        fillActivities();
                                        mAdapter.notifyDataSetChanged();

                                    } else {
                                        showSimplePopup(getString(R.string.server_error));
                                    }

                                });

                            }
                        });

                break;
            default:
                break;
        }

    }

    @Override
    public void onSwipe(RecyclerView.ViewHolder viewHolder, int swipeDir) {
        Tools.showAskingPopup(getString(R.string.do_realy_want_do), new DidOnTap() {
            @Override
            public void onTap(HeadComponents view) {
                showLockedLoading();
                int position = viewHolder.getAdapterPosition();
                JSONObject activity = mAdapter.mDataSource.optJSONObject(position);
                if (activity != null) {
                    String activityId = activity.optString("_id");
                    BackendProxy.getInstance().mActivityManager.detletActivity(activityId, new CallBack() {
                        @Override
                        public void onResult(String response) {
                            runOnUiThread(() -> {
                                hideLockedLoading();
                                mAdapter.mDataSource.remove(position);
                                mAdapter.notifyDataSetChanged();
                            });
                        }
                    });
                }
            }
        }, new DidOnTap() {
            @Override
            public void onTap(HeadComponents view) {
                runOnUiThread(() -> {
                    mAdapter.notifyDataSetChanged();
                });
            }
        });

    }


    public class ListAdapter extends RecyclerView.Adapter<ActivitiesView.ListAdapter.CellViewHolder> {
//
//        protected static final int HORIZONTAL_CELL = 0;
//        protected static final int VERTICAL_CELL = 1;


        JSONArray mDataSource;

        @Override
        public int getItemViewType(int position) {
//            JSONObject mData = mDataSource.optJSONObject(position);
//            String sectionId = mData.optString("id");
//            switch (sectionId) {
//                case NEWS:
//                    return VERTICAL_CELL;
//                case TRENDING_STICKERS:
//
//                default:
//                    break;
//            }

            //just for avoid error , getItemViewType not used for now
            return 1;
        }

        @NonNull
        @Override
        public ActivitiesView.ListAdapter.CellViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ActivitiesView.ListAdapter.CellViewHolder viewHolder = null;
            switch (viewType) {
//                case VERTICAL_CELL:
//                    VerticalSectionViewCell viewCell = new VerticalSectionViewCell(parent.getContext());
//                    viewHolder = new CellViewHolder(viewCell);
////                    viewHolder.mCell.setBackgroundColor(Color.RED);
//                    break;
//                case HORIZONTAL_CELL:
//                    SectionViewCell horizontalCell = new SectionViewCell(parent.getContext());
//                    viewHolder = new CellViewHolder(horizontalCell);
//                    break;

            }
            ActivitiesView.ChallengeCell challengeCell = new ActivitiesView.ChallengeCell(getContext());
            viewHolder = new ActivitiesView.ListAdapter.CellViewHolder(challengeCell);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ActivitiesView.ListAdapter.CellViewHolder holder, int position) {
            if (mDataSource != null) {
                JSONObject object = mDataSource.optJSONObject(position);
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
            protected ActivitiesView.ChallengeCell mCell;

            public CellViewHolder(View itemView) {
                super(itemView);
                mCell = (ActivitiesView.ChallengeCell) itemView;
            }
        }
    }


    public class ChallengeCell extends HeadComponents {

        protected MTextView mDate;
        protected MTextView mDescription;


        public ChallengeCell(@NonNull Context context, AttributeSet attrs) {

            super(context, attrs);
        }

        public ChallengeCell(@NonNull Context context) {
            super(context);
        }

        @Override
        public void init(Context ctx, AttributeSet attrs) {
            super.init(ctx, attrs);

            mDate = new MTextView(ctx);
            mDate.setTextColor(getColor(R.color.headColor));
            addView(mDate);

            mDescription = new MTextView(ctx);
            addView(mDescription);

            setBackground(getDrawable(GradientDrawable.RECTANGLE, R.color.white, dpToPx(10), -1, -1));
            setElevation(12);
            layoutViews();
        }

        public void layoutViews() {
            int cellSize = dpToPx(150);
            int iconSize = dpToPx(80);
            int cellHeight = dpToPx(40);

            int margin = dpToPx(16);

            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMarginStart(margin / 2);
            params.topMargin = margin / 2;
            mDate.setLayoutParams(params);

            params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.topMargin = margin * 2;
            params.bottomMargin = margin;
            params.setMarginStart(margin);
            mDescription.setLayoutParams(params);

            params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.topMargin = margin;
            params.setMarginEnd(margin);
            params.setMarginStart(margin);
            setLayoutParams(params);
        }

        @Override
        public void setData(JSONObject data) {
            super.setData(data);

            if (data != null) {
                String date = data.optString("date");
                date = DateHelper.convertMongoDate(date);
                String Description = data.optString("title");
                mDate.setText(date);
                mDescription.setText(Description);
            }

        }
    }


    @Override
    public void onHeadBtnClicked(HeadComponents view1) {
        super.onHeadBtnClicked(view1);
        AddPopup addTaliaaPopup = new AddPopup(getContext());
        addTaliaaPopup.mNameEditText.setPlaceHolder(getString(R.string.add_new_activity));
        addTaliaaPopup.mDelegate = this;
        addTaliaaPopup.setOnTapListener(this);

        showPopup(addTaliaaPopup);
    }
}
