package com.example.scoutchallenge.conponents;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.scoutchallenge.R;

public class MButtonComponent extends HeadComponents {
    protected MTextView mLabel;
    GradientDrawable mGradientDrawable ;


    public MButtonComponent(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public MButtonComponent(@NonNull Context context) {
        super(context, null);

        mLabel = new MTextView(context);
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

    private void layoutViews() {
        int padding = dpToPx(16);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        mLabel.setLayoutParams(params);

    }

    public void setText(String value) {
        mLabel.setText(value);
    }

    public void setTextSize(int size) {
        mLabel.setTextSize(size);
    }

    public void setSkin(int color){
        mGradientDrawable.setColor(color);
    }
}
