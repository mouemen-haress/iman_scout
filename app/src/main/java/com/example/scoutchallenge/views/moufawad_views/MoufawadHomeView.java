package com.example.scoutchallenge.views.moufawad_views;

import android.content.Context;
import android.view.View;

import com.example.scoutchallenge.models.MemberModule;
import com.example.scoutchallenge.views.HomeView;

public class MoufawadHomeView extends HomeView {

    @Override
    public void init(Context ctx, View view) {
        super.init(ctx, view);
        setMenu(MemberModule.MOUFAWAD);

    }


}
