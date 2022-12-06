package com.example.scoutchallenge.helpers;

import com.example.scoutchallenge.R;

import org.json.JSONObject;

import java.util.HashMap;

public class D {

    // Api
    public final static String URL = "http://api-kachaf.herokuapp.com/api";
    //        public final static String URL = "http://192.168.1.159:8080/api";
    public final static String ASSET_URL = "http://192.168.119.184:8080";


    public final static String SALAT_ROOT = "/get-prayer";
    public final static String GET_USER_SQUAD = "/get-user-squad";
    public final static String LOGIN_ROOT = "/login";
    public final static String GET_SQUAD_CATEGORIES = "/get-squad-categories";
    public final static String GET_SQUAD_ACTIVITIES = "/get-squad-activities";
    public final static String ADD_ACTIVITIES = "/add-activities";
    public final static String ADD_USER_TO_ACTIVITY = "/add-user-activities";
    public final static String CHECK_EMAIL = "/check-Email";
    public final static String ADD_USER = "/register";
    public final static String DELET_USER_ACTIVITIES = "/delete-user-activities";
    public final static String UPDATE_USER = "/update-user";
    public final static String DELETE_ACTIVITIES = "/delete-activities";
    public final static String DELETE_USER_TALIAA = "/delete-user-taliaa";

    //taliaa Api
    public final static String GET_TALIAA = "/get-taliaa";
    public final static String ADD_TALIAA = "/add-taliaa";
    public final static String DELETE_TALIAA = "/delete-taliaa";
    public final static String ADD_USER_TALIAA = "/add-user-taliaa";


    //flag
    public static final boolean IS_STIMILATE_ADD_USER_ENABLED = false;


    protected static HashMap<String, Integer> mResourceMap;


    public static int getResourceId(String resource) {

        if (mResourceMap == null) {
            mResourceMap = new HashMap<>();
        }

        mResourceMap.put("logo", new Integer(R.drawable.logo));
        mResourceMap.put("locked_eyes", new Integer(R.drawable.locked_eyes));
        mResourceMap.put("unlocked_eyes", new Integer(R.drawable.unlocked_eyes));
        mResourceMap.put("koran", new Integer(R.drawable.koran));
        mResourceMap.put("qr_code", new Integer(R.drawable.qr_code));
        mResourceMap.put("onsor", new Integer(R.drawable.onsor));
        mResourceMap.put("onsor1", new Integer(R.drawable.onsor1));
        mResourceMap.put("add_icon", new Integer(R.drawable.add));
        mResourceMap.put("talaa_icon", new Integer(R.drawable.talaa_icon));
        mResourceMap.put("date_icon", new Integer(R.drawable.date_icom));
        mResourceMap.put("user", new Integer(R.drawable.user));
        mResourceMap.put("class_icon", new Integer(R.drawable.class_icon));
        mResourceMap.put("scouut_category_icon", new Integer(R.drawable.scouut_category_icon));
        mResourceMap.put("other", new Integer(R.drawable.other));
        mResourceMap.put("checked_icon", new Integer(R.drawable.checked_icon));
        mResourceMap.put("unchecked_icon", new Integer(R.drawable.unchecked_icon));
        mResourceMap.put("plus_icon", new Integer(R.drawable.plus));
        mResourceMap.put("false_icon", new Integer(R.drawable.false_icon));

        if (mResourceMap.containsKey(resource)) {
            return mResourceMap.get(resource).intValue();
        }
        return 0;
    }


}
