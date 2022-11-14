package com.example.scoutchallenge.conponents;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.compose.ui.graphics.Outline;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.ShapeAppearanceModel;

import java.net.URI;

public class CircularImageView extends HeadComponents {

    protected ShapeableImageView mImage;


    public CircularImageView(@NonNull Context context) {
        super(context);
    }

    @Override
    public void init(Context ctx, AttributeSet attrs) {
        mImage = new ShapeableImageView(ctx);
//
//        GradientDrawable gradientDrawable = new GradientDrawable();
//        gradientDrawable.setShape(shape);
//
//        gradientDrawable.setColor(getColor(color));
//
//
//        gradientDrawable.setCornerRadius(50 % 100);


        mImage.setShapeAppearanceModel(new ShapeAppearanceModel()
                .toBuilder()
                .setAllCorners(CornerFamily.ROUNDED, (dpToPx(80) * 50) / 100)
                .build());

        mImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        addView(mImage);

        layoutViews();
    }

    private void layoutViews() {

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mImage.setLayoutParams(params);
    }

    public void setImageResource(int rcs) {
        mImage.setImageResource(rcs);
    }

    public void setImageURI(Uri uri) {
        mImage.setImageURI(uri);
    }

    public ShapeableImageView getImage(){
        return mImage;
    }

}
