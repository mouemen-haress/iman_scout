package com.example.scoutchallenge.views;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scoutchallenge.App;
import com.example.scoutchallenge.R;
import com.example.scoutchallenge.backend.BackendProxy;
import com.example.scoutchallenge.conponents.HeadComponents;
import com.example.scoutchallenge.conponents.MDrawableEditText;
import com.example.scoutchallenge.conponents.MTextView;
import com.example.scoutchallenge.conponents.MyRecyclerView;
import com.example.scoutchallenge.conponents.popups.AddPopup;
import com.example.scoutchallenge.helpers.RecyclerItemClickListener;
import com.example.scoutchallenge.helpers.StringHelper;
import com.example.scoutchallenge.helpers.Tools;
import com.example.scoutchallenge.interfaces.CallBack;
import com.example.scoutchallenge.interfaces.DidOnTap;
import com.example.scoutchallenge.interfaces.OnCellSwipe;
import com.example.scoutchallenge.utils.NotificationCenter;

import org.json.JSONArray;
import org.json.JSONObject;

public class TaliaaView extends HeadView implements OnCellSwipe, DidOnTap {
    public static final String OTEHER_TALIAA = "أخرى";
    protected MyRecyclerView mTaliaaList;
    protected TaliaaView.ListAdapter mAdapter;
    protected LinearLayoutManager mMyManager;

    public void init(Context ctx, View view) {
        super.init(ctx, view);
        showTabBar(true);
        showHeader();
        setHeadBtn(getImage("add_icon"));

        mAdapter = new TaliaaView.ListAdapter();

        mMyManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        mTaliaaList = new MyRecyclerView(ctx);
        mTaliaaList.setAdapter(mAdapter);
        mTaliaaList.setLayoutManager(mMyManager);

        mTaliaaList.mMyList.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), mTaliaaList.mMyList, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                JSONObject currentObj = (JSONObject) mAdapter.mDataSource.opt(position);
                JSONArray taliaaList = BackendProxy.getInstance().mTaliaaManager.mTaliaaList;
                if (taliaaList != null && currentObj != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("relatedTaliaaObj", currentObj.toString());
                    pushView(R.id.taliaaUserListView, bundle);
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
        mTaliaaList.setOnSwipeDelegate(this);
        mRootView.addView(mTaliaaList);

        fillDate();
        layoutViews();
    }

    private void fillDate() {
        JSONArray array = BackendProxy.getInstance().mTaliaaManager.mTaliaaList;
        if (array != null) {
            mAdapter.mDataSource = array;
            mAdapter.notifyDataSetChanged();
        }
    }

    private void layoutViews() {
        int logoSize = dpToPx(100);
        int headerSize = logoSize / 2 + dpToPx(16);


        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(logoSize, logoSize);
        params.topMargin = headerSize - logoSize / 2;
        params.gravity = Gravity.CENTER_HORIZONTAL;
        mActionBtn.setLayoutParams(params);


        params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.topMargin = headerSize + logoSize / 2;
        params.bottomMargin = dpToPx(getBottomNavHeight());
        mTaliaaList.setLayoutParams(params);

    }

