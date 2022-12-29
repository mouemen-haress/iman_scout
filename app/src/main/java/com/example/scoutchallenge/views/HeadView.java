package com.example.scoutchallenge.views;

import static android.view.View.GONE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.Toast;

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
import com.example.scoutchallenge.models.MemberModule;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONObject;

public class HeadView extends Fragment {
    public static final int HEADER_LOG = 0;
    public static final int ACTION_BTN = 1;
    public static final int PROFILE_BTN = 1;

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
    protected MImageComponent mActionBtn;
    protected MImageComponent mProfile;


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
        mHeader.setVisibility(GONE);
        mHeader.setElevation(10);
        mHeader.setBackgroundColor(getColor(R.color.headColor));
        mRootView.addView(mHeader);

        mActionBtn = new MImageComponent(getContext());
        mActionBtn.hide();
        mActionBtn.mIndex = ACTION_BTN;
        mHeader.addView(mActionBtn);
        mActionBtn.setOnTapListener(view1 -> {
            onHeadBtnClicked(view1);
        });

        mProfile = new MImageComponent(getContext());
        mProfile.mIndex = PROFILE_BTN;
        mProfile.setImageResource(getImage("settings"));
        mHeader.addView(mProfile);
        mProfile.setOnTapListener(view1 -> {
            onHeadBtnClicked(view1);
        });
        mLogo = new MImageComponent(getContext());
        GradientDrawable gradientDrawable = getDrawable(GradientDrawable.OVAL, -1, dpToPx(250), 2,getColor(R.color.secondColor));
        mLogo.mIndex = HEADER_LOG;
        mLogo.setImageResource(getImage("logo"));
        mLogo.setBackground(gradientDrawable);
        int padding = dpToPx(3);
        mLogo.setPadding(padding,padding,padding,padding);
        mLogo.getImage().setColorFilter(getColor(R.color.white));
        mHeader.addView(mLogo);
        mLogo.setOnTapListener(view1 -> {
            onHeadBtnClicked(view1);
        });

        mRootView.setBackgroundColor(getColor(R.color.secondColor));

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
        int logoSize = dpToPx(32);
        int headerSize = logoSize / 2 + dpToPx(16);


        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,Tools.getBottomNavHeight());
        mHeader.setLayoutParams(params);

        params = new FrameLayout.LayoutParams(logoSize, logoSize);
        params.gravity = Gravity.END | Gravity.CENTER_VERTICAL;
        params.setMarginEnd(logoSize / 2);
        mLogo.setLayoutParams(params);

        params = new FrameLayout.LayoutParams(logoSize, logoSize);
        params.gravity = Gravity.CENTER_VERTICAL | Gravity.START;
        params.setMarginStart((logoSize / 2) * 2 + logoSize);
        mActionBtn.setLayoutParams(params);

        params = new FrameLayout.LayoutParams(logoSize, logoSize);
        params.gravity = Gravity.CENTER_VERTICAL | Gravity.START;
        params.setMarginStart(logoSize / 2);
        mProfile.setLayoutParams(params);

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

    public void showToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
    }

    public void showLockedLoading() {
        switchPopupState(true);
        mLoaderContainer.setVisibility(View.VISIBLE);
        mPopupContainer.setVisibility(View.VISIBLE);
        AnimationHelper.shimmer(mLoader, 0.5f, 1f, 700, true);


    }

    public void hideLockedLoading() {
        runOnUiThread(() -> {

            switchPopupState(false);
            mLoaderContainer.setVisibility(GONE);
            mPopupContainer.setVisibility(GONE);
            mPopupView.setVisibility(GONE);
            Animation currentAnimation = mLoader.getAnimation();
            if (currentAnimation != null) {
                currentAnimation.cancel();
            }
        });


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
        Tools.pushView(target, bundle);
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
        mActionBtn.show();
    }

    public void hideHeader() {
        mHeader.setVisibility(GONE);
        mActionBtn.hide();
    }

    public void setHeadBtn(int rsc) {
        mActionBtn.setImageResource(rsc);
    }

    public int getHeadSize() {
       return getBottomNavHeight();
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

    public void onNotification(String notificationType, JSONObject data) {

    }

    public void setMenu(String position) {
        runOnUiThread(() -> {

            BottomNavigationView menu = getActivity().findViewById(R.id.tab_Bar);
            if (position.equalsIgnoreCase(MemberModule.HELPING_LEADER)) {
                menu.getMenu().clear(); //clear old inflated items.
                menu.inflateMenu(R.menu.helping_leader_bottom_menu);
            }
        });
    }
}