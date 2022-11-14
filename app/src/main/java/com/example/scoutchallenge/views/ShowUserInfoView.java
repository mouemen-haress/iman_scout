package com.example.scoutchallenge.views;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.scoutchallenge.conponents.HeadComponents;
import com.example.scoutchallenge.interfaces.DidOnTap;

public class ShowUserInfoView extends AddUserView {


    @Override
    public void init(Context ctx, View view) {
      super.init(ctx,view);
    }

    @Override
    public void layoutViews() {
        super.layoutViews();

        int margin = dpToPx(16);
        int btnHeight = dpToPx(40);
        int icon = dpToPx(120);

        mCategoriesMenu.measure(0, 0);
        int categoriesHeight = mCategoriesMenu.getMeasuredHeight();




    }
}