    @Override
    public void onSwipe(RecyclerView.ViewHolder viewHolder, int swipeDir) {
        int position = viewHolder.getAdapterPosition();
        JSONObject taliaa = mAdapter.mDataSource.optJSONObject(position);
        String taliaaName = taliaa.optString("name");
        if (taliaaName.equalsIgnoreCase(OTEHER_TALIAA)) {
            showToast(getString(R.string.can_not_delete_this_taliaa));
            fillDate();
            return;
        }
        if (taliaa != null) {
            Tools.showAskingPopup(getString(R.string.do_realy_want_do), new DidOnTap() {
                @Override
                public void onTap(HeadComponents view) {
                    showLockedLoading();
                    String taliaaID = taliaa.optString("_id");
                    String defaultTaliaaID = BackendProxy.getInstance().mTaliaaManager.getDefaultTaliaaId();
                    if (!StringHelper.isNullOrEmpty(defaultTaliaaID)) {
                        BackendProxy.getInstance().mTaliaaManager.deleteTaliaa(taliaaID, defaultTaliaaID, new CallBack() {
                            @Override
                            public void onResult(String response) {
                                runOnUiThread(() -> {
                                    hideLockedLoading();
                                    if (mAdapter != null) {
                                        BackendProxy.getInstance().mTaliaaManager.deleteTaliaaLocaly(taliaa);
                                        fillDate();
                                    }
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
    }

    @Override
    public void onTap(HeadComponents view) {
        switch (view.getId()) {
            case AddPopup.ADD_TALIAA_POPUP:
                MDrawableEditText mDrawableEditText = (MDrawableEditText) view;
                showLockedLoading();
                hidePopup();
                BackendProxy.getInstance().mTaliaaManager.addTaliaa(mDrawableEditText.getText(), new CallBack() {
                    @Override
                    public void onResult(String response) {
                        if (response != null) {
                            App.getSharedInstance().getMainActivity().injectTaliaaUSerData(new CallBack() {
                                @Override
                                public void onResult(String response) {
                                    runOnUiThread(() -> {
                                        hideLockedLoading();
                                        if (response != null) {
                                            fillDate();
                                        } else {
                                            showSimplePopup(getString(R.string.server_error));
                                        }
                                    });
                                }
                            });



                        } else {
                            runOnUiThread(() -> {
                                hideLockedLoading();
                                showSimplePopup(getString(R.string.server_error));
                            });

                        }
                    }
                });

                break;
            default:
                break;
        }
    }

    public class ListAdapter extends RecyclerView.Adapter<TaliaaView.ListAdapter.CellViewHolder> {
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
        public TaliaaView.ListAdapter.CellViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TaliaaView.ListAdapter.CellViewHolder viewHolder = null;
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
            taliaaCell challengeCell = new taliaaCell(getContext());
            viewHolder = new TaliaaView.ListAdapter.CellViewHolder(challengeCell);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull TaliaaView.ListAdapter.CellViewHolder holder, int position) {
            if (mDataSource != null) {
                holder.mCell.setData(mDataSource.optJSONObject(position));
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
            protected taliaaCell mCell;

            public CellViewHolder(View itemView) {
                super(itemView);
                mCell = (taliaaCell) itemView;
            }
        }
    }


    public class taliaaCell extends HeadComponents {

        protected MTextView mTaliaaName;

        public taliaaCell(@NonNull Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public taliaaCell(@NonNull Context context) {
            super(context);
        }

        @Override
        public void init(Context ctx, AttributeSet attrs) {
            super.init(ctx, attrs);

            mTaliaaName = new MTextView(ctx);
            mTaliaaName.setText("علي ابن ابي طالب");
            addView(mTaliaaName);

            setBackground(getDrawable(GradientDrawable.RECTANGLE, R.color.white, dpToPx(10), -1, -1));
            setElevation(12);
            layoutViews();
        }

        public void layoutViews() {
            int cellSize = dpToPx(150);
            int cellHeight = dpToPx(40);

            int margin = dpToPx(16);

            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER;
            mTaliaaName.setLayoutParams(params);

            params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, cellHeight);
            params.topMargin = margin;
            params.setMarginStart(margin);
            params.setMarginEnd(margin);
            setLayoutParams(params);
        }

        @Override
        public void setData(JSONObject data) {
            super.setData(data);

            if (data != null) {
                mTaliaaName.setText(data.optString("name"));
            }
        }
    }

    @Override
    public void onHeadBtnClicked(HeadComponents view1) {
        super.onHeadBtnClicked(view1);
        AddPopup addTaliaaPopup = new AddPopup(getContext());
        addTaliaaPopup.mDelegate = this;
        addTaliaaPopup.setOnTapListener(this);
        showPopup(addTaliaaPopup);
    }

    @Override
    public void onNotification(String notificationType, JSONObject data) {
        super.onNotification(notificationType, data);
        switch (notificationType) {
            case NotificationCenter.USERS_LIST_UPDATED:
                runOnUiThread(() -> {
                    fillDate();
                });
                break;

        }
    }
}
