package com.example.scoutchallenge.backend;

import android.net.Uri;

import com.example.scoutchallenge.helpers.D;
import com.example.scoutchallenge.helpers.JsonHelper;
import com.example.scoutchallenge.helpers.StringHelper;
import com.example.scoutchallenge.interfaces.ArrayCallBack;
import com.example.scoutchallenge.interfaces.CallBack;
import com.example.scoutchallenge.modules.UserModule;
import com.example.scoutchallenge.network.ApiClient;
import com.example.scoutchallenge.utils.LocalStorage;
import com.google.android.gms.common.api.Api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class UserManager {
    public JSONArray mAllUserList;
    private HashMap<String, JSONObject> mUserMap;


    public UserManager() {
    }


    public void addUser(UserModule model, CallBack callBack) {

        JSONObject body = new JSONObject();
        body = generateFakeUser(model.getmName(), true, model);

        ApiClient.getInstance().perFormeMultiTypeRequest(model.getmImageUri(), D.ADD_USER, body, new CallBack() {
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

    public void addUserToTaliaa(String userId, CallBack callBack) {

        JSONObject body = new JSONObject();
        JsonHelper.put(body, "userId", userId);
        ApiClient.getInstance().perFormeRequest(D.ADD_USER_TALIAA + "/" + D.mTaliaaObject.optString("_id"), body, new CallBack() {
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

    private JSONObject generateFakeUser(String phone, boolean makeTrue, UserModule model) {
        JSONObject body = new JSONObject();
        JsonHelper.put(body, "Name", phone);
        makeTrue = !D.IS_STIMILATE_ADD_USER_ENABLED;
        Random rand = new Random();

        JsonHelper.put(body, "Email", rand.nextInt(50));
        JsonHelper.put(body, "Date", phone);
        JsonHelper.put(body, "BloodType", phone);
        JsonHelper.put(body, "Number", phone);
        JsonHelper.put(body, "Password", phone);
        JsonHelper.put(body, "Position", "onsor");
        JsonHelper.put(body, "isAdmin", "false");
        JsonHelper.put(body, "FatherName", phone);
        JsonHelper.put(body, "FatherBloodType", phone);
        JsonHelper.put(body, "FatherNumber", phone);
        JsonHelper.put(body, "FatherWork", phone);
        JsonHelper.put(body, "MotherName", phone);
        JsonHelper.put(body, "MotherDate", phone);
        JsonHelper.put(body, "MotherBloodType", phone);
        JsonHelper.put(body, "MotherNumber", phone);
        JsonHelper.put(body, "MotherWork", phone);
        JsonHelper.put(body, "PlaceOfBirth", phone);
        JsonHelper.put(body, "Address", phone);
        JsonHelper.put(body, "NbOfFamily", phone);
        JsonHelper.put(body, "AddressType", phone);
        JsonHelper.put(body, "CurrentEducation", phone);
        JsonHelper.put(body, "Hobbies", phone);
        JsonHelper.put(body, "Insurance", phone);
        JsonHelper.put(body, "taliaa", phone);
        JsonHelper.put(body, "SerialNumber", model.getmSerialNumber());
        JsonHelper.put(body, "Illness", phone);
        JsonHelper.put(body, "squad", LocalStorage.getString(LocalStorage.SQUAD));
        JsonHelper.put(body, "moufawadiyeh", LocalStorage.MOUFAWADIYEH);
        JsonHelper.put(body, "fawj", LocalStorage.FAWJ);

        if (makeTrue) {
            body = new JSONObject();
            JsonHelper.put(body, "Name", model.getmName());
            JsonHelper.put(body, "Email", model.getmEmail());
            JsonHelper.put(body, "Date", model.getmDateOfBirth());
            JsonHelper.put(body, "BloodType", model.getmPersonalBloodType());
            JsonHelper.put(body, "Number", model.getmRegisterNumber());
            JsonHelper.put(body, "Password", model.getmPassword());
            JsonHelper.put(body, "Position", "onsor");
            JsonHelper.put(body, "Cloth", model.ismHasClothes());
            JsonHelper.put(body, "taliaa", model.getTaliaaId());
            JsonHelper.put(body, "SerialNumber", model.getmSerialNumber());
            JsonHelper.put(body, "isAdmin", "false");

            JsonHelper.put(body, "FatherName", model.getmFhaterPhone());
            JsonHelper.put(body, "FatherBloodType", model.getMfhaterBloodType());
            JsonHelper.put(body, "FatherNumber", model.getmFhaterPhone());
            JsonHelper.put(body, "FatherWork", model.getmFatherWork());
            JsonHelper.put(body, "MotherName", model.getmMotherName());
            JsonHelper.put(body, "MotherBloodType", model.getmMotherBloodType());
            JsonHelper.put(body, "MotherNumber", model.getmMotherPhone());
            JsonHelper.put(body, "MotherWork", model.getmMotherWork());
            JsonHelper.put(body, "PlaceOfBirth", model.getmPlaceOfBirth());
            JsonHelper.put(body, "Address", model.getmAddress());
            JsonHelper.put(body, "NbOfFamily", model.getFamilyNb());
            JsonHelper.put(body, "AddressType", model.getmAddressType());
            JsonHelper.put(body, "CurrentEducation", model.getmSchool());
            JsonHelper.put(body, "Hobbies", model.getmHobby());
            JsonHelper.put(body, "Insurance", model.getIsHasOtherAssociation());
            JsonHelper.put(body, "Illness", model.ismIsHasChronicDisease());

            JsonHelper.put(body, "squad", LocalStorage.getString(LocalStorage.SQUAD));
            JsonHelper.put(body, "moufawadiyeh", LocalStorage.getString(LocalStorage.MOUFAWADIYEH));
            JsonHelper.put(body, "fawj", LocalStorage.getString(LocalStorage.FAWJ));

        }

        return body;
    }

    public void getAllUser(ArrayCallBack callBack) {

        ApiClient.getInstance().perFormeRequest(D.GET_USER_SQUAD + "/" + LocalStorage.getString(LocalStorage.SQUAD), null, new CallBack() {
            @Override
            public void onResult(String response) {
                if (callBack != null) {
                    if (response != null) {
                        JSONObject object = JsonHelper.parse(response);
                        JSONArray userArray = object.optJSONArray("user");
                        mAllUserList = userArray;
                        callBack.onResult(mAllUserList);
                        createUserMap();
                    } else {
                        callBack.onResult(null);
                    }
                }
            }
        });
    }

    private void createUserMap() {
        if (mUserMap == null) {
            mUserMap = new HashMap<>();
        }
        if (mAllUserList != null) {
            for (int i = 0; i < mAllUserList.length(); i++) {
                JSONObject currrentUSer = mAllUserList.optJSONObject(i);
                if (currrentUSer != null) {
                    mUserMap.put(currrentUSer.optString("_id"), currrentUSer);
                }
            }
        }
    }

    public void checkUserEmail(String email, CallBack callBack) {
        JSONObject body = new JSONObject();
        JsonHelper.put(body, "email", email);
        ApiClient.getInstance().perFormeRequest(D.CHECK_EMAIL, body, new CallBack() {
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


    public void updateUser(UserModule model, String userId, CallBack callBack) {
        JSONObject body = new JSONObject();
        body = generateFakeUser("dsadsa", true, model);
        ApiClient.getInstance().perFormeRequest(D.UPDATE_USER + "/" + "6361757a8a2292d2b3e7f142", body, new CallBack() {
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


    public JSONArray getRelatedNameUser(String text, JSONArray array) {

        JSONArray resultObject = new JSONArray();
        JSONArray inputArray = new JSONArray();
        inputArray = mAllUserList;
        if (array != null) {
            inputArray = array;
        }

        if (inputArray != null) {
            for (int i = 0; i < inputArray.length(); i++) {
                JSONObject currentObj = inputArray.optJSONObject(i);
                if (currentObj != null) {
                    String currentName = currentObj.optString("Name");
                    if (!StringHelper.isNullOrEmpty(currentName)) {
                        if (currentName.contains(text)) {
                            JsonHelper.put(resultObject, currentObj);
                        }
                    }
                }
            }
        }
        return resultObject;

    }

    public JSONArray getRelatedNameUser(String text) {
        return getRelatedNameUser(text, null);

    }

    public void deleteUserFromTaliaa(String userId, CallBack callBack) {
        ApiClient.getInstance().perFormeRequest(D.DELETE_USER_TALIAA + "/" + userId, null, new CallBack() {
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

    public JSONObject getUserById(String id) {
        if (mUserMap == null) {
            return null;
        }
        return mUserMap.get(id);
    }

    public JSONArray getOtherUser(JSONArray cuurentUserList) {
        if (cuurentUserList == null || mUserMap == null) {
            return null;
        }

        ArrayList<String> cuurentUSerListKeys = new ArrayList<>();
        JSONArray resultArray = new JSONArray();
        Set<String> mapKeys = mUserMap.keySet();

        for (int i = 0; i < cuurentUserList.length(); i++) {
            JSONObject user = cuurentUserList.optJSONObject(i);
            if (user != null) {
                JSONObject userData = user.optJSONObject("userId");
                if (userData != null) {
                    String userId = userData.optString("_id");
                    cuurentUSerListKeys.add(userId);
                }

            }
        }

        for (String key : mapKeys) {
            if (!cuurentUSerListKeys.contains(key)) {
                resultArray.put(mUserMap.get(key));
            }
        }

        return resultArray;
    }
}
