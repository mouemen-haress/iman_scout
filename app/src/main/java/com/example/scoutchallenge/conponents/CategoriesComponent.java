package com.example.scoutchallenge.conponents;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scoutchallenge.R;
import com.example.scoutchallenge.helpers.JsonHelper;
import com.example.scoutchallenge.helpers.RecyclerItemClickListener;
import com.example.scoutchallenge.interfaces.CategoriesMenuDelegate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CategoriesComponent extends HeadComponents {

    protected RecyclerView mListView;
    protected CategoriesComponent.ListAdapter mAdapter;
    protected RecyclerView.LayoutManager mMyManager;

    public JSONArray mMenuArray;
    public CategoriesMenuDelegate mDelegate;

    public boolean mIsNeedPreventSelection =false;

    public CategoriesComponent(@NonNull Context context) {
        super(context);
    }


    @Override
    public void init(Context ctx, AttributeSet attrs) {
        super.init(ctx, attrs);
        mAdapter = new CategoriesComponent.ListAdapter();

        mMyManager = new LinearLayoutManager(ctx, RecyclerView.HORIZONTAL, false);

        mListView = new RecyclerView(ctx);
        mListView.setAdapter(mAdapter);
        mListView.setLayoutManager(mMyManager);
        mListView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), mListView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(mIsNeedPreventSelection){
                    if (mDelegate != null) {
                        JSONObject data = mAdapter.getDataAt(position);
                        mDelegate.didPreventSelectCategoryCell(data,view,position);
                        return;
                    }
                }

                selectionAction(view, position);

                if (mDelegate != null) {
                    JSONObject data = mAdapter.getDataAt(position);
                    mDelegate.didSelectCategoryCell(data);
                }

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
        addView(mListView);

        layoutViews();
    }

    private void layoutViews() {
        int padding = dpToPx(16);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        mListView.setLayoutParams(params);

    }

    public void selectionAction(View view, int position) {
        if (mAdapter != null) {
            CategoryCell cell = (CategoryCell) view;
            for (int i = 0; i < mMenuArray.length(); i++) {
                JSONObject menuData = mMenuArray.optJSONObject(i);
                try {
                    if (i == position) {
                        menuData.put("isSelected", true);
                    } else {
                        menuData.put("isSelected", false);
                    }
                } catch (JSONException e) {
                }
            }
            setUpMenuArray(mMenuArray);
        }
        mListView.smoothScrollToPosition(position);
    }

    public void setUpMenuArray(JSONArray array) {
        if (array == null) {
            return;
        }
        mMenuArray = array;
        mAdapter.mDataSource = array;
        mAdapter.notifyDataSetChanged();


    }

    public void stimilateCellSelection(String id) {
        if (mMenuArray == null) {
            return;
        }
        for (int i = 0; i < mMenuArray.length(); i++) {
            JSONObject obj = JsonHelper.getJSONObject(mMenuArray, i);
            if (obj != null) {
                String idObj = obj.optString("id");
                if (idObj.equalsIgnoreCase(id)) {
                    if (mMyManager != null) {
                        HeadComponents headComponents = (HeadComponents) mMyManager.findViewByPosition(i);
                        headComponents.performClick();
                    }
                }
            }
        }
    }

    public class ListAdapter extends RecyclerView.Adapter<CategoriesComponent.ListAdapter.CellViewHolder> {
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
        public CategoriesComponent.ListAdapter.CellViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            CategoriesComponent.ListAdapter.CellViewHolder viewHolder = null;

            CategoryCell challengeCell = new CategoryCell(getContext());
            viewHolder = new CategoriesComponent.ListAdapter.CellViewHolder(challengeCell);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull CategoriesComponent.ListAdapter.CellViewHolder holder, int position) {
            if (mDataSource != null) {
                JSONObject obj = mDataSource.optJSONObject(position);
                holder.mCell.setData(obj);
            }
        }

        @Override
        public int getItemCount() {
            if (mDataSource == null) {
                return 0;
            }
            return mDataSource.length();
        }

        public JSONObject getDataAt(int position) {
            if (mDataSource != null && position < mDataSource.length()) {
                return mDataSource.optJSONObject(position);
            }
            return null;
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
        protected MTextView mLabel;
        GradientDrawable mGradientDrawable;
        protected String id;

        public CategoryCell(@NonNull Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public CategoryCell(@NonNull Context context) {
            super(context);
        }

        @Override
        public void init(Context ctx, AttributeSet attrs) {
            super.init(ctx, attrs);

            mLabel = new MTextView(ctx, attrs);
            mLabel.setTextColor(Color.WHITE);
            addView(mLabel);

            mGradientDrawable = new GradientDrawable();
            mGradientDrawable.setShape(GradientDrawable.RECTANGLE);
            mGradientDrawable.setColor(Color.BLACK);
            mGradientDrawable.setCornerRadius(dpToPx(20));

            setElevation(9);
            this.setBackground(mGradientDrawable);

            layoutViews();
        }

        public void layoutViews() {
            int padding = dpToPx(16);

            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER;
            mLabel.setPadding(padding, padding, padding, padding);
            mLabel.setLayoutParams(params);

            params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMarginEnd(padding);
            setLayoutParams(params);
        }

        @Override
        public void setData(JSONObject data) {
            super.setData(data);
            if (data == null) {
                return;
            }

            mLabel.setText(data.optString("title"));
            if (mData.optBoolean("isSelected")) {
                mGradientDrawable.setColor(getColor(R.color.headColor));
                mLabel.setTextColor(getColor(R.color.white));

            } else {
                mGradientDrawable.setColor(Color.TRANSPARENT);
                mGradientDrawable.setStroke(2, Color.BLACK);
                mLabel.setTextColor(getColor(R.color.headColor));
            }

            id = data.optString("id");

            layoutViews();
        }
    }
}
