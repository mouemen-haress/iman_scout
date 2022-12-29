package com.example.scoutchallenge.conponents;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scoutchallenge.R;
import com.example.scoutchallenge.models.UserModule;
import com.example.scoutchallenge.network.MImageLoader;

import org.json.JSONArray;
import org.json.JSONObject;

public class MatrixColumComponent extends HeadComponents {

    protected MTextView mListHead;
    protected RecyclerView mListView;
    protected ListAdapter mAdapter;
    protected RecyclerView.LayoutManager mMyManager;


    public MatrixColumComponent(@NonNull Context context) {
        super(context);
    }

    @Override
    public void init(Context ctx, AttributeSet attrs) {
        super.init(ctx, attrs);

        mListHead = new MTextView(ctx);
        mListHead.setText("علي ابن ابي طالب");
        mListHead.setTextColor(getColor(R.color.white));
        mListHead.setBackground(getDrawable(GradientDrawable.RECTANGLE, R.color.headColor, dpToPx(5), -1, -1));

        addView(mListHead);

        mAdapter = new ListAdapter();

        mMyManager = new LinearLayoutManager(ctx);


        mListView = new RecyclerView(ctx);
        mListView.setAdapter(mAdapter);
        mListView.setLayoutManager(mMyManager);
        addView(mListView);


        layoutViews();

    }

    private void layoutViews() {
        int headWidth = dpToPx(150);
        int headHeight = dpToPx(50);
        int margin = dpToPx(16) / 2;

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(headWidth, headHeight);
        mListHead.setLayoutParams(params);
        mListHead.setElevation(8);
        mListHead.getLabel().setGravity(Gravity.CENTER);

        params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.topMargin = headHeight;
        mListView.setLayoutParams(params);

        params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.topMargin = margin;
        params.bottomMargin = getBottomNavHeight() + margin;
        params.setMarginStart(margin);
        setLayoutParams(params);


    }


    @Override
    public void setData(JSONObject data) {
        super.setData(data);
        if (data != null) {
            mListHead.setText(data.optString("name"));


            runOnUiThread(() -> {
                JSONArray array = new JSONArray();
                array = data.optJSONArray("users");
                mAdapter.mDataSource = array;
                mAdapter.notifyDataSetChanged();
            });

        }

    }


    public class ListAdapter extends RecyclerView.Adapter<MatrixColumComponent.ListAdapter.CellViewHolder> {
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
        public MatrixColumComponent.ListAdapter.CellViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            MatrixColumComponent.ListAdapter.CellViewHolder viewHolder = null;

            OnsorCell challengeCell = new OnsorCell(getContext());
            viewHolder = new MatrixColumComponent.ListAdapter.CellViewHolder(challengeCell);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MatrixColumComponent.ListAdapter.CellViewHolder holder, int position) {
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
            protected OnsorCell mCell;

            public CellViewHolder(View itemView) {
                super(itemView);
                mCell = (OnsorCell) itemView;
            }
        }
    }


    public class OnsorCell extends HeadComponents {

        protected ImageView mImage;
        protected HeadComponents mLine;

        protected MTextView mNameLabel;

        public OnsorCell(@NonNull Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public OnsorCell(@NonNull Context context) {
            super(context);
        }

        @Override
        public void init(Context ctx, AttributeSet attrs) {
            super.init(ctx, attrs);

            mImage = new ImageView(ctx);
            mImage.setImageResource(getImage("onsor"));
            mImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            addView(mImage);


            mLine = new HeadComponents(ctx);
            mLine.setBackgroundColor(getColor(R.color.headColor));
            addView(mLine);

            mNameLabel = new MTextView(ctx);
            mNameLabel.setText("ديب شموط");
            mNameLabel.setTextSize(14);
            mNameLabel.getLabel().setGravity(Gravity.CENTER);
            mNameLabel.setTextColor(getColor(R.color.headColor));
//            mNameLabel.setBackground(getDrawable(GradientDrawable.RECTANGLE, R.color.headColor, dpToPx(10), -1, -1));
            addView(mNameLabel);


            GradientDrawable gradientDrawable = getDrawable(GradientDrawable.RECTANGLE, R.color.white, dpToPx(10), 2, getColor(R.color.headColor));
            setBackground(gradientDrawable);

            layoutViews();
        }

        public void layoutViews() {
            int cellSize = dpToPx(150);
            int iconSize = dpToPx(80);

            int margin = dpToPx(16) / 2;

            LayoutParams params = new LayoutParams(cellSize, iconSize);
            mImage.setLayoutParams(params);

            params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(2));
            params.topMargin = iconSize;
            mLine.setLayoutParams(params);


            int topMargin = margin * 2 + iconSize;
            params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, cellSize - topMargin);
            params.gravity = Gravity.CENTER_HORIZONTAL;
            params.topMargin = topMargin;
            params.setMarginStart(margin);
            params.setMarginEnd(margin);
            params.bottomMargin = margin;
            mNameLabel.setLayoutParams(params);


            params = new LayoutParams(cellSize, cellSize);
            params.topMargin = margin;
            setLayoutParams(params);
        }

        @Override
        public void setData(JSONObject data) {
            super.setData(data);
            if (data != null) {
                UserModule userModule = new UserModule();
                userModule.setData(data);

                String name = data.optString("Name");
                mNameLabel.setText(name);

                MImageLoader.loadWithGlide(userModule.getImageUrl(), 0, mImage);
            }
        }


    }
}
