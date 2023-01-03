package com.example.scoutchallenge.views;

import android.content.Context;
import android.view.View;

import com.example.scoutchallenge.Core;
import com.example.scoutchallenge.models.MemberModule;

public class MoufawadHomeView extends HomeView {

    @Override
    public void init(Context ctx, View view) {
        super.init(ctx, view);
        setMenu(MemberModule.MOUFAWAD);

    }


}
