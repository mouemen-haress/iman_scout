package com.example.scoutchallenge.conponents;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

public class TextAreaComponent extends  MDrawableEditText{
    
    public TextAreaComponent(@NonNull Context context) {
        super(context   );
    }

    @Override
    public void layoutViews() {
        super.layoutViews();

        int margin = dpToPx(16);
        int hintHeight = dpToPx(25);
        int onDp = dpToPx(3);
        int editTextHeight = dpToPx(50);
        int iconSize = dpToPx(24);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = hintHeight + margin / 2;
        mEditText.setPadding(margin + iconSize + margin, 0, margin, 0);
        mEditText.setLayoutParams(params);

    }
}
