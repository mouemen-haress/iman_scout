package com.example.scoutchallenge.views;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.scoutchallenge.R;
import com.example.scoutchallenge.backend.BackendProxy;
import com.example.scoutchallenge.helpers.AnimationHelper;
import com.example.scoutchallenge.interfaces.CallBack;

public class splashView extends HeadView {

    protected ImageView mLogo;
    protected CardView mCardView;
    protected TextView mText;


    public splashView() {
        // Required empty public constructor
    }


    public static splashView newInstance(String param1, String param2) {
        splashView fragment = new splashView();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.splash_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCardView = view.findViewById(R.id.card);
        mText = view.findViewById(R.id.resp);

        BackendProxy.getInstance().mSalatTimeManager.getSlatTime(new CallBack() {
            @Override
            public void onResult(String response) {
                if (response != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mText.setText(response);

                        }
                    });
                }
            }
        });
        initAnimation();
    }

    private void initAnimation() {
//        AnimationSet s = new AnimationSet(false);
//        Animation shimmerAnimation = AnimationHelper.shimmer(mLogo, 0.5f, 1f, 1000, false);
//        s.addAnimation(shimmerAnimation);
//        mLogo.startAnimation(s);

//        AnimationHelper.rotationAnimation(mCardView, true);

    }

    @Override
    public void init(Context ctx, View view) {
        super.init(ctx, view);


    }

}