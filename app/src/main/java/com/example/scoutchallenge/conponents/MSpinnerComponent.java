package com.example.scoutchallenge.conponents;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;

public class MSpinnerComponent extends HeadComponents {
    Spinner mSpinnerList;

    String[] mItems;
    ArrayAdapter<String> mAdapter;

    public MSpinnerComponent(@NonNull Context context) {
        super(context);
    }

    @Override
    public void init(Context ctx) {
        super.init(ctx);

        mSpinnerList = new Spinner(ctx);
        mItems = new String[]{};

        mAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, mItems);

        mSpinnerList.setAdapter(mAdapter);
        addView(mSpinnerList);


    }

    public void setItems(String[] items) {
        mItems = items;
        mAdapter.notifyDataSetChanged();
    }
}
