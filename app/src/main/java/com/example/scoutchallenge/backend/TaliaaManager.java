package com.example.scoutchallenge.backend;

import com.example.scoutchallenge.App;
import com.example.scoutchallenge.helpers.D;
import com.example.scoutchallenge.helpers.JsonHelper;
import com.example.scoutchallenge.interfaces.ArrayCallBack;
import com.example.scoutchallenge.interfaces.CallBack;
import com.example.scoutchallenge.models.TaliaaModel;
import com.example.scoutchallenge.models.UserModule;
import com.example.scoutchallenge.network.ApiClient;
import com.example.scoutchallenge.utils.LocalStorage;
import com.example.scoutchallenge.utils.NotificationCenter;

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
                if (response != null) {
                    JSONObject object = JsonHelper.parse(response);
                    if (object != null) {
                        JSONArray squad = object.optJSONArray("taliaa");
                        if (squad != null) {
                            mTaliaaList = squad;

                            if (callBack != null) {
                                callBack.onResult(mTaliaaList);
                            }
                            return;
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

    public void   deleteTaliaaLocaly(JSONObject taliaa) {
        int targetPosition = -1;
        JSONObject currentTaliaaObj = null;
        if (mTaliaaList != null) {
            for (int i = 0; i < mTaliaaList.length(); i++) {
                currentTaliaaObj = mTaliaaList.optJSONObject(i);
                if (currentTaliaaObj != null) {
                    String id = currentTaliaaObj.optString("_id");
                    if (id.equalsIgnoreCase(taliaa.optString("_id"))) {
                        targetPosition = i;
                        break;
                    }
                }
            }
            if (targetPosition != -1) {
                if (currentTaliaaObj != null) {
                    JSONArray userList = currentTaliaaObj.optJSONArray("users");
                    mTaliaaList.remove(targetPosition);
                    moveUserToOtherTaliaaLocaly(userList);
                }
            }
        }
    }



    public void addUserToTaliaa(String userId, String taliaaId, CallBack callBack) {

        JSONObject body = new JSONObject();
        JsonHelper.put(body, "userId", userId);
        ApiClient.getInstance().perFormeRequest(D.ADD_USER_TALIAA + "/" + taliaaId, body, new CallBack() {
            @Override
            public void onResult(String response) {
                if (callBack != null) {
                    if (response != null) {
                        callBack.onResult(response);
                    } else {
                        callBack.onResult(null);
                    }
                }

            }
        });
    }


    public JSONObject getTaliaaById(String id) {
        if (mTaliaaList != null) {
            for (int i = 0; i < mTaliaaList.length(); i++) {
                JSONObject currentTaliaaObj = mTaliaaList.optJSONObject(i);
                if (currentTaliaaObj != null && currentTaliaaObj.optString("_id").equalsIgnoreCase(id)) {
                    return currentTaliaaObj;

                }
            }
            return null;
        }

        return new JSONObject();
    }

    public void moveUserToOtherTaliaaLocaly(JSONArray users) {
        if (users == null) {
            return;
        }
        JSONObject otherTaliaa = null;
        TaliaaModel otherTaliaaModel = new TaliaaModel();
        for (int i = 0; i < mTaliaaList.length(); i++) {
            JSONObject currentTaliaaObj = mTaliaaList.optJSONObject(i);
            if (currentTaliaaObj != null) {
                TaliaaModel taliaaModel = new TaliaaModel();
                taliaaModel.mData = currentTaliaaObj;
                if (taliaaModel.getName().equalsIgnoreCase(TaliaaModel.OTHER)) {
                    otherTaliaa = currentTaliaaObj;
                    otherTaliaaModel.mData = otherTaliaa;
                }
            }
        }
        if (otherTaliaa != null) {
            for (int i = 0; i < users.length(); i++) {
                JSONObject currentUSer = users.optJSONObject(i);
                if (currentUSer != null) {
                    UserModule userModule = new UserModule();
                    userModule.setData(currentUSer);
                    userModule.setTaliaaId(otherTaliaaModel.get_id());
                    userModule.setTaliaaName(otherTaliaaModel.getName());
                }
            }
            App.getSharedInstance().getMainActivity().injectTaliaaUSerDataLocaly(null);
            NotificationCenter.getInstance().fireNotificaion(NotificationCenter.USERS_LIST_UPDATED);
        }
    }
}
