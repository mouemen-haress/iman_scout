package com.example.scoutchallenge.backend;

import com.example.scoutchallenge.Core;
import com.example.scoutchallenge.helpers.D;
import com.example.scoutchallenge.helpers.JsonHelper;
import com.example.scoutchallenge.helpers.Tools;
import com.example.scoutchallenge.interfaces.ArrayCallBack;
import com.example.scoutchallenge.interfaces.CallBack;
import com.example.scoutchallenge.models.MemberModule;
import com.example.scoutchallenge.network.ApiClient;
import com.example.scoutchallenge.utils.LocalStorage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Random;

public class UserManager extends MainManager {
    public JSONArray mAllUserList;
    public HashMap<String, JSONArray> mUserMap;
    public JSONArray fullUserTaliaaArray;

    public UserManager() {
    }


    public void addUser(MemberModule model, CallBack callBack) {

        JSONObject body = new JSONObject();
        body = parseUserModel(model.getmName(), false, model);

        ApiClient.getInstance().perFormeMultiTypeRequest(model.getmImageUri(), "", body, new CallBack() {
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

    private JSONObject parseUserModel(String fakeText, boolean isFake, MemberModule model) {
        JSONObject body = new JSONObject();
        JsonHelper.put(body, "Name", fakeText);
        isFake = D.IS_STIMILATE_ADD_USER_ENABLED;
        Random rand = new Random();
        fakeText = "test";
        JsonHelper.put(body, "Email", rand.nextInt(50));
        JsonHelper.put(body, "Name", rand.nextInt(50));

        JsonHelper.put(body, "Date", fakeText);
        JsonHelper.put(body, "BloodType", fakeText);
        JsonHelper.put(body, "Number", fakeText);
        JsonHelper.put(body, "Password", fakeText);
        JsonHelper.put(body, "Position", "onsor");
        JsonHelper.put(body, "isAdmin", "false");
        JsonHelper.put(body, "FatherName", fakeText);
        JsonHelper.put(body, "FatherBloodType", fakeText);
        JsonHelper.put(body, "FatherNumber", fakeText);
        JsonHelper.put(body, "FatherWork", fakeText);
        JsonHelper.put(body, "MotherName", fakeText);
        JsonHelper.put(body, "MotherDate", fakeText);
        JsonHelper.put(body, "MotherBloodType", fakeText);
        JsonHelper.put(body, "MotherNumber", fakeText);
        JsonHelper.put(body, "MotherWork", fakeText);
        JsonHelper.put(body, "PlaceOfBirth", fakeText);
        JsonHelper.put(body, "Address", fakeText);
        JsonHelper.put(body, "NbOfFamily", fakeText);
        JsonHelper.put(body, "AddressType", fakeText);
        JsonHelper.put(body, "CurrentEducation", fakeText);
        JsonHelper.put(body, "Hobbies", fakeText);
        JsonHelper.put(body, "Insurance", fakeText);
        JsonHelper.put(body, "taliaa", fakeText);
        JsonHelper.put(body, "SerialNumber", model.getmSerialNumber());
        JsonHelper.put(body, "Illness", fakeText);
        JsonHelper.put(body, "squad", LocalStorage.getString(LocalStorage.SQUAD));
        JsonHelper.put(body, "moufawadiyeh", LocalStorage.getString(LocalStorage.MOUFAWADIYEH));
        JsonHelper.put(body, "fawj", LocalStorage.getString(LocalStorage.FAWJ));

        if (!isFake) {
            body = new JSONObject();
            JsonHelper.put(body, "Name", model.getmName());
            JsonHelper.put(body, "Date", model.getmDateOfBirth());
            JsonHelper.put(body, "BloodType", model.getmPersonalBloodType());
            JsonHelper.put(body, "Number", model.getmOwnNuymber());
            JsonHelper.put(body, "Password", model.getmPassword());
            JsonHelper.put(body, "Position", "onsor");
            JsonHelper.put(body, "Cloth", model.ismHasClothes());
            JsonHelper.put(body, "taliaa", model.getTaliaaId());
            JsonHelper.put(body, "SerialNumber", model.getmSerialNumber());
            JsonHelper.put(body, "isAdmin", "false");

            JsonHelper.put(body, "FatherName", model.getmFhaterPhone());
            JsonHelper.put(body, "FatherNumber", model.getmFhaterPhone());
            JsonHelper.put(body, "FatherWork", model.getmFatherWork());
            JsonHelper.put(body, "MotherName", model.getmMotherName());
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

        ApiClient.getInstance().perFormeRequest(D.GET_MY_AMASER + "/" + Core.getInstance().getFerkaId(), null, new CallBack() {
            @Override
            public void onResult(String response) {
                if (response != null) {
                    JSONObject parsedData = Tools.getParsedResp(response);
                    if (parsedData != null) {
                        if (Tools.isSuccess(parsedData)) {
                            JSONArray data = parsedData.optJSONArray("data");
                            if (data != null) {
                                mAllUserList = data;
                                createUserMap();
                                doCallBack(callBack, data);
                                return;
                            }

                        } else {
                            doCallBack(callBack, null);
                        }
                    }
                }

                doCallBack(callBack, null);
            }
        });
    }

    private void createUserMap() {
        if (mUserMap == null) {
            mUserMap = new HashMap<>();
        }




        if (mAllUserList != null) {
            for (int i = 0; i < mAllUserList.length(); i++) {
                JSONObject currentOnj = mAllUserList.optJSONObject(i);
                if (currentOnj != null) {
                    JSONObject currentTaliaa = currentOnj.optJSONObject("taliaa");
                    if (currentTaliaa != null) {
                        String id = currentTaliaa.optString("idTaliaa");
                        id = BackendProxy.getInstance().mTaliaaManager.getTaliaaNameById(id);
                        JSONArray currentOnsorsArray = mUserMap.get(id);
                        if (currentOnsorsArray == null) {
                            JSONArray array = new JSONArray();
                            array.put(currentOnj);
                            mUserMap.put(id, array);
                        } else {
                            currentOnsorsArray.put(currentOnj);
                        }
                    }
                }
            }

            if (fullUserTaliaaArray == null) {
                fullUserTaliaaArray = new JSONArray();
            }
            for (String key : mUserMap.keySet()) {
                JSONArray value = mUserMap.get(key);
                JSONObject object = new JSONObject();
                if (value != null) {
                    JsonHelper.put(object, "name", key);
                    JsonHelper.put(object, "users", value);
                    fullUserTaliaaArray.put(object);
                }

            }

        }
    }


    public void updateUser(MemberModule model, CallBack callBack) {
        JSONObject body = new JSONObject();
        body = parseUserModel(model.getmName(), false, model);

        ApiClient.getInstance().perFormeRequest("" + "/" + model.getId(), body, new CallBack() {
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


}
