package com.example.scoutchallenge.views.cells;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.scoutchallenge.R;
import com.example.scoutchallenge.conponents.CircularImageView;
import com.example.scoutchallenge.conponents.HeadComponents;
import com.example.scoutchallenge.conponents.MImageComponent;
import com.example.scoutchallenge.conponents.MTextView;
import com.example.scoutchallenge.helpers.JsonHelper;
import com.example.scoutchallenge.interfaces.DidOnTap;
import com.example.scoutchallenge.interfaces.OnObjectCallBack;
import com.example.scoutchallenge.models.MemberModule;
import com.example.scoutchallenge.network.MImageLoader;

import org.json.JSONObject;

public class UserCell extends HeadComponents {

    public MemberModule userModule = new MemberModule();
    protected CircularImageView mImage;
    protected MTextView mNameLabel;
    protected MTextView mUserInfo;
    protected MImageComponent mIcon;
    protected HeadComponents mLine;
    public OnObjectCallBack mDelegate;
    public boolean isKoraanCell = false;

    public UserCell(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UserCell(@NonNull Context context) {
        super(context);
    }

    @Override
    public void init(Context ctx, AttributeSet attrs) {
        super.init(ctx, attrs);

        mImage = new CircularImageView(ctx);
        mImage.setImageResource(getImage("onsor"));
        addView(mImage);

        mNameLabel = new MTextView(ctx);
        mNameLabel.setText("ديب شموط");
        mNameLabel.setTextSize(18);
        mNameLabel.getLabel().setGravity(Gravity.CENTER);
        mNameLabel.setTextColor(getColor(R.color.headColor));
        addView(mNameLabel);

        mUserInfo = new MTextView(ctx);
        mUserInfo.setText("ديب شموط");
        mUserInfo.setTextSize(14);
        mUserInfo.getLabel().setGravity(Gravity.CENTER);
        mUserInfo.setTextColor(getColor(R.color.hint));
        addView(mUserInfo);

        mIcon = new MImageComponent(ctx);
        mIcon.setImageResource(getImage("green_koran"));
        mIcon.setOnTapListener(new DidOnTap() {
            @Override
            public void onTap(HeadComponents view) {
                if (mDelegate != null) {
                    if (mData != null) {
                        mDelegate.OnObject(mData);
                    }
                }
            }
        });
        mIcon.hide();
        addView(mIcon);

        mLine = new HeadComponents(ctx);
        mLine.setBackgroundColor(getColor(R.color.headColor));
//        addView(mLine);

//        GradientDrawable gradientDrawable = getDrawable(GradientDrawable.RECTANGLE, R.color.white, -1, -1, -1);
//        gradientDrawable.setStroke(3, getColor(R.color.secondColor));
//        setBackground(gradientDrawable);

        setElevation(2);
        layoutViews();
    }

    public void layoutViews() {
        int cellSize = dpToPx(150);
        int iconSize = dpToPx(80);
        int twoDp = dpToPx(2);
        int margin = dpToPx(16);

        LayoutParams params = new LayoutParams(iconSize, iconSize);
        params.topMargin = margin;
        params.setMarginStart(margin);
        params.bottomMargin = margin;
        mImage.setLayoutParams(params);


        params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.START;
        params.setMarginStart(iconSize + margin * 2);
        params.topMargin = margin * 2;
        params.setMarginEnd(margin / 2);
        mNameLabel.setLayoutParams(params);

        params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.START;
        params.setMarginStart(iconSize + margin * 2);
        params.topMargin = margin * 4;
        params.setMarginEnd(margin / 2);
        params.bottomMargin = margin;
        mUserInfo.setLayoutParams(params);

        params = new LayoutParams(iconSize / 2, iconSize / 2);
        params.gravity = Gravity.CENTER_VERTICAL;
        params.setMarginEnd(margin / 2);
        mIcon.setLayoutParams(params);


        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(2));
        params.gravity = Gravity.BOTTOM;
        mLine.setLayoutParams(params);

        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = margin;
        params.setMarginStart(margin);
        params.setMarginEnd(margin);
        setLayoutParams(params);
    }

    @Override
    public void setData(JSONObject data) {
        super.setData(data);
        if (data != null) {
            JSONObject user = JsonHelper.getJSONObject(data, "userId");
            if (user == null) {
                user = data;
            }

            if (user != null) {
                userModule.setData(user);
                mNameLabel.setText(userModule.getmName());
                MImageLoader.loadWithGlide(userModule.getImageUrl(), 0, mImage.getImage());
                mUserInfo.setText(userModule.getTaliaaName());
            }
            if (isKoraanCell) {
                mIcon.show();
            }
        }
    }

}

