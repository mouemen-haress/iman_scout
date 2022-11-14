package com.example.scoutchallenge.conponents;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.scoutchallenge.R;
import com.example.scoutchallenge.interfaces.DidOnTap;

import java.util.ArrayList;

public class DatePickerComponent extends HeadComponents {
    public static final String REQUIRE = "REQUIRE";

    GradientDrawable myGradientDrawable;
    protected MTextView mText;
    protected MImageComponent mIcon;
    public DidOnTap mDelegate;
    public boolean mIsNewDataSelected = false;

    ArrayList<String> mCheckingList;
    ArrayList<String> mErrorTypeList;

    public DatePickerComponent(@NonNull Context context) {
        super(context);
    }

    @Override
    public void init(Context ctx, AttributeSet attrs) {
        super.init(ctx, attrs);

        mText = new MTextView(ctx);
        mText.setTextColor(getColor(R.color.white));
        addView(mText);

        mIcon = new MImageComponent(ctx);
        addView(mIcon);
        myGradientDrawable = getDrawable(GradientDrawable.RECTANGLE, R.color.secondColor, dpToPx(10), 2,
                -1);

        setBackground(myGradientDrawable);

        setOnTapListener(new DidOnTap() {
            @Override
            public void onTap(HeadComponents view) {
                mIsNewDataSelected = true;
                myGradientDrawable.setColor(getColor(R.color.secondColor));
                if (mDelegate != null) {
                    mDelegate.onTap(view);
                }
            }
        });
        mCheckingList = new ArrayList<>();
        mErrorTypeList = new ArrayList<>();
        layoutViews();

        setUpArrayErrorType();

    }

    public void layoutViews() {
        int cellSize = dpToPx(150);
        int iconSize = dpToPx(40);

        int margin = dpToPx(16);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_VERTICAL | Gravity.START;
        params.setMarginEnd(iconSize);
        params.setMarginStart(margin);
        mText.setLayoutParams(params);

        params = new LayoutParams(iconSize, iconSize);
        params.gravity = Gravity.END;
        mIcon.setLayoutParams(params);

    }

    private void setUpArrayErrorType() {
        mErrorTypeList.add(REQUIRE);
    }


    public void setHint(String text) {
        mText.setText(text);
    }

    public String getText() {
        return mText.getText();
    }


    public void setIcon(int rsc) {
        mIcon.setImageResource(rsc);
    }

    public void setErrorState() {
        myGradientDrawable.setColor(Color.RED);
    }

    public boolean canGo() {
        if (!mIsNewDataSelected && mCheckingList.contains(REQUIRE)) {
            mText.setText(getContext().getString(R.string.require_text));
            setErrorState();
            return false;
        }
        return true;
    }

    public void addCondition(String... condition) {
        for (int i = 0; i < condition.length; ++i) {
            String conditionText = condition[i];
            if (conditionText != null && mErrorTypeList.contains(conditionText)) {
                mCheckingList.add(conditionText);
            }
        }
    }
}
