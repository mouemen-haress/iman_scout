package com.example.scoutchallenge.interfaces;

import android.view.View;

import org.json.JSONObject;

public interface CategoriesMenuDelegate {
    void didSelectCategoryCell(JSONObject data);

    void didPreventSelectCategoryCell(JSONObject data, View view, int position);

}
