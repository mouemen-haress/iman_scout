package com.example.scoutchallenge.conponents;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;

import com.example.scoutchallenge.R;
import com.example.scoutchallenge.helpers.FontHelper;

public class MTextView extends HeadComponents {

    protected TextView mLabel;

    public MTextView(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public MTextView(@NonNull Context context) {
        super(context);
    }


    @Override
    public void init(Context ctx, AttributeSet attrs) {
        super.init(ctx, attrs);
        mLabel = new TextView(ctx);
        mLabel.setEllipsize(TextUtils.TruncateAt.END);
        Typeface typeface = ResourcesCompat.getFont(getContext(), FontHelper.getMainFont());
        mLabel.setTypeface(typeface);
        addView(mLabel);

        layoutViews();

    }

    private void layoutViews() {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(params);

    }

    public void setText(String value) {
        mLabel.setText(value);
    }

    public void setTextColor(int color) {
        mLabel.setTextColor(color);
    }

    public void setTextSize(int size) {
        mLabel.setTextSize(size);
    }

    public TextView getLabel() {
        return mLabel;
    }

    public String getText() {
        return mLabel.getText().toString();
    }

    public void setMultiLine(int lineNb) {
        mLabel.setMaxLines(lineNb);
        mLabel.setSingleLine(false);
        mLabel.setLines(lineNb);
    }

    public void setTypeFace(int type) {
        mLabel.setTypeface(mLabel.getTypeface(), type);
    }
}
