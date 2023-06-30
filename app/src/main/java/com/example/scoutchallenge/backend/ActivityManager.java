package com.example.scoutchallenge.backend;

import com.example.scoutchallenge.helpers.D;
import com.example.scoutchallenge.helpers.JsonHelper;
import com.example.scoutchallenge.interfaces.ArrayCallBack;
import com.example.scoutchallenge.interfaces.CallBack;
import com.example.scoutchallenge.network.ApiClient;
import com.example.scoutchallenge.utils.LocalStorage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class ActivityManager {

    public static String QURAAN_CATEGORY = "مجلس القرآن الكريم";
    public static String SCOUT_CATEGORY = "اللقاء الكشفي";
    public static String WEEKLY_COURSE_CATEGORY = "الحلقة الأسبوعية";
    public static String OTHER_CATEGORY = "أخرى";

    public JSONArray mCategoriesArray;
    public HashMap<String, JSONArray> mMapActivitiesByCategory;

    public void getSquadCategories(ArrayCallBack callBack) {

        JSONObject body = new JSONObject();

        ApiClient.getInstance().perFormeRequest("D.GET_SQUAD_CATEGORIES "+ "/" + LocalStorage.getString(LocalStorage.SQUAD), body, new CallBack() {
            @Override
            public void onResult(String response) {
                if (response != null) {
                    JSONObject resp = JsonHelper.parse(response);
                    if (resp != null) {
                        // inject static  icon targrt name
                        JSONArray categoryArray = resp.optJSONArray("categories");
                        if (categoryArray != null) {
                            for (int i = 0; i < categoryArray.length(); i++) {
                                JSONObject currentObj = categoryArray.optJSONObject(i);
                                if (currentObj != null) {
                                    String iconLink = "";
                                    String title = currentObj.optString("title");
                                    if (title.equalsIgnoreCase(QURAAN_CATEGORY)) {
                                        iconLink = "koran";
                                    }
                                    if (title.equalsIgnoreCase(SCOUT_CATEGORY)) {
                                        iconLink = "scouut_category_icon";
                                    }
                                    if (title.equalsIgnoreCase(WEEKLY_COURSE_CATEGORY)) {
                                        iconLink = "class_icon";
                                    }
                                    if (title.equalsIgnoreCase(OTHER_CATEGORY)) {
                                        iconLink = "other";
                                    }
                                    JsonHelper.put(currentObj, "iconLink", iconLink);
                                }
                            }
                        }
                        callBack.onResult(categoryArray);
                        mCategoriesArray = categoryArray;
                    }
                } else {
                    callBack.onResult(null);
                }

            }
        });
    }

    public void getActivitiesOfCategory(String categoryId, CallBack callBack) {

        JSONObject body = new JSONObject();

        ApiClient.getInstance().perFormeRequest("D.GET_SQUAD_ACTIVITIES" + "/" + categoryId, body, new CallBack() {
            @Override
            public void onResult(String response) {
                if (response != null) {
                    callBack.onResult(response);
                } else {
                    callBack.onResult(null);
                }

            }
        });
    }

    public void addActivities(String title, String categoryId, CallBack callBack) {

        JSONObject body = new JSONObject();
        JsonHelper.put(body, "title", title);
        JsonHelper.put(body, "categoryId", categoryId);

        ApiClient.getInstance().perFormeRequest("D.ADD_ACTIVITIES", body, new CallBack() {
            @Override
            public void onResult(String response) {
                if (response != null) {
                    callBack.onResult(response);
                } else {
                    callBack.onResult(null);
                }

            }
        });
    }

    public void AddUserToActivity(String activityId, String userId, CallBack callBack) {

        JSONObject body = new JSONObject();
        JsonHelper.put(body, "userId", userId);
        JsonHelper.put(body, "note", "");
        JsonHelper.put(body, "rate", "");
        JsonHelper.put(body, "next", "");


        ApiClient.getInstance().perFormeRequest("D.ADD_USER_TO_ACTIVITY "+ "/" + activityId, body, new CallBack() {
            @Override
            public void onResult(String response) {
                if (response != null) {
                    callBack.onResult(response);
                } else {
                    callBack.onResult(null);
                }

            }
        });
    }

    public void detletActivity(String activityId, CallBack callBack) {
        ApiClient.getInstance().perFormeRequest("D.DELETE_ACTIVITIES" + "/" + activityId, null, new CallBack() {
            @Override
            public void onResult(String response) {
                if (response != null) {
                    callBack.onResult(response);
                } else {
                    callBack.onResult(null);
                }

            }
        });
    }

    public void deleteUserFromActivity(String userId, String activityId, CallBack callBack) {
        JSONObject body = new JSONObject();
        JsonHelper.put(body, "userId", userId);
        ApiClient.getInstance().perFormeRequest("D.DELET_USER_ACTIVITIES" + "/" + activityId, body, new CallBack() {
            @Override
            public void onResult(String response) {
                if (response != null) {
                    callBack.onResult(response);
                } else {
                    callBack.onResult(null);
                }

            }
        });
    }

    public void updateNoteActivities(String userId, String activityId, String note, String rate, String next,
                                     CallBack callBack) {

        JSONObject body = new JSONObject();
        JsonHelper.put(body, "userId", userId);
        JsonHelper.put(body, "note", note);
        JsonHelper.put(body, "rate", rate);
        JsonHelper.put(body, "next", next);

        ApiClient.getInstance().perFormeRequest("D.UPDATE_NOTE_ACTIVITIES "+ "/" + activityId, body, new CallBack() {
            @Override
            public void onResult(String response) {
                if (response != null) {
                    callBack.onResult(response);
                } else {
                    callBack.onResult(null);
                }

            }
        });
    }

    public void saveActivitiesOfCategory(String categoryId, JSONArray result) {
        if (mMapActivitiesByCategory == null) {
            mMapActivitiesByCategory = new HashMap<>();
        }
        if (categoryId != null) {
            mMapActivitiesByCategory.put(categoryId, result);
        }
    }

    public JSONArray getActivitiesOfCategory(String categoryId) {
        if (mMapActivitiesByCategory != null) {
            if (categoryId != null) {
                return mMapActivitiesByCategory.get(categoryId);
            }
        }
        return null;
    }

    public void removeActivityOfCategory(String categoryId) {
        if (mMapActivitiesByCategory != null) {
            mMapActivitiesByCategory.put(categoryId, null);
        }
    }
}
