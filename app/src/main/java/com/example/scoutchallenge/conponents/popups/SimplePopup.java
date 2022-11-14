package com.example.scoutchallenge.conponents.popups;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.example.scoutchallenge.R;
import com.example.scoutchallenge.conponents.CircularImageView;
import com.example.scoutchallenge.conponents.HeadComponents;
import com.example.scoutchallenge.conponents.MButtonComponent;
import com.example.scoutchallenge.conponents.MCircularImage;
import com.example.scoutchallenge.conponents.MImageComponent;
import com.example.scoutchallenge.conponents.MTextView;
import com.example.scoutchallenge.helpers.D;
import com.example.scoutchallenge.interfaces.DidOnTap;

import org.json.JSONObject;

public class SimplePopup extends HeadComponents {

    LinearLayout mContainer;
    protected MTextView mDescription;
    protected MButtonComponent mConfirmBtn;

    public DidOnTap mDelegate;


    public SimplePopup(@NonNull Context context) {
        super(context);
    }

    @Override
    public void init(Context ctx, AttributeSet attrs) {
        super.init(ctx, attrs);

        mContainer = new LinearLayout(ctx);
        mContainer.setOrientation(LinearLayout.VERTICAL);
        addView(mContainer);


        mDescription = new MTextView(ctx);
        mContainer.addView(mDescription);

        mConfirmBtn = new MButtonComponent(ctx);
        mConfirmBtn.setText(getString(R.string.ok));
        mConfirmBtn.setOnTapListener(new DidOnTap() {
            @Override
            public void onTap(HeadComponents view) {
                if (mDelegate != null) {
                    mDelegate.onTap(view);
                }
            }
        });
        mContainer.addView(mConfirmBtn);

        layoutViews();
    }

    @Override
    public void setData(JSONObject data) {
        super.setData(data);


    }

    public void setDescription(String description) {
        if (mDescription != null) {
            mDescription.setText(description);

        }
    }


    private void layoutViews() {
        int margin = dpToPx(16);
        int logoSize = dpToPx(50);


        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(margin, margin, margin, margin);
        mContainer.setLayoutParams(params);


        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearParams.gravity = Gravity.CENTER_HORIZONTAL;

        linearParams.topMargin = margin;

        mDescription.setLayoutParams(linearParams);


        linearParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, logoSize / 2);
        mConfirmBtn.setLayoutParams(linearParams);

        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(params);

    }


}
