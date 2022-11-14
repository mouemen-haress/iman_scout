package com.example.scoutchallenge.helpers;

import static java.security.AccessController.getContext;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Shader;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.core.content.res.ResourcesCompat;

import com.example.scoutchallenge.App;
import com.example.scoutchallenge.R;
import com.example.scoutchallenge.conponents.HeadComponents;
import com.example.scoutchallenge.conponents.popups.SimplePopup;
import com.example.scoutchallenge.interfaces.DidOnTap;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;

public class Tools {
    static BottomSheetDialog mDialog;


    public static int dpToPx(int dp) {
        Resources r = App.getSharedInstance().mMyActivity.getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        return px;
    }

    private static int getNavigationBarHeight() {
        Activity activity = App.getSharedInstance().mMyActivity;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics metrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int usableHeight = metrics.heightPixels;
            activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
            int realHeight = metrics.heightPixels;
            if (realHeight > usableHeight)
                return realHeight - usableHeight;
            else
                return 0;
        }
        return 0;
    }

    public static int getScreenHeight(boolean isPortrait) {
        Activity activity = App.getSharedInstance().mMyActivity;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        if (isPortrait) {
            height = Math.max(height, width);
        } else height = Math.min(width, height);
        return height;
    }

    public static int getScreenWidth(Boolean isPortrait) {
        Activity activity = App.getSharedInstance().mMyActivity;
        Display mDisplay = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        try {
            mDisplay.getSize(size);
        } catch (NoSuchMethodError err) {
            mDisplay.getSize(size);
        }
        int width = 0;
        if (isPortrait) width = Math.min(size.x, size.y);
        else width = Math.max(size.x, size.y);
        return width;

    }

    public static boolean isPortrait() {
        Activity activity = App.getSharedInstance().mMyActivity;

        int orientation = activity.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT)
            return true;
        return false;
    }


    public static GradientDrawable getDrawable(int shape, int color, float radius, int stroke, int strokeColor) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        if (shape != -1) {
            gradientDrawable.setShape(shape);
        }

        if (color != -1) {

            gradientDrawable.setColor(getColor(color));
        }

        if (radius != -1) {
            gradientDrawable.setCornerRadius(radius);
        }


        if (stroke != -1 && strokeColor != -1) {
            gradientDrawable.setStroke(stroke, strokeColor);
        }

        return gradientDrawable;
    }

    public static int getBottomNavHeight() {
        Resources resources = App.getSharedInstance().mMyActivity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public static int getColor(int color) {
        return ResourcesCompat.getColor(App.getSharedInstance().mMyActivity.getResources(), color, null); //without theme
//        ResourcesCompat.getColor(getResources(), R.color.your_color, your_theme); //with theme

    }


    public static void runOnUIThread(Runnable r) {
        new Handler(Looper.getMainLooper()).post(r);

    }

    public static View generateLine() {
        View view = new View(App.getSharedInstance().mMyActivity);
        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(2));
        linearParams.topMargin = dpToPx(16);
        view.setLayoutParams(linearParams);

        view.setBackgroundColor(getColor(R.color.headColor));
        return view;
    }

    public static void hidePopup() {
        mDialog.hide();
    }

    public static void showPopup(HeadComponents view) {
//        mPopupContainer.setVisibility(View.VISIBLE);
//        mPopupView.removeAllViews();
//        mPopupView.addView(view);
//
//        mPopupView.measure(0, 0);
//        AnimationHelper.translateView(mPopupView, 0, 0, getScreenHeight(isPortrait()), 0);
//        switchPopupState(false);

        mDialog = new BottomSheetDialog(App.getSharedInstance().mMyActivity);
        mDialog.setCancelable(true);
        mDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mDialog.setContentView(view);

        mDialog.show();

    }

    public static void showSimplePopup(String des) {
        runOnUIThread(() -> {

            SimplePopup simplePopup = new SimplePopup(App.getSharedInstance().mMyActivity);
            simplePopup.setDescription(des);

            simplePopup.mDelegate = new DidOnTap() {
                @Override
                public void onTap(HeadComponents view) {
                    hidePopup();
                }
            };

            mDialog = new BottomSheetDialog(App.getSharedInstance().mMyActivity);
            mDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            mDialog.setContentView(simplePopup);
            mDialog.show();
        });

    }

    public static void showAskingPopup(String des, DidOnTap delegate, DidOnTap cancelDelegate) {
        runOnUIThread(() -> {

            SimplePopup simplePopup = new SimplePopup(App.getSharedInstance().mMyActivity);
            simplePopup.setDescription(des);

            simplePopup.mDelegate = new DidOnTap() {
                @Override
                public void onTap(HeadComponents view) {
                    hidePopup();
                    if (delegate != null) {
                        delegate.onTap(new HeadComponents(App.getSharedInstance().mMyActivity));
                    }
                }
            };

            mDialog = new BottomSheetDialog(App.getSharedInstance().mMyActivity);
            mDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            mDialog.setDismissWithAnimation(true);
            mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    if (cancelDelegate != null) {
                        cancelDelegate.onTap(new HeadComponents(App.getSharedInstance().mMyActivity));
                    }
                }
            });
            mDialog.setContentView(simplePopup);
            mDialog.show();
        });

    }
}
