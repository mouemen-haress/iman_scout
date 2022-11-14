package com.example.scoutchallenge.helpers;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.example.scoutchallenge.conponents.HeadComponents;
import com.example.scoutchallenge.views.HeadView;

public class AnimationHelper {


    public static Animation shimmer(View view, float startAlpha, float endAlpha, int durartion, boolean shouldStart) {
        AlphaAnimation animation = new AlphaAnimation(startAlpha, endAlpha);
        animation.setDuration(durartion);
        animation.setRepeatCount(Animation.INFINITE);
        if (shouldStart) {
            view.startAnimation(animation);
        }
        return animation;
    }

    public static Animation rotationAnimation(View view, boolean shouldStart) {
        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(1500);
        rotate.setRepeatMode(Animation.INFINITE);
        rotate.setInterpolator(new LinearInterpolator());
        if (shouldStart) {
            view.startAnimation(rotate);
        }
        return rotate;


    }

    public static void translateView(View components, int fromX, int toX, int fromY, int toY) {
        Animation animation = new TranslateAnimation(fromX, toX, fromY, toY);
        animation.setDuration(500);
        components.startAnimation(animation);
    }


}
