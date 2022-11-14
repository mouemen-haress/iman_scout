package com.example.scoutchallenge.conponents.popups;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.example.scoutchallenge.R;
import com.example.scoutchallenge.conponents.HeadComponents;
import com.example.scoutchallenge.conponents.MButtonComponent;
import com.example.scoutchallenge.conponents.MDrawableEditText;
import com.example.scoutchallenge.conponents.MImageComponent;
import com.example.scoutchallenge.conponents.MTextView;
import com.example.scoutchallenge.interfaces.DidOnTap;

public class AddPopup extends HeadComponents {
    public static final int ADD_TALIAA_POPUP = 0;

    protected LinearLayout mContainer;
    public MDrawableEditText mNameEditText;
    public MImageComponent mAddBtn;

    public DidOnTap mDelegate;

    public AddPopup(@NonNull Context context) {
        super(context);
    }

    @Override
    public void init(Context ctx, AttributeSet attrs) {
        super.init(ctx, attrs);

        mContainer = new LinearLayout(ctx);
        mContainer.setOrientation(LinearLayout.HORIZONTAL);
        addView(mContainer);


        mAddBtn = new MImageComponent(ctx);
//        mAddBtn.setText("بشينتسشخثهبتش خهبيسشخعباخيسشعا سشخيعا بخايسب");
//        mAddBtn.setSkin(getColor(R.color.headColor));
        mAddBtn.setImageResource(getImage("plus_icon"));
        mAddBtn.setOnTapListener(view -> {
            if (mDelegate != null) {
                if (mNameEditText != null) {
                    mDelegate.onTap(mNameEditText);
                }
            }
        });
        mContainer.addView(mAddBtn);

        mNameEditText = new MDrawableEditText(ctx);
        mNameEditText.setPlaceHolder(getText(R.string.enter_taliaa_name));
        mNameEditText.setId(ADD_TALIAA_POPUP);
        fixHintGarvity();
        mContainer.addView(mNameEditText);

        layoutViews();
    }


    private void layoutViews() {
        int margin = dpToPx(16);
        int editTextHeight = dpToPx(50);
        int hintHeight = dpToPx(25);


        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearParams.setMargins(margin, 0, margin, margin);
        linearParams.weight = 4;
        mNameEditText.setLayoutParams(linearParams);



        linearParams = new LinearLayout.LayoutParams(0, editTextHeight);
        linearParams.weight = 1;
        linearParams.topMargin=hintHeight+ margin / 2;
        linearParams.setMarginStart(margin);
        mAddBtn.setLayoutParams(linearParams);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = margin;
        params.bottomMargin = margin;
        mContainer.setLayoutParams(params);

    }

    private void fixHintGarvity() {
        MTextView hint = mNameEditText.getHintTextView();
        if (hint != null) {
            LayoutParams params = (LayoutParams) hint.getLayoutParams();
            params.gravity = Gravity.END;
            hint.setLayoutParams(params);
        }
    }

}
