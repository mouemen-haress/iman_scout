package com.example.scoutchallenge.conponents.popups;

import android.content.Context;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;

import com.example.scoutchallenge.R;
import com.example.scoutchallenge.conponents.HeadComponents;
import com.example.scoutchallenge.conponents.MDrawableEditText;
import com.example.scoutchallenge.conponents.MImageComponent;
import com.example.scoutchallenge.conponents.MTextView;
import com.example.scoutchallenge.helpers.InputHelper;
import com.example.scoutchallenge.helpers.JsonHelper;
import com.example.scoutchallenge.interfaces.CallBack;

import org.json.JSONObject;

public class KoranNotePopup extends HeadComponents {
    protected ScrollView mScrolView;
    LinearLayout mcontainer;
    protected MImageComponent mUpdateIcon;

    protected MDrawableEditText mCurrent;
    protected MDrawableEditText mRate;
    protected MDrawableEditText mNext;

    public CallBack mDelegate;


    public KoranNotePopup(@NonNull Context context) {
        super(context);
    }

    @Override
    public void init(Context ctx, AttributeSet attrs) {
        super.init(ctx, attrs);

        mScrolView = new ScrollView(ctx);
        addView(mScrolView);

        mcontainer = new LinearLayout(ctx);
        mcontainer.setOrientation(LinearLayout.VERTICAL);
        mScrolView.addView(mcontainer);

        mUpdateIcon = new MImageComponent(ctx);
        mUpdateIcon.setImageResource(getImage("true_icon"));
        mUpdateIcon.setOnTapListener(view -> {
            String current = mCurrent.getText().toString();
            String rate = mRate.getText().toString();
            String next = mNext.getText().toString();

            if (mDelegate != null) {
                JSONObject data = new JSONObject();
                JsonHelper.put(data, "note", current);
                JsonHelper.put(data, "rate", rate);
                JsonHelper.put(data, "next", next);
                mDelegate.onResult(data.toString());

            }
//            BackendProxy.getInstance().mActivityManager.updateNoteActivities();
        });
        mcontainer.addView(mUpdateIcon);

        mCurrent = new MDrawableEditText(ctx);
        mCurrent.setPlaceHolder(getString(R.string.current_reading));
        mCurrent.transformToTextArea(6);
        mCurrent.setgravity(Gravity.TOP | Gravity.START);
        mcontainer.addView(mCurrent);

        mRate = new MDrawableEditText(ctx);
        mRate.setPlaceHolder(getString(R.string.rating));
        mRate.setInpuType(InputType.TYPE_CLASS_NUMBER);
        mRate.getEditText().setFilters(new InputFilter[]{new InputHelper("0", "20")});
        mcontainer.addView(mRate);

        mNext = new MDrawableEditText(ctx);
        mNext.setPlaceHolder(getString(R.string.next_reading));
        mNext.transformToTextArea(6);
        mNext.setgravity(Gravity.TOP | Gravity.START);
        mcontainer.addView(mNext);


        fixHintGarvity();
        layoutViews();
    }

    private void layoutViews() {
        int iconSize = dpToPx(32);
        int margin = dpToPx(16);

        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearParams.topMargin = margin;
        linearParams.setMarginEnd(margin);
        linearParams.setMarginStart(margin);
        mCurrent.setLayoutParams(linearParams);
        mNext.setLayoutParams(linearParams);
        mRate.setLayoutParams(linearParams);


        linearParams = new LinearLayout.LayoutParams(iconSize, iconSize);
        linearParams.topMargin = margin;
        linearParams.setMarginEnd(margin);
        linearParams.setMarginStart(margin);
        linearParams.gravity = Gravity.START;
        mUpdateIcon.setLayoutParams(linearParams);


    }

    @Override
    public void setData(JSONObject data) {
        super.setData(data);
        if (data != null) {
            String current = data.optString("note");
            String rate = data.optString("rate");
            String next = data.optString("next");

            mCurrent.setText(current);
            mRate.setText(rate);
            mNext.setText(next);


        }
    }

    private void fixHintGarvity() {
        MTextView hint = mCurrent.getHintTextView();
        if (hint != null) {
            LayoutParams params = (LayoutParams) hint.getLayoutParams();
            params.gravity = Gravity.END;
            hint.setLayoutParams(params);
        }

        hint = mRate.getHintTextView();
        if (hint != null) {
            LayoutParams params = (LayoutParams) hint.getLayoutParams();
            params.gravity = Gravity.END;
            hint.setLayoutParams(params);
        }


        hint = mNext.getHintTextView();
        if (hint != null) {
            LayoutParams params = (LayoutParams) hint.getLayoutParams();
            params.gravity = Gravity.END;
            hint.setLayoutParams(params);
        }
    }
}
