package com.example.scoutchallenge.conponents.components_Group;

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
import com.example.scoutchallenge.conponents.CircularImageView;
import com.example.scoutchallenge.conponents.HeadComponents;
import com.example.scoutchallenge.conponents.MDrawableEditText;
import com.example.scoutchallenge.conponents.MTextView;
import com.example.scoutchallenge.conponents.popups.AddPopup;
import com.example.scoutchallenge.helpers.D;
import com.example.scoutchallenge.helpers.RecyclerItemClickListener;
import com.example.scoutchallenge.interfaces.CallBack;
import com.example.scoutchallenge.interfaces.DidOnTap;
import com.example.scoutchallenge.models.MemberModule;
import com.example.scoutchallenge.network.MImageLoader;
import com.example.scoutchallenge.views.cells.UserCell;

import org.json.JSONArray;
import org.json.JSONObject;

public class UserListComponent extends HeadComponents implements DidOnTap {

    protected RecyclerView mAvtivitiesList;
    protected UserListComponent.ListAdapter mAdapter;
    protected LinearLayoutManager mMyManager;


    public UserListComponent(@NonNull Context context) {
        super(context);
    }

    @Override
    public void init(Context ctx, AttributeSet attrs) {
        super.init(ctx, attrs);


        mAdapter = new ListAdapter();

        mMyManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        mAvtivitiesList = new RecyclerView(ctx);
        mAvtivitiesList.setAdapter(mAdapter);
        mAvtivitiesList.setLayoutManager(mMyManager);

        mAvtivitiesList.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), mAvtivitiesList, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                JSONObject clickedObj = new JSONObject();
                clickedObj = mAdapter.mDataSource.optJSONObject(position);
                if (clickedObj != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("userObj", clickedObj.toString());
                    pushView(R.id.showUserInfoView, bundle);
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
        addView(mAvtivitiesList);

        layoutViews();
    }

    public void fillUsers(JSONArray array) {

        if (array != null) {
            mAdapter.mDataSource = array;
            mAdapter.notifyDataSetChanged();
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
        mAvtivitiesList.setLayoutParams(params);

    }

    @Override
    public void onTap(HeadComponents view) {
        switch (view.getId()) {
            case AddPopup.ADD_TALIAA_POPUP:
//                MDrawableEditText mDrawableEditText = (MDrawableEditText) view;
//                BackendProxy.getInstance().mActivityManager.addActivities(mDrawableEditText.getText(), D.t
//                        , new CallBack() {
//                            @Override
//                            public void onResult(String response) {
//                                runOnUiThread(() -> {
//                                    mAdapter.notifyDataSetChanged();
//
//                                });
//                            }
//                        });

                break;
            default:
                break;
        }

    }


    public class ListAdapter extends RecyclerView.Adapter<UserListComponent.ListAdapter.CellViewHolder> {
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
        public UserListComponent.ListAdapter.CellViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            UserListComponent.ListAdapter.CellViewHolder viewHolder = null;
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
            viewHolder = new UserListComponent.ListAdapter.CellViewHolder(challengeCell);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull UserListComponent.ListAdapter.CellViewHolder holder, int position) {
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



}
