package com.example.scoutchallenge.conponents;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;

import com.example.scoutchallenge.R;
import com.example.scoutchallenge.helpers.FontHelper;

public class MEditText extends HeadComponents {

    private EditText mEditText;

    public MEditText(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void init(Context ctx, AttributeSet attr) {
        super.init(ctx, attr);

        mEditText = new EditText(ctx);
        mEditText.setFocusableInTouchMode(true);
        mEditText.setFocusable(true);
        mEditText.setTextSize(14);
        mEditText.setBackgroundResource(android.R.color.transparent);
        mEditText.setBackground(null);
        Typeface typeface = ResourcesCompat.getFont(getContext(), FontHelper.getMainFont());
        mEditText.setTypeface(typeface);
        setMaxLine(1);
        mEditText.setHorizontallyScrolling(true);

        String hint = (String) geResourcefromXml(R.attr.hint);
        if (hint != null) {
            mEditText.setHint(hint);
        }

        setHintColor(R.color.hint);

        this.addView(mEditText);
        layoutViews();

    }

    private void layoutViews() {
        int padding = dpToPx(5);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mEditText.setLayoutParams(params);

    }

    public void setInputType(int type) {
        mEditText.setInputType(type);
    }

    public void setMaxLine(int line) {
        mEditText.setMaxLines(line);
    }

    public void setHorizontallyScrolling(boolean trueOrFalse) {
        mEditText.setHorizontallyScrolling(trueOrFalse);
    }

    public void setgravity(int gravity) {
        mEditText.setGravity(gravity);
    }

    public void setTransformationMethod(PasswordTransformationMethod transformationMethod) {
        mEditText.setTransformationMethod(transformationMethod);
    }

    public void setHint(String s) {
        mEditText.setHint(s);
    }

    @SuppressLint("ResourceAsColor")
    public void setHintColor(int color) {
        mEditText.setHintTextColor(color);
    }

    public String getText() {
        return mEditText.getText().toString();
    }

    public EditText getEditText() {
        return mEditText;
    }
}
