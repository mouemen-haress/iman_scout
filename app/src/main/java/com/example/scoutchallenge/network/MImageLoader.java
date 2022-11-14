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

public class MImageLoader {
    private static int TIME_OUT = 40000;


    public static void loadWithGlide(String pictureUrl, int placeHolder, ImageView imageView) {
        try {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(placeHolder)
                    .error(placeHolder)
                    .dontAnimate()
                    .set(HttpGlideUrlLoader.TIMEOUT, TIME_OUT);

            Glide.with(App.getSharedInstance().mMyActivity).applyDefaultRequestOptions(options).load(pictureUrl == null ? pictureUrl : pictureUrl).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    if (e != null) {

                    }
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    return false;
                }
            }).into(imageView);
        } catch (Exception e) {
        }
    }


}
