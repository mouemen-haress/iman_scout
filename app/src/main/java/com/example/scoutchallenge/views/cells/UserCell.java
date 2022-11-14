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
import com.example.scoutchallenge.conponents.MTextView;
import com.example.scoutchallenge.helpers.D;
import com.example.scoutchallenge.helpers.JsonHelper;
import com.example.scoutchallenge.network.MImageLoader;

import org.json.JSONObject;

public class UserCell extends HeadComponents {
    protected CircularImageView mImage;
    protected MTextView mNameLabel;

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
        mNameLabel.setTextColor(getColor(R.color.white));
        mNameLabel.setBackground(getDrawable(GradientDrawable.RECTANGLE, R.color.headColor, dpToPx(10), -1, -1));
        addView(mNameLabel);


        GradientDrawable gradientDrawable = getDrawable(GradientDrawable.RECTANGLE, R.color.white, dpToPx(10), -1, -1);
        gradientDrawable.setStroke(3, getColor(R.color.secondColor));
        setBackground(gradientDrawable);
        setElevation(8);

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
        params.gravity = Gravity.CENTER_VERTICAL | Gravity.START;
        params.setMarginStart(iconSize + margin * 2);
        params.setMarginEnd(margin / 2);
        mNameLabel.setPadding(twoDp, twoDp, twoDp, twoDp);
        mNameLabel.setLayoutParams(params);


        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = margin;
        params.setMarginStart(margin);
        params.setMarginEnd(margin);
        setLayoutParams(params);
    }

    @Override
    public void setData(JSONObject data) {
        super.setData(data);
        JSONObject user = JsonHelper.getJSONObject(data, "userId");
        if (user != null) {
            mNameLabel.setText(user.optString("Name"));
            String avat = user.optString("file").split("\\\\")[1];
            MImageLoader.loadWithGlide(D.ASSET_URL + "/" + avat, 0, mImage.getImage());
        }


    }
}
