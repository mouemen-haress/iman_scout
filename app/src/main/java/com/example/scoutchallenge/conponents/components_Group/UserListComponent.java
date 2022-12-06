package com.example.scoutchallenge.conponents.components_Group;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
//                    App.getSharedInstance().getMainActivity().pushView(R.id.showUserInfoView);
                    BackendProxy.getInstance().mUserManager.deleteUserFromTaliaa(clickedObj.optString("_id"), null);
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
            UserListComponent.UserCell challengeCell = new UserListComponent.UserCell(getContext());
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
            protected UserListComponent.UserCell mCell;

            public CellViewHolder(View itemView) {
                super(itemView);
                mCell = (UserListComponent.UserCell) itemView;
            }
        }
    }


    public class UserCell extends HeadComponents {
        protected CircularImageView mImage;
        protected MTextView mNameLabel;

        public UserCell(@NonNull Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public UserCell(@NonNull Context context) {
            super(context);
        }

        @Override
        public void init(Context ctx, AttributeSet attrs) {
            super.init(ctx, attrs);

            mImage = new CircularImageView(ctx);
            mImage.setImageResource(getImage("onsor"));
            addView(mImage);

            mNameLabel = new MTextView(ctx);
            mNameLabel.setText("ديب شموط");
            mNameLabel.setTextSize(18);
            mNameLabel.getLabel().setGravity(Gravity.CENTER);
            mNameLabel.setTextColor(getColor(R.color.white));
            mNameLabel.setBackground(getDrawable(GradientDrawable.RECTANGLE, R.color.headColor, dpToPx(10), -1, -1));
            addView(mNameLabel);


            GradientDrawable gradientDrawable = getDrawable(GradientDrawable.RECTANGLE, R.color.white, dpToPx(10), -1, -1);
            gradientDrawable.setStroke(3, getColor(R.color.secondColor));
            setBackground(gradientDrawable);
            setElevation(8);

            layoutViews();
        }

        public void layoutViews() {
            int cellSize = dpToPx(150);
            int iconSize = dpToPx(80);
            int twoDp = dpToPx(2);
            int margin = dpToPx(16);

            LayoutParams params = new LayoutParams(iconSize, iconSize);
            params.topMargin = margin;
            params.setMarginStart(margin);
            params.bottomMargin = margin;
            mImage.setLayoutParams(params);


            params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER_VERTICAL | Gravity.START;
            params.setMarginStart(iconSize + margin * 2);
            params.setMarginEnd(margin / 2);
            mNameLabel.setPadding(twoDp, twoDp, twoDp, twoDp);
            mNameLabel.setLayoutParams(params);


            params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.topMargin = margin;
            setLayoutParams(params);
        }

        @Override
        public void setData(JSONObject data) {
            super.setData(data);

            mNameLabel.setText(data.optString("Name"));
//            String avat = user.optString("file").split("\\\\")[1];
//            MImageLoader.loadWithGlide(D.ASSET_URL + "/" + avat, 0, mImage.getImage());

        }

    }


}
