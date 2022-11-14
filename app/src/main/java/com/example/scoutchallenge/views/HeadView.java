package com.example.scoutchallenge.views;

import static android.view.View.GONE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.example.scoutchallenge.R;
import com.example.scoutchallenge.conponents.HeadComponents;
import com.example.scoutchallenge.conponents.MImageComponent;
import com.example.scoutchallenge.helpers.AnimationHelper;
import com.example.scoutchallenge.helpers.D;
import com.example.scoutchallenge.helpers.Tools;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HeadView extends Fragment {

    public NavController mNavController;
    protected Context mSelf;
    protected View mView;
    protected FrameLayout mPopupContainer;
    protected FrameLayout mPopupView;
    protected CardView mLoaderContainer;
    protected ImageView mLoader;
    protected FrameLayout mRootView;
    protected BottomNavigationView mMainTabBar;
    protected FrameLayout mHeader;
    protected MImageComponent mLogo;
    ActivityResultLauncher<Intent> mLauncher;

    protected boolean mIsLockedPopup = false;
    ActivityResultLauncher<String> mRequestPermissionLauncher;


    public HeadView() {
        // Required empty public constructor
    }

    public static HeadView newInstance(String param1, String param2) {
        HeadView fragment = new HeadView();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.head_layout, container, false);
        mRootView = new FrameLayout(getContext());
        mRootView.setBackgroundResource(R.color.main_background);

        mHeader = new FrameLayout(getContext());
        mHeader.setBackgroundResource(R.color.headColor);
        mHeader.setVisibility(GONE);
        mRootView.addView(mHeader);

        mLogo = new MImageComponent(getContext());
        mLogo.hide();
        GradientDrawable gradientDrawable = getDrawable(GradientDrawable.OVAL, R.color.white, dpToPx(250), -1, -1);
        mLogo.setElevation(10);
        int padding = dpToPx(23);
        mLogo.setPadding(padding, padding, padding, padding);
        mLogo.setBackground(gradientDrawable);
        mRootView.addView(mLogo);

        mLogo.setOnTapListener(view1 -> {
            onHeadBtnClicked(view1);
        });

        layoutViews();
        return mRootView;
    }

    public void onHeadBtnClicked(HeadComponents view1) {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNavController = Navigation.findNavController(this.getView());
        mSelf = getActivity();
        mView = view;

        FillPassedData();
        init(mSelf, mRootView);

    }

    public void FillPassedData() {
    }

    public void init(Context ctx, View view) {


        mPopupContainer = getElementFromParent(R.id.popup_container);
        mPopupContainer.setVisibility(GONE);

        mPopupView = getElementFromParent(R.id.popup_view);

        mLoaderContainer = getElementFromParent(R.id.loader_container);
        mLoaderContainer.setVisibility(GONE);

        mLoader = getElementFromParent(R.id.loader);
//
//        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
//            @Override
//            public void handleOnBackPressed() {
//                handleOnBackButtonPressed();
//            }
//        });

        mMainTabBar = getElementFromParent(R.id.tab_Bar);


        mLauncher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
                    onResultFromLauncher(result);
                });

        mRequestPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean result) {
                        onPermissionResult(result);
                    }
                }
        );


    }

    private void layoutViews() {
        int logoSize = dpToPx(100);
        int headerSize = logoSize / 2 + dpToPx(16);


        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, headerSize);
        mHeader.setLayoutParams(params);

        params = new FrameLayout.LayoutParams(logoSize, logoSize);
        params.topMargin = headerSize - logoSize / 2;
        params.gravity = Gravity.CENTER_HORIZONTAL;
        mLogo.setLayoutParams(params);
    }

    public void runOnUiThread(Runnable r) {
        Tools.runOnUIThread(r);
    }


    public int getImage(String key) {
        return D.getResourceId(key);
    }

    public <T extends View> T getViewById(int id) {
        return mView.findViewById(id);

    }

    public <T extends View> T getElementFromParent(int id) {
        return getActivity().findViewById(id);
    }


    public int getScreenHeight(boolean isPortrait) {
        return Tools.getScreenHeight(isPortrait);
    }

    public int getScreenWidth(boolean isPortrait) {
        return Tools.getScreenWidth(isPortrait);
    }

    public void handleOnBackButtonPressed() {
        if (mIsLockedPopup) {
            return;
        }

        if (mPopupContainer.isShown()) {
            mPopupContainer.setVisibility(GONE);
        } else {
            getActivity().onBackPressed();
        }
    }

    public void popBackStack() {
        mNavController.popBackStack();

    }

    public void popAndPushView(int target) {

    }

    public void showPopup(HeadComponents view) {
        Tools.showPopup(view);

    }

    public void hidePopup() {
        Tools.hidePopup();
    }

    public void showSimplePopup(String des) {
        Tools.showSimplePopup(des);

    }


    public void showLockedLoading() {
        switchPopupState(true);
        mLoaderContainer.setVisibility(View.VISIBLE);
        mPopupContainer.setVisibility(View.VISIBLE);
        AnimationHelper.shimmer(mLoader, 0.5f, 1f, 700, true);


    }

    public void hideLockedLoading() {
        switchPopupState(false);
        mLoaderContainer.setVisibility(GONE);
        mPopupContainer.setVisibility(GONE);
        mPopupView.setVisibility(GONE);

        Animation currentAnimation = mLoader.getAnimation();
        if (currentAnimation != null) {
            currentAnimation.cancel();
        }


    }


    public int dpToPx(int dp) {
        return Tools.dpToPx(dp);
    }

    public void switchPopupState(boolean isMakedLocked) {
        mIsLockedPopup = isMakedLocked;

        if (isMakedLocked) {


            mPopupContainer.setOnTouchListener(null);

        } else {
            mPopupContainer.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    AnimationHelper.translateView(mPopupView, 0, 0, 0, getScreenHeight(isPortrait()));
                    mPopupContainer.setVisibility(GONE);

                    return false;
                }
            });

        }
    }

    public void pushView(int target) {
        mNavController.navigate(target);
    }

    public void pushView(int target, Bundle bundle) {
        mNavController.navigate(target, bundle);
    }

    public void pushAndSetRootView(int lastFragment, int target) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                NavOptions navOptions = new NavOptions.Builder()
                        .setPopUpTo(lastFragment, true)
                        .build();
                mNavController.navigate(target, null, navOptions);
            }
        });


    }


    public boolean isPortrait() {
        return Tools.isPortrait();
    }

    public GradientDrawable getDrawable(int shape, int color, float radius, int stroke, int strokeColor) {
        return Tools.getDrawable(shape, color, radius, stroke, strokeColor);
    }

    public void showTabBar(boolean trueOrFalse) {
        if (trueOrFalse) {
            mMainTabBar.setVisibility(View.VISIBLE);
        } else {
            mMainTabBar.setVisibility(GONE);

        }
    }

    public static int getBottomNavHeight() {
        return Tools.getBottomNavHeight();
    }

    public void showHeader() {
        mHeader.setVisibility(View.VISIBLE);
        mLogo.show();
    }

    public void hideHeader() {
        mHeader.setVisibility(GONE);
        mLogo.hide();
    }

    public void setHeadBtn(int rsc) {
        mLogo.setImageResource(rsc);
    }

    public int getHeadSize() {
        int logoSize = dpToPx(100);
        int headerSize = logoSize / 2 + dpToPx(16);
        return headerSize + logoSize / 2;

    }

    public void requestPermission(String permission) {
        mRequestPermissionLauncher.launch(permission);

    }

    public void requestMultiPermission(String[] permissions) {

    }


    public void onResultFromLauncher(ActivityResult result) {

    }

    public void onPermissionResult(boolean result) {

    }

    public int getColor(int color) {
        return Tools.getColor(color);
    }

    public View generateLine() {
        return Tools.generateLine();
    }
}