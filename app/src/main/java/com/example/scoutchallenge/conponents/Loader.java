package com.example.scoutchallenge.conponents;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

public class Loader extends HeadComponents {
    ProgressBar mProgressBar;


    public Loader(@NonNull Context context) {
        super(context);

        mProgressBar = new ProgressBar(context);
        addView(mProgressBar);

        layoutViews();
    }

    private void layoutViews() {
    }


    public void showLoader() {
        mProgressBar.setVisibility(VISIBLE);
    }


    public void hideLoader() {
        mProgressBar.setVisibility(GONE);

    }

    @Override
    public void init(Context ctx, AttributeSet attrs) {
        super.init(ctx, attrs);


    }
}
