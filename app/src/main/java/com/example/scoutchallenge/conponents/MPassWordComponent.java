package com.example.scoutchallenge.conponents;

import android.content.Context;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.scoutchallenge.R;
import com.example.scoutchallenge.interfaces.DidOnTap;

public class MPassWordComponent extends MDrawableEditText {

    protected MImageComponent mEyes;
    protected boolean isPassMode = true;

    public MPassWordComponent(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context ctx, AttributeSet attr) {
        super.init(ctx, attr);

        mEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        mEditText.setHint("********");


        mEyes = new MImageComponent(ctx, attr);
        mEyes.setImageResource(getImage("unlocked_eyes"));
        mEyes.setOnTapListener(new DidOnTap() {
            @Override
            public void onTap(HeadComponents view) {
                toggleEyes();
            }
        });
        addView(mEyes);

        layoutViews();
    }


    public void layoutViews() {
        int iconSize = dpToPx(24);

        measure(0, 0);
        int componentWidth = getMeasuredWidth();

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_VERTICAL | Gravity.END;
        params.setMarginStart(iconSize * 2);
        params.setMarginEnd(iconSize / 2);
        mEditText.setLayoutParams(params);

        params = new LayoutParams(iconSize, iconSize);
        params.gravity = Gravity.CENTER_VERTICAL | Gravity.START;
        params.setMarginStart(iconSize);
        mEyes.setLayoutParams(params);

    }

    private void toggleEyes() {
        if (isPassMode) {
            mEditText.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
            mEyes.setImageResource(getImage("unlocked_eyes"));
            isPassMode = false;
        } else {
            mEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            mEyes.setImageResource(getImage("locked_eyes"));
            isPassMode = true;
        }

    }

}
