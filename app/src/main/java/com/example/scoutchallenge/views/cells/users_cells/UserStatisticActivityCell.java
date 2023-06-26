package com.example.scoutchallenge.views.cells.users_cells;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;

import com.example.scoutchallenge.Core;
import com.example.scoutchallenge.helpers.DateHelper;
import com.example.scoutchallenge.models.MemberModule;
import com.example.scoutchallenge.network.MImageLoader;
import com.example.scoutchallenge.views.cells.UserCell;

import org.json.JSONObject;

public class UserStatisticActivityCell extends UserCell {

    public UserStatisticActivityCell(@NonNull Context context) {
        super(context);
    }

    @Override
    public void init(Context ctx, AttributeSet attrs) {
        super.init(ctx, attrs);
    }

    @Override
    public void setData(JSONObject data) {
        super.setData(data);
        if (data != null) {
            mNameLabel.setText(data.optString("title"));
            mUserInfo.setText(DateHelper.convertMongoDate(data.optString("date")));
            MemberModule currentUser = Core.getInstance().getCurrentMemberModel();
            if (currentUser != null) {
                MImageLoader.loadWithGlide(currentUser.getImageUrl(), 0, mImage.getImage());
            }
        }
    }
}
