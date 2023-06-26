package com.example.scoutchallenge.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scoutchallenge.R;
import com.example.scoutchallenge.backend.BackendProxy;
import com.example.scoutchallenge.conponents.HeadComponents;
import com.example.scoutchallenge.conponents.Loader;
import com.example.scoutchallenge.conponents.MImageComponent;
import com.example.scoutchallenge.conponents.MTextView;
import com.example.scoutchallenge.helpers.RecyclerItemClickListener;
import com.example.scoutchallenge.helpers.StringHelper;
import com.example.scoutchallenge.helpers.Tools;
import com.example.scoutchallenge.interfaces.ArrayCallBack;

import org.json.JSONArray;
import org.json.JSONObject;


public class HomeView extends HeadView {
    protected RecyclerView mCategoriesList;
    protected ListAdapter mAdapter;
    protected GridLayoutManager mMyManager;
    public RecyclerView.OnItemTouchListener mRecyclerListener;
    protected Loader mLoader;

    int mCellSize = dpToPx(150);

    public HomeView() {
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
        setHeadBtn(getImage("qr_code"));

        mAdapter = new ListAdapter();


        mMyManager = new GridLayoutManager(getContext(), RecyclerView.VERTICAL);
        mMyManager.setSpanCount(2);
        mCategoriesList = new RecyclerView(ctx);
        mCategoriesList.setAdapter(mAdapter);
        mCategoriesList.setLayoutManager(mMyManager);
        mRecyclerListener = new RecyclerItemClickListener(getContext(), mCategoriesList, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                JSONObject currentObj = (JSONObject) mAdapter.mDataSource.opt(position);
                Bundle bundle = new Bundle();
                bundle.putString("categoryObj", currentObj.toString());
                if (currentObj != null) {
                    pushView(R.id.activitiesView, bundle);
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });

        mCategoriesList.addOnItemTouchListener(mRecyclerListener);
        mCellSize = getBestCellSizing(dpToPx(16), 2);
        mRootView.addView(mCategoriesList);

        mLoader = new Loader(ctx);
        mRootView.addView(mLoader);

        fillCategories();
        layoutViews();
    }


    private void fillCategories() {
        mLoader.showLoader();
        JSONArray localCategories = BackendProxy.getInstance().mActivityManager.mCategoriesArray;
        if (localCategories != null && localCategories.length() > 0) {
            refreshAdapter(localCategories);

        }

        BackendProxy.getInstance().mActivityManager.getSquadCategories(new ArrayCallBack() {
            @Override
            public void onResult(JSONArray array) {
                if (array != null) {
                    refreshAdapter(array);
                }

            }
        });
    }

    private void layoutViews() {
        int logoSize = dpToPx(100);
        int headerSize = logoSize / 2 + dpToPx(16);
        int margin = dpToPx(16);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.topMargin = getHeadSize();
        params.setMarginEnd(margin);
        params.bottomMargin = Tools.getBottomNavHeightWithMargin()+margin;
        mCategoriesList.setLayoutParams(params);

        params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        mLoader.setLayoutParams(params);

    }

    private int getBestCellSizing(int margin, int column) {
        int screenWidth = getScreenWidth(isPortrait());
        screenWidth = screenWidth - ((column + 1) * margin);
        return (screenWidth - dpToPx(2)) / 2;
    }


    public void refreshAdapter(JSONArray array) {
        mAdapter.mDataSource = array;
        runOnUiThread(() -> {
            mLoader.hideLoader();
            mAdapter.notifyDataSetChanged();
        });
    }

    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.CellViewHolder> {
//
//        protected static final int HORIZONTAL_CELL = 0;
//        protected static final int VERTICAL_CELL = 1;


        public JSONArray mDataSource;

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
        public CellViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            CellViewHolder viewHolder = null;
            CategoryCell challengeCell = new CategoryCell(getContext());
            viewHolder = new CellViewHolder(challengeCell);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull CellViewHolder holder, int position) {
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
            protected CategoryCell mCell;

            public CellViewHolder(View itemView) {
                super(itemView);
                mCell = (CategoryCell) itemView;
            }
        }
    }


    public class CategoryCell extends HeadComponents {

        protected MImageComponent mIcon;
        protected MTextView mName;


        public CategoryCell(@NonNull Context context, AttributeSet attrs) {

            super(context, attrs);
        }

        public CategoryCell(@NonNull Context context) {
            super(context);
        }

        @Override
        public void init(Context ctx, AttributeSet attrs) {
            super.init(ctx, attrs);

            mIcon = new MImageComponent(ctx);
            mIcon.setImageResource(getImage("koran"));
            addView(mIcon);

            mName = new MTextView(ctx);
            mName.setTextSize(dpToPx(8));
            mName.setTextColor(getColor(R.color.headColor));
            addView(mName);

            setBackground(getDrawable(GradientDrawable.RECTANGLE, R.color.white, dpToPx(10), 2, getColor(R.color.headColor)));
            layoutViews();
        }

        public void layoutViews() {

            int iconSize = dpToPx(50);
            int margin = dpToPx(16);


            LayoutParams params = new LayoutParams(iconSize, iconSize);
            params.gravity = Gravity.CENTER;
            mIcon.setLayoutParams(params);

            params = new LayoutParams(mCellSize, ViewGroup.LayoutParams.MATCH_PARENT);
            mName.getLabel().setGravity(Gravity.CENTER);
            params.bottomMargin = margin / 2;
            params.topMargin = iconSize + (mCellSize / 2) - iconSize / 2;
            mName.setLayoutParams(params);

            params = new LayoutParams(mCellSize, mCellSize);
            params.topMargin = margin;
            params.setMarginStart(margin);
            setLayoutParams(params);
        }

        @Override
        public void setData(JSONObject data) {
            super.setData(data);

            if (data != null) {
                String name = data.optString("title");
                String iconLink = data.optString("iconLink");
                if (!StringHelper.isNullOrEmpty(iconLink)) {
                    mIcon.setImageResource(getImage(iconLink));
                }
                mName.setText(name);
            }

        }
    }


    @Override
    public void onHeadBtnClicked(HeadComponents view1) {
        super.onHeadBtnClicked(view1);
        int index = view1.mIndex;
        if (index == ACTION_BTN) {
            pushView(R.id.scanView);
        }
    }

    @Override
    public void onNotification(String notificationType, JSONObject data) {
        super.onNotification(notificationType, data);
    }
}

