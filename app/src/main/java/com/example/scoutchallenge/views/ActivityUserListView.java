package com.example.scoutchallenge.views;

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
import com.example.scoutchallenge.backend.BackendProxy;
import com.example.scoutchallenge.conponents.HeadComponents;
import com.example.scoutchallenge.conponents.MTextView;
import com.example.scoutchallenge.conponents.MyRecyclerView;
import com.example.scoutchallenge.conponents.popups.SelectUserPopup;
import com.example.scoutchallenge.helpers.DateHelper;
import com.example.scoutchallenge.helpers.JsonHelper;
import com.example.scoutchallenge.helpers.RecyclerItemClickListener;
import com.example.scoutchallenge.helpers.StringHelper;
import com.example.scoutchallenge.helpers.Tools;
import com.example.scoutchallenge.interfaces.CallBack;
import com.example.scoutchallenge.interfaces.DidOnFinishWork;
import com.example.scoutchallenge.interfaces.DidOnTap;
import com.example.scoutchallenge.interfaces.OnCellSwipe;
import com.example.scoutchallenge.views.cells.UserCell;

import org.json.JSONArray;
import org.json.JSONObject;

public class ActivityUserListView extends HeadView implements DidOnTap, OnCellSwipe, DidOnFinishWork {
    protected MTextView mHeaderText;
    protected MyRecyclerView mUserList;
    protected ActivityUserListView.ListAdapter mAdapter;
    protected LinearLayoutManager mMyManager;
    protected JSONObject mRelatedObj;
    protected JSONArray mCurrentUserList;
    protected JSONArray mOtherUserList;

    public ActivityUserListView() {
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

        mAdapter = new ActivityUserListView.ListAdapter();

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

        fillUsers();
        layoutViews();
    }

    protected void fillUsers() {
        JSONObject data = mRelatedObj;
        if (data != null) {
            JSONArray userList = data.optJSONArray("users");

            if (userList != null) {
                mCurrentUserList = userList;
                mAdapter.mDataSource = userList;
                mOtherUserList = BackendProxy.getInstance().mUserManager.getOtherUser(mCurrentUserList);
                mAdapter.notifyDataSetChanged();
            }

            String title = data.optString("title");
            String date = DateHelper.convertMongoDate(data.optString("date"));

            String headerText = "";
            if (!StringHelper.isNullOrEmpty(date)) {
                headerText = date + ": ";
            }
            headerText = headerText + title;
            mHeaderText.setText(headerText);
        }

    }

    private void layoutViews() {
        int logoSize = dpToPx(100);
        int headerSize = logoSize / 2 + dpToPx(16);
        int margin = dpToPx(16);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = getHeadSize() + margin;
        params.setMarginEnd(margin);
        params.setMarginStart(margin);
        params.bottomMargin = getBottomNavHeight() + margin / 2;
        mHeaderText.getLabel().setGravity(Gravity.CENTER);
//        mHeaderText.setMinimumHeight(headerSize);
        mHeaderText.setLayoutParams(params);

        mHeaderText.measure(0, 0);
        int textHeight = mHeaderText.getMeasuredHeight();

        params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.topMargin = getHeadSize() + textHeight + margin;
        params.bottomMargin = getBottomNavHeight() + margin / 2;
        mUserList.setLayoutParams(params);

    }

    @Override
    public void FillPassedData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String dataString = bundle.getString("activityObj");
            JSONObject obj = JsonHelper.parse(dataString);
            if (obj != null) {
                mRelatedObj = obj;
            }
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
        if (mRelatedObj != null) {
            Tools.showAskingPopup(getString(R.string.do_realy_want_do), new DidOnTap() {
                @Override
                public void onTap(HeadComponents view) {
                    showLockedLoading();
                    int position = viewHolder.getAdapterPosition();
                    JSONObject user = mAdapter.mDataSource.optJSONObject(position);
                    if (user != null) {
                        JSONObject userId = user.optJSONObject("userId");
                        String activityId = mRelatedObj.optString("_id");
                        if (userId != null) {
                            String _id = userId.optString("_id");
                            if (!StringHelper.isNullOrEmpty(_id)) {
                                BackendProxy.getInstance().mActivityManager.deleteUserFromActivity(_id, activityId, new CallBack() {
                                    @Override
                                    public void onResult(String response) {
                                        runOnUiThread(() -> {
                                            hideLockedLoading();
                                            mAdapter.mDataSource.remove(position);
                                            fillUsers();
                                        });
                                    }
                                });
                            }
                        }
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
    public void onFinishWork(JSONArray array) {
        if (array != null && mRelatedObj != null) {
            int arrayLenght = array.length();
            showLockedLoading();

            for (int i = 0; i < arrayLenght; i++) {
                String cuurentId = array.optString(i);
                if (!StringHelper.isNullOrEmpty(cuurentId)) {
                    String activityId = mRelatedObj.optString("_id");
                    if (!StringHelper.isNullOrEmpty(activityId)) {
                        int finalI = i;
                        hidePopup();

                        BackendProxy.getInstance().mActivityManager.AddUserToActivity(activityId, cuurentId, new CallBack() {
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
                                            JSONObject userIdObj = new JSONObject();
                                            JsonHelper.put(userIdObj, "userId", targetUser);
                                            lastUserArray.put(userIdObj);
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


    public class ListAdapter extends RecyclerView.Adapter<ActivityUserListView.ListAdapter.CellViewHolder> {
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
        public ActivityUserListView.ListAdapter.CellViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ActivityUserListView.ListAdapter.CellViewHolder viewHolder = null;
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
            UserCell challengeCell = new UserCell(getContext());
            viewHolder = new ActivityUserListView.ListAdapter.CellViewHolder(challengeCell);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ActivityUserListView.ListAdapter.CellViewHolder holder, int position) {
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
            protected UserCell mCell;

            public CellViewHolder(View itemView) {
                super(itemView);
                mCell = (UserCell) itemView;
            }
        }

    }


    @Override
    public void onHeadBtnClicked(HeadComponents view1) {
        super.onHeadBtnClicked(view1);

        if (mOtherUserList != null && mOtherUserList.length() == 0) {
            showToast(getString(R.string.all_user_add));
            return;
        }
        SelectUserPopup addUserPopup = new SelectUserPopup(getContext());
        if (mOtherUserList != null) {
            addUserPopup.setUserArray(mOtherUserList);
        } else {
            addUserPopup.setUserArray(BackendProxy.getInstance().mUserManager.mAllUserList);
        }
        addUserPopup.mDelegate = this;
        showPopup(addUserPopup);


    }
}
