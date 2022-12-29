package com.example.scoutchallenge.conponents;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.example.scoutchallenge.R;
import com.example.scoutchallenge.activities.MainActivity;
import com.example.scoutchallenge.helpers.D;
import com.example.scoutchallenge.helpers.Tools;
import com.example.scoutchallenge.interfaces.DidOnTap;

import org.json.JSONObject;

public class HeadComponents extends FrameLayout {

    public int mIndex;
    private DidOnTap mListener;
    private Rect mRect;
    TypedArray mTypedArray;
    public Context mContext;
    public JSONObject mData;


    public HeadComponents(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = getContext();

        mTypedArray = mContext.obtainStyledAttributes(attrs,
                R.styleable.CustomView);

        init(context, attrs);
    }


    public HeadComponents(@NonNull Context context) {
        super(context);
        mContext = getContext();
        init(context, null);
    }


    public void init(Context ctx, AttributeSet attrs) {
        setOnTapListener(null);
    }


    public void init(Context ctx) {

    }


    public int getImage(String key) {
        return D.getResourceId(key);
    }


    public Object geResourcefromXml(int res) {
        if (mTypedArray == null) {
            return "";
        }
        int count = mTypedArray.getIndexCount();
        try {

            for (int i = 0; i < count; ++i) {
                int attr = mTypedArray.getIndex(i);

                if (res == attr) {
                    int type = mTypedArray.getType(attr);
                    switch (type) {
                        case TypedValue.TYPE_STRING:
                            return mTypedArray.getString(i);

                        case TypedValue.TYPE_INT_BOOLEAN:
                            return mTypedArray.getBoolean(i, false);

                        case TypedValue.TYPE_INT_DEC:
                            return mTypedArray.getInt(i, 0);

                    }
                }
            }
        } finally {
            mTypedArray.recycle();
        }
        return null;

    }


    public void setOnTapListener(DidOnTap delegate) {
        if (delegate == null) {
            return;
        }
        mListener = delegate;

        this.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mRect = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
                        onTouchDown();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        onTouchUp();
                        break;
                    case MotionEvent.ACTION_UP:
                        onTouchUp();
                        if (mRect.contains(view.getLeft() + (int) motionEvent.getX(), view.getTop() + (int) motionEvent.getY())) {
                            onTouchUpInside();
                        }
                        return false;

                }
                return true;
            }
        });
    }


    protected void onTouchDown() {
        setAlpha(.5f);
    }

    protected void onTouchUp() {
        setAlpha(1f);
    }

    protected void onTouchUpInside() {
        if (mListener != null) mListener.onTap(this);
    }

    protected String getText(int id) {
        return getResources().getString(id);

    }

    public int dpToPx(int dp) {
        return Tools.dpToPx(dp);
    }

    public void setData(JSONObject data) {
        mData = data;
    }

    public GradientDrawable getDrawable(int shape, int color, float radius, int stroke, int strokeColor) {
        return Tools.getDrawable(shape, color, radius, stroke, strokeColor);
    }

    public static int getBottomNavHeight() {
        return Tools.getBottomNavHeight();
    }

    public int getColor(int color) {
        return Tools.getColor(color);
    }

    public void hide() {
        setVisibility(GONE);
    }

    public void show() {
        setVisibility(VISIBLE);
    }

    public String getString(int rcs) {
        return getResources().getString(rcs);
    }

    public void runOnUiThread(Runnable r) {
        Tools.runOnUIThread(r);
    }

    public void pushView(int target, Bundle bundle) {
        Tools.pushView(target, bundle);
    }

}
