package com.example.scoutchallenge.conponents;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.NonNull;

public class MImageComponent extends HeadComponents {

    protected ImageView mImage;

    public MImageComponent(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MImageComponent(@NonNull Context context) {
        super(context);
    }


    @Override
    public void init(Context ctx, AttributeSet attr) {
        super.init(ctx, attr);

        mImage = new ImageView(ctx);
        addView(mImage);
    }

    public void setImageResource(int resource) {
        mImage.setImageResource(resource);
    }

    public ImageView getImage() {
        return mImage;
    }
}
