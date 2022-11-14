package com.example.scoutchallenge.conponents;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.scoutchallenge.R;
import com.example.scoutchallenge.helpers.StringHelper;
import com.example.scoutchallenge.helpers.Tools;
import com.example.scoutchallenge.interfaces.OnEditTextIconTap;

import java.util.ArrayList;

public class MDrawableEditText extends HeadComponents {

    public static final String REQUIRE = "REQUIRE";
    public static final String EMAIL_CHECKING = "EMAIL_CHECKING";
    public static final String DEFAULT = "DEFAULT";

    protected MTextView mHint;
    protected MEditText mEditText;
    protected MImageComponent mIcon;


    GradientDrawable mGradientDrawable;

    ArrayList<String> mCheckingList;
    ArrayList<String> mErrorTypeList;
    String mLastHint = "";
    String mDefaultMessage;

    OnEditTextIconTap mIconDelegate;
    protected boolean isPassMode = true;


    public MDrawableEditText(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MDrawableEditText(@NonNull Context context) {
        super(context, null);
    }

    @Override
    public void init(Context ctx, AttributeSet attr) {
        super.init(ctx, attr);


        mHint = new MTextView(ctx);
        mHint.setText("تقليدي");
        mHint.setTextColor(getColor(R.color.headColor));
//        mHint.setBackground(getDrawable(GradientDrawable.RECTANGLE, R.color.headColor, dpToPx(10)));
        this.addView(mHint);


        mIcon = new MImageComponent(ctx, attr);
        mIcon.setOnTapListener(view -> {
            if (mIconDelegate != null) {
                mIconDelegate.onIconTap(view);
            }
        });
        mIcon.hide();
        this.addView(mIcon);


        mEditText = new MEditText(ctx, null);
        mEditText.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    handleFocusState();
                } else {
                    handleUnFocusState();
                }
            }

        });

        mEditText.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mHint.setTextColor(getColor(R.color.headColor));
                mGradientDrawable.setStroke(1, Color.BLACK);
                mHint.setText(mLastHint);

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        addView(mEditText);


        mGradientDrawable = new GradientDrawable();
        mGradientDrawable.setShape(GradientDrawable.RECTANGLE);
        mGradientDrawable.setStroke(1, Color.BLACK);
        mGradientDrawable.setCornerRadius(dpToPx(20));

        setElevation(9);
        mEditText.setBackground(mGradientDrawable);
        mEditText.getEditText().setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);

        mCheckingList = new ArrayList<>();

        mErrorTypeList = new ArrayList<>();
        setUpArrayErrorType();
        layoutViews();
    }

    private void setUpArrayErrorType() {
        mErrorTypeList.add(REQUIRE);
        mErrorTypeList.add(EMAIL_CHECKING);
        mErrorTypeList.add(DEFAULT);
    }

    private void handleFocusState() {
        mGradientDrawable.setStroke(3, getColor(R.color.headColor));

    }

    private void handleUnFocusState() {
        mGradientDrawable.setStroke(1, getColor(R.color.secondColor));

    }

    private void layoutViews() {
        int margin = dpToPx(16);
        int hintHeight = dpToPx(25);
        int onDp = dpToPx(3);
        int editTextHeight = dpToPx(50);
        int iconSize = dpToPx(24);


        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, hintHeight);
        params.setMarginStart(margin);
        mHint.setPadding(onDp, onDp, onDp, onDp);
        mHint.setLayoutParams(params);

        params = new LayoutParams(iconSize, iconSize);
        params.setMarginEnd(margin);
        params.topMargin = hintHeight + margin / 2 + (editTextHeight / 2 - (iconSize / 2));
        params.gravity = Gravity.END;
        mIcon.setPadding(onDp, onDp, onDp, onDp);
        mIcon.setLayoutParams(params);


        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, editTextHeight);
        params.topMargin = hintHeight + margin / 2;
        mEditText.setPadding(margin + iconSize + margin, 0, margin, 0);
        mEditText.setLayoutParams(params);

        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(params);
    }

    public void setInpuType(int type) {
        mEditText.setInputType(type);
    }

    public void setPlaceHolder(String s) {
        mEditText.setHint(s);
        setHintText(s);
    }

    public void setHintColor(int color) {
        mEditText.setHintColor(color);
    }

    public void setgravity(int gravity) {
        mEditText.setgravity(gravity);
    }

    public String getText() {
        return mEditText.getText();
    }

    public void setErrorState(String errorMsg) {
        mGradientDrawable.setStroke(3, Color.RED);
        mHint.setText(errorMsg);
        mHint.setTextColor(Color.RED);
    }

    public void setHintText(String text) {
        mHint.setText(text);
        mLastHint = text;
    }

    public void setPasswordType() {
        mEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        mEditText.setHint("********");
        mIcon.setImageResource(getImage("unlocked_eyes"));
        mIcon.show();
        setOnIconTapDelegate(currentIcon -> {
            toggleEyes();
        });
    }

    public void setIcon(int rcs) {
        mIcon.show();
        mIcon.setImageResource(rcs);

    }

    public void setOnIconTapDelegate(OnEditTextIconTap delegate) {
        if (delegate != null) {
            mIconDelegate = delegate;
        }

    }

    private void toggleEyes() {
        if (isPassMode) {
            mEditText.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
            mIcon.setImageResource(getImage("unlocked_eyes"));
            isPassMode = false;
        } else {
            mEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            mIcon.setImageResource(getImage("locked_eyes"));
            isPassMode = true;
        }

    }


    public boolean canGo() {
        boolean canGo = true;
        String text = mEditText.getText();
        if (mCheckingList == null) {
            return true;
        }
        if (mCheckingList.contains(DEFAULT)) {
            if (!StringHelper.isNullOrEmpty(mDefaultMessage)) {
                setErrorState(mDefaultMessage);
            }
            return false;
        }

        if (mCheckingList.contains(REQUIRE) && text.equalsIgnoreCase("")) {
            setErrorState(getContext().getString(R.string.require_text));
            return false;
        }

        if (mCheckingList.contains(EMAIL_CHECKING) &&
                !android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
            setErrorState(getContext().getString(R.string.invalid_email));

            return false;
        }

        return canGo;
    }

    public void setDefaultErrorMsg(String msg) {
        mDefaultMessage = msg;
        addCondition(DEFAULT);
    }

    public void addCondition(String... condition) {
        for (int i = 0; i < condition.length; ++i) {
            String conditionText = condition[i];
            if (conditionText != null && mErrorTypeList.contains(conditionText) && !mCheckingList.contains(conditionText)) {
                mCheckingList.add(conditionText);
            }
        }
    }

    public void removeCondition(String... condition) {
        for (int i = 0; i < condition.length; ++i) {
            String conditionText = condition[i];
            if (conditionText != null && mErrorTypeList.contains(conditionText) && mCheckingList.contains(conditionText)) {
                mCheckingList.remove(conditionText);
            }
        }
    }

    public EditText getEditText() {
        return mEditText.getEditText();
    }

    public MTextView getHintTextView() {
        return mHint;
    }

}
