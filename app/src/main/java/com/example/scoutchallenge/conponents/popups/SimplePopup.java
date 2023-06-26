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
import com.example.scoutchallenge.helpers.Tools;
import com.example.scoutchallenge.interfaces.DidOnTap;
import com.example.scoutchallenge.interfaces.OnSimplePopupDelegate;

import org.json.JSONObject;

public class SimplePopup extends HeadComponents {

    LinearLayout mContainer;
    protected MTextView mDescription;
    LinearLayout mButtonContainer;
    protected MButtonComponent mConfirmBtn;
    protected MButtonComponent mCancelBtn;

    public OnSimplePopupDelegate mDelegate;


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
        mDescription.getLabel().setTextAlignment(TEXT_ALIGNMENT_CENTER);
        mDescription.getLabel().setTextSize(14);
        mContainer.addView(mDescription);

        mButtonContainer = new LinearLayout(ctx);
        mButtonContainer.setOrientation(LinearLayout.HORIZONTAL);
        mContainer.addView(mButtonContainer);

        mConfirmBtn = new MButtonComponent(ctx);
        mConfirmBtn.setText(getString(R.string.okk));
        mConfirmBtn.setOnTapListener(new DidOnTap() {
            @Override
            public void onTap(HeadComponents view) {
                if (mDelegate != null) {
                    mDelegate.onConfirm();
                }
            }
        });
        mButtonContainer.addView(mConfirmBtn);

        mCancelBtn = new MButtonComponent(ctx);
        mCancelBtn.setText(getString(R.string.no));
        mCancelBtn.hide();
        mCancelBtn.setOnTapListener(new DidOnTap() {
            @Override
            public void onTap(HeadComponents view) {
                Tools.hidePopup();
                if (mDelegate != null) {
                    mDelegate.onCancel();
                }
            }
        });
        mButtonContainer.addView(mCancelBtn);

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


        linearParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearParams.setMargins(margin, margin, margin, margin);
        mButtonContainer.setLayoutParams(linearParams);

        linearParams = new LinearLayout.LayoutParams(0, logoSize);
        linearParams.weight = 2;
        linearParams.setMarginEnd(margin);
        mConfirmBtn.setLayoutParams(linearParams);
        mCancelBtn.setLayoutParams(linearParams);


        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(params);

    }

    public void showCancelButton() {
        mCancelBtn.show();
    }


}
