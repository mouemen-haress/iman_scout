package com.example.scoutchallenge.views;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.scoutchallenge.conponents.MatrixColumComponent;
import com.example.scoutchallenge.conponents.components_Group.UserListComponent;
import com.example.scoutchallenge.helpers.StringHelper;

import org.json.JSONArray;

public class OnsorMatrixView extends HeadView {
    protected MDrawableEditText mSearch;
    private UserListComponent mUserList;
    protected RecyclerView mMatrixList;
    protected ListAdapter mAdapter;
    protected RecyclerView.LayoutManager mMyManager;


    @Override
    public void init(Context ctx, View view) {
        super.init(ctx, view);

        showHeader();
        setHeadBtn(getImage("add_icon"));

        mAdapter = new ListAdapter();

        mMyManager = new LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false);

        mUserList = new UserListComponent(ctx);
        mUserList.hide();
        mRootView.addView(mUserList);


        mSearch = new MDrawableEditText(ctx);
        mSearch.setPlaceHolder(getString(R.string.search));
        mSearch.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = charSequence.toString();
                if (StringHelper.isNullOrEmpty(text)) {
                    mMatrixList.setVisibility(View.VISIBLE);
                    mUserList.hide();
                } else {
                    mUserList.show();
                    mUserList.fillUsers(getSpesificUser(text));
                    mMatrixList.setVisibility(View.GONE);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mRootView.addView(mSearch);


        mMatrixList = new RecyclerView(ctx);
        mMatrixList.setAdapter(mAdapter);
        mMatrixList.setLayoutManager(mMyManager);
        mRootView.addView(mMatrixList);

        fillData();


        layoutViews();
    }

    private JSONArray getSpesificUser(String text) {
        return BackendProxy.getInstance().mUserManager.getRelatedNameUser(text);
    }

    private void layoutViews() {
        int margin = dpToPx(16);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMarginEnd(margin);
        params.setMarginStart(margin);
        params.topMargin = getHeadSize();
        mSearch.setLayoutParams(params);

        mSearch.measure(0, 0);
        int searchHeight = mSearch.getMeasuredHeight();


        params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMarginEnd(dpToPx(16));
        params.topMargin = getHeadSize() + searchHeight + margin;
        mMatrixList.setLayoutParams(params);

        params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMarginEnd(margin);
        params.setMarginStart(margin);
        params.topMargin = getHeadSize() + searchHeight + margin;
        mUserList.setLayoutParams(params);

    }


    private void fillData() {


        JSONArray taliaaList = BackendProxy.getInstance().mTaliaaManager.mTaliaaList;
        mAdapter.mDataSource = taliaaList;
        mAdapter.notifyDataSetChanged();
    }


    public class ListAdapter extends RecyclerView.Adapter<OnsorMatrixView.ListAdapter.CellViewHolder> {
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
        public OnsorMatrixView.ListAdapter.CellViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            OnsorMatrixView.ListAdapter.CellViewHolder viewHolder = null;

            MatrixColumComponent matrixCell = new MatrixColumComponent(getContext());
            viewHolder = new OnsorMatrixView.ListAdapter.CellViewHolder(matrixCell);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull OnsorMatrixView.ListAdapter.CellViewHolder holder, int position) {
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
            protected MatrixColumComponent mCell;

            public CellViewHolder(View itemView) {
                super(itemView);
                mCell = (MatrixColumComponent) itemView;
            }
        }
    }

    @Override
    public void onHeadBtnClicked(HeadComponents view1) {
        super.onHeadBtnClicked(view1);
        pushView(R.id.onsorView);
    }
}
