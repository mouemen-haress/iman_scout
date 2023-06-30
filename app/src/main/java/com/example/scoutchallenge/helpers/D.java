package com.example.scoutchallenge.helpers;

import com.example.scoutchallenge.R;

import java.util.HashMap;

public class D {

    // Api
//    public final static String URL = "http://api-kachaf.herokuapp.com/api";
    public final static String URL = "http://192.168.1.111:8080";
    public final static String ASSET_URL = "https://nodekachaf.onrender.com";

//    public final static String URL = "http://192.168.1.111:8080/api";
//    public final static String ASSET_URL = "http://192.168.1.111:8080";


    public final static String GET_MY_AMASER = "/getMyAnaser";
    public final static String LOGIN_ROOT = "/login";


    //taliaa Api
    public final static String GET_TALIAA = "/getMyAllTaliaa";




    //flag
    public static final boolean IS_STIMILATE_ADD_USER_ENABLED = false;

    //size
    public static int TAB_BAR_HEIGHT = Tools.dpToPx(50);
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
        mResourceMap.put("settings", new Integer(R.drawable.settings));
        mResourceMap.put("rope", new Integer(R.drawable.rope));
        mResourceMap.put("green_koran", new Integer(R.drawable.green_koran));
        mResourceMap.put("suicide", new Integer(R.drawable.suicide));
        mResourceMap.put("true_icon", new Integer(R.drawable.true_icon));
        mResourceMap.put("analysis", new Integer(R.drawable.analysis));


        if (mResourceMap.containsKey(resource)) {
            return mResourceMap.get(resource).intValue();
        }
        return 0;
    }


}
