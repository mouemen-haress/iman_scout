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

import com.example.scoutchallenge.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.ShapeAppearanceModel;

import java.net.URI;

import de.hdodenhof.circleimageview.CircleImageView;

public class CircularImageView extends HeadComponents {

    protected CircleImageView mImage;


    public CircularImageView(@NonNull Context context) {
        super(context);
    }

    @Override
    public void init(Context ctx, AttributeSet attrs) {
        mImage = new CircleImageView(ctx);
        mImage.setBorderWidth(dpToPx(3));
        mImage.setBorderColor(getColor(R.color.headColor));
//
//        GradientDrawable gradientDrawable = new GradientDrawable();
//        gradientDrawable.setShape(shape);
//
//        gradientDrawable.setColor(getColor(color));
//
//
//        gradientDrawable.setCornerRadius(50 % 100);



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

    public CircleImageView getImage(){
        return mImage;
    }

}
