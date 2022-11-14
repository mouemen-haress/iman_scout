package com.example.scoutchallenge.conponents;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scoutchallenge.interfaces.OnCellSwipe;

public class MyRecyclerView extends HeadComponents {
    public RecyclerView mMyList;
    private OnCellSwipe mSwipeDelegate;

    public MyRecyclerView(@NonNull Context context) {
        super(context);
    }


    @Override
    public void init(Context ctx, AttributeSet attrs) {
        super.init(ctx, attrs);

        mMyList = new RecyclerView(ctx);
        addView(mMyList);

        layoutViews();
    }

    private void layoutViews() {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mMyList.setLayoutParams(params);
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mMyList.setLayoutManager(layoutManager);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        mMyList.setAdapter(adapter);
    }

    public void setOnSwipeDelegate(OnCellSwipe delegate) {
        mSwipeDelegate = delegate;


        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                if (mSwipeDelegate != null) {
                    mSwipeDelegate.onSwipe(viewHolder, swipeDir);
                }

            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        if (mMyList != null) {
            itemTouchHelper.attachToRecyclerView(mMyList);
        }
    }


}
