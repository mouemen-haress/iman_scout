package com.example.scoutchallenge.backend;

import com.example.scoutchallenge.App;
import com.example.scoutchallenge.helpers.D;
import com.example.scoutchallenge.helpers.JsonHelper;
import com.example.scoutchallenge.helpers.StringHelper;
import com.example.scoutchallenge.interfaces.ArrayCallBack;
import com.example.scoutchallenge.interfaces.CallBack;
import com.example.scoutchallenge.models.MemberModule;
import com.example.scoutchallenge.network.ApiClient;
import com.example.scoutchallenge.utils.LocalStorage;

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


    public void addUser(MemberModule model, CallBack callBack) {

        JSONObject body = new JSONObject();
        body = parseUserModel(model.getmName(), false, model);

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

    public void addUserLocaly(MemberModule userModel, CallBack callBack) {
        JSONObject userObject = parseUserModel("", false, userModel);
        if (userObject != null) {
            mAllUserList.put(userObject);
            mUserMap.put(userModel.getId(), userObject);
        }
        App.getSharedInstance().getMainActivity().injectTaliaaUSerDataLocaly(null);
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
                if (response != null) {
                    JSONObject object = JsonHelper.parse(response);
                    JSONArray userArray = object.optJSONArray("user");
                    mAllUserList = userArray;
                    createUserMap();

                    if (callBack != null) {
                        callBack.onResult(mAllUserList);
                    }

                } else {
                    if (callBack != null) {
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
        JsonHelper.put(body, "Email", email);
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


    public void updateUser(MemberModule model, CallBack callBack) {
        JSONObject body = new JSONObject();
        body = parseUserModel(model.getmName(), false, model);

        ApiClient.getInstance().perFormeRequest(D.UPDATE_USER + "/" + model.getId(), body, new CallBack() {
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

    public void getLast30Activities(String categoryId, String userId, CallBack callBack) {
        JSONObject body = new JSONObject();
        JsonHelper.put(body, "userId", userId);
        ApiClient.getInstance().perFormeRequest(D.GET_ONSOR_ACTIVITIES + "/" + categoryId, body, new CallBack() {
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

    public MemberModule getUserBySerialNumber(String searchedSerial) {
        if (StringHelper.isNullOrEmpty(searchedSerial)) {
            return null;
        }
        if (mAllUserList != null) {
            for (int i = 0; i < mAllUserList.length(); i++) {
                JSONObject currentUser = mAllUserList.optJSONObject(i);
                if (currentUser != null) {
                    MemberModule userModule = new MemberModule();
                    userModule.setData(currentUser);
                    if (searchedSerial.equalsIgnoreCase(userModule.getmSerialNumber())) {
                        return userModule;
                    }
                }
            }
        }
        return null;

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
                if (userData == null) {
                    userData = user;
                }

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
