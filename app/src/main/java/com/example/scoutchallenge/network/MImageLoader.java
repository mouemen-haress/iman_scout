package com.example.scoutchallenge.network;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.load.model.stream.HttpGlideUrlLoader;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.scoutchallenge.App;
import com.example.scoutchallenge.R;
import com.example.scoutchallenge.helpers.AnimationHelper;
import com.example.scoutchallenge.helpers.Tools;

public class MImageLoader {
    private static int TIME_OUT = 40000;


    public static void loadWithGlide(String pictureUrl, int placeHolder, ImageView imageView) {
        try {
            AnimationHelper.shimmer(imageView, 0.3f, 1f, 700, true);

            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(Tools.getImage("user"))
                    .error(Tools.getImage("false_icon"))
                    .dontAnimate()
                    .set(HttpGlideUrlLoader.TIMEOUT, TIME_OUT);

            Glide.with(App.getSharedInstance().mMyActivity).applyDefaultRequestOptions(options).load(pictureUrl == null ? pictureUrl : pictureUrl).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    if (e != null) {

                    }
                    imageView.clearAnimation();
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    imageView.clearAnimation();
                    return false;
                }
            }).into(imageView);
        } catch (Exception e) {
        }
    }


}
