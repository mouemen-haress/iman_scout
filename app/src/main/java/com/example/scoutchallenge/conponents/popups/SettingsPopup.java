package com.example.scoutchallenge.conponents.popups;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;

import com.example.scoutchallenge.App;
import com.example.scoutchallenge.Core;
import com.example.scoutchallenge.R;
import com.example.scoutchallenge.conponents.CircularImageView;
import com.example.scoutchallenge.conponents.HeadComponents;
import com.example.scoutchallenge.conponents.MButtonComponent;
import com.example.scoutchallenge.conponents.MCircularImage;
import com.example.scoutchallenge.conponents.MImageComponent;
import com.example.scoutchallenge.conponents.MTextView;
import com.example.scoutchallenge.helpers.D;
import com.example.scoutchallenge.helpers.Tools;
import com.example.scoutchallenge.models.MemberModule;
import com.example.scoutchallenge.network.MImageLoader;
import com.example.scoutchallenge.utils.LocalStorage;

public class SettingsPopup extends HeadComponents {
    protected ScrollView mScrollView;
    protected CircularImageView mProfileImage;
    protected LinearLayout mContainer;
    protected MTextView mName;
    protected MTextView mPosition;
    protected MTextView mMoufawadye;
    protected MTextView mFawje;
    protected MTextView mSquad;
    protected MTextView mTaliaa;
    protected MButtonComponent mLogoutButton;

    public SettingsPopup(@NonNull Context context) {
        super(context);
    }

    @Override
    public void init(Context ctx) {
        super.init(ctx);
    }

    @Override
    public void init(Context ctx, AttributeSet attrs) {
        super.init(ctx, attrs);


        mScrollView = new ScrollView(ctx);
        addView(mScrollView);


        mContainer = new LinearLayout(ctx);
        mContainer.setOrientation(LinearLayout.VERTICAL);
        mScrollView.addView(mContainer);


        mProfileImage = new CircularImageView(ctx);
        mContainer.addView(mProfileImage);


        mName = new MTextView(ctx);
        mName.setTextSize(dpToPx(17));
        mName.setTypeFace(Typeface.BOLD);
        mName.setTextColor(Color.BLACK);
        mContainer.addView(mName);

        mPosition = new MTextView(ctx);
        mPosition.getLabel().setTextAlignment(TEXT_ALIGNMENT_TEXT_START);
        mPosition.setTextSize(dpToPx(13));
        mContainer.addView(mPosition);

        mMoufawadye = new MTextView(ctx);
        mMoufawadye.getLabel().setTextAlignment(TEXT_ALIGNMENT_TEXT_START);
        mMoufawadye.setTextSize(dpToPx(13));
        mContainer.addView(mMoufawadye);

        mFawje = new MTextView(ctx);
        mFawje.getLabel().setTextAlignment(TEXT_ALIGNMENT_TEXT_START);
        mFawje.setTextSize(dpToPx(13));
        mContainer.addView(mFawje);

        mSquad = new MTextView(ctx);
        mSquad.getLabel().setTextAlignment(TEXT_ALIGNMENT_TEXT_START);
        mSquad.setTextSize(dpToPx(13));
        mContainer.addView(mSquad);


        mTaliaa = new MTextView(ctx);
        mTaliaa.getLabel().setTextAlignment(TEXT_ALIGNMENT_TEXT_START);
        mTaliaa.setTextSize(dpToPx(13));
        mContainer.addView(mTaliaa);
        if (!Core.getInstance().isOnsor()) {
            mTaliaa.hide();
        }

        mLogoutButton = new MButtonComponent(ctx);
        mLogoutButton.setText(getText(R.string.log_out));
        mLogoutButton.setOnTapListener(view -> {
            doLogout();
        });
        mContainer.addView(mLogoutButton);

        initValue();
        layoutViews();
    }

    private void doLogout() {
        LocalStorage.clearAllLocalStorage();
        Intent intent = App.getSharedInstance().mMyActivity.getIntent();
        App.getSharedInstance().mMyActivity.finish();
        App.getSharedInstance().mMyActivity.startActivity(intent);
    }

    private void layoutViews() {
        int margin = dpToPx(16);
        int iconSize = dpToPx(120);
        int buttonWidth = dpToPx(100);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Tools.getScreenHeight(Tools.isPortrait()) - D.TAB_BAR_HEIGHT);
        setLayoutParams(params);

        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mScrollView.setLayoutParams(params);


        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mContainer.setLayoutParams(params);

        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(iconSize, iconSize);
        linearParams.gravity = Gravity.CENTER_HORIZONTAL;
        linearParams.topMargin = margin;
        mProfileImage.setLayoutParams(linearParams);


        linearParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearParams.gravity = Gravity.CENTER_HORIZONTAL;
        linearParams.topMargin = margin;
        mName.setLayoutParams(linearParams);
        mPosition.setLayoutParams(linearParams);
        mMoufawadye.setLayoutParams(linearParams);
        mFawje.setLayoutParams(linearParams);
        mSquad.setLayoutParams(linearParams);
        mTaliaa.setLayoutParams(linearParams);

        linearParams = new LinearLayout.LayoutParams(buttonWidth, buttonWidth/2);
        linearParams.setMarginStart(margin);
        linearParams.setMarginEnd(margin);
        linearParams.topMargin = margin;
        linearParams.gravity = Gravity.CENTER_HORIZONTAL;
        mLogoutButton.setLayoutParams(linearParams);


    }

    private void initValue() {
        MemberModule currentMember = new MemberModule();
        currentMember = Core.getInstance().getCurrentMemberModel();
        if (currentMember != null) {
            MImageLoader.loadWithGlide(currentMember.getImageUrl(), 0, mProfileImage.getImage());
            mName.setText(currentMember.getmName());
            mPosition.setText(currentMember.getPositionFullName());
            mMoufawadye.setText(currentMember.getmMoufawadyeName());
            mFawje.setText(currentMember.getFawjeName());
            mSquad.setText(currentMember.getFerkaName());
            mTaliaa.setText(getText(R.string.taliaa) + ": " + currentMember.getTaliaaName());
        }
    }
}
