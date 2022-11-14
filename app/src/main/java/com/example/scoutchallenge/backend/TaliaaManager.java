package com.example.scoutchallenge.backend;

import com.example.scoutchallenge.helpers.D;
import com.example.scoutchallenge.helpers.JsonHelper;
import com.example.scoutchallenge.interfaces.ArrayCallBack;
import com.example.scoutchallenge.interfaces.CallBack;
import com.example.scoutchallenge.network.ApiClient;
import com.example.scoutchallenge.utils.LocalStorage;

import org.json.JSONArray;
import org.json.JSONObject;

public class TaliaaManager {
    public JSONArray mTaliaaList;


    public void addTaliaa(String name, CallBack callBack) {
        JSONObject body = new JSONObject();
        JsonHelper.put(body, "name", name);
        JsonHelper.put(body, "squad", LocalStorage.getString(LocalStorage.SQUAD));
        JsonHelper.put(body, "moufawadiyeh", LocalStorage.getString(LocalStorage.MOUFAWADIYEH));
        JsonHelper.put(body, "fawj", LocalStorage.getString(LocalStorage.FAWJ));

        ApiClient.getInstance().perFormeRequest(D.ADD_TALIAA, body, new CallBack() {
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

    public void deleteTaliaa(String taliaaId, String defaultTaliaaId, CallBack callBack) {
        JSONObject body = new JSONObject();
        JsonHelper.put(body, "defaultTaliaaId", defaultTaliaaId);
        ApiClient.getInstance().perFormeRequest(D.DELETE_TALIAA + "/" + taliaaId, body, new CallBack() {
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

    public void addTaliaa(String name, String mfawID, String fawje, String ferka, CallBack callBack) {
        JSONObject body = new JSONObject();
        JsonHelper.put(body, "name", name);
        JsonHelper.put(body, "squad", ferka);
        JsonHelper.put(body, "moufawadiyeh", mfawID);
        JsonHelper.put(body, "fawj", fawje);

        ApiClient.getInstance().perFormeRequest(D.ADD_TALIAA, body, new CallBack() {
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

    public JSONArray getTaliaaList(ArrayCallBack callBack) {

        String squadId = LocalStorage.getString(LocalStorage.SQUAD);
        ApiClient.getInstance().perFormeRequest(D.GET_TALIAA + "/" + squadId, null, new CallBack() {
            @Override
            public void onResult(String response) {
                if (callBack != null) {
                    if (response != null) {
                        JSONObject object = JsonHelper.parse(response);
                        if (object != null) {
                            JSONArray squad = object.optJSONArray("taliaa");
                            if (squad != null) {
                                mTaliaaList = squad;
                                callBack.onResult(mTaliaaList);
                                return;
                            }
                        }
                    }
                }
                if (callBack != null) {
                    callBack.onResult(null);
                }
            }
        });


        return null;
    }

    private JSONArray getTaliaaFakeData() {
        JSONArray array = new JSONArray();
        array.put("0");
        array.put("1");
        array.put("2");
        array.put("3");
        array.put("3");
        array.put("3");
        array.put("3");
        array.put("3");
        array.put("3");
        array.put("3");
        array.put("3");

        return array;
    }

    public String getDefaultTaliaaId() {
        if (mTaliaaList != null) {
            for (int i = 0; i < mTaliaaList.length(); i++) {
                JSONObject currentTaliaaObj = mTaliaaList.optJSONObject(i);
                if (currentTaliaaObj != null) {
                    String name = currentTaliaaObj.optString("name");
                    if (name.equalsIgnoreCase("أخرى")) {
                        return currentTaliaaObj.optString("_id");
                    }
                }
            }
        }
        return null;
    }

    public void deleteTaliaaLocaly(JSONObject taliaa) {
        int targetPosition = -1;
        if (mTaliaaList != null) {
            for (int i = 0; i < mTaliaaList.length(); i++) {
                JSONObject currentTaliaaObj = mTaliaaList.optJSONObject(i);
                if (currentTaliaaObj != null) {
                    String id = currentTaliaaObj.optString("_id");
                    if (id.equalsIgnoreCase("_id")) {
                        targetPosition = i;
                    }
                }
            }
            if (targetPosition != -1) {
                mTaliaaList.remove(targetPosition);
            }
        }
    }
}
