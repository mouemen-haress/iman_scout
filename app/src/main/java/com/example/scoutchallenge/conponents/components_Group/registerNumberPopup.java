package com.example.scoutchallenge.conponents.components_Group;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.scoutchallenge.conponents.HeadComponents;
import com.example.scoutchallenge.conponents.MTextView;

public class registerNumberPopup extends HeadComponents {

    MTextView mLabel;

    public registerNumberPopup(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context ctx, AttributeSet attrs) {
        super.init(ctx, attrs);

        mLabel = new MTextView(ctx, attrs);
        mLabel.setText("Mouemen");
        mLabel.setTextColor(Color.YELLOW);
        addView(mLabel);

        layoutViews();
    }

    private void layoutViews() {
        int padding = dpToPx(16);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        mLabel.setLayoutParams(params);

        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(300));
        params.gravity = Gravity.CENTER;
        setLayoutParams(params);
    }
}
