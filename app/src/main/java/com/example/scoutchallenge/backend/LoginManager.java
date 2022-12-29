package com.example.scoutchallenge.backend;

import com.example.scoutchallenge.Core;
import com.example.scoutchallenge.helpers.D;
import com.example.scoutchallenge.helpers.JsonHelper;
import com.example.scoutchallenge.helpers.StringHelper;
import com.example.scoutchallenge.interfaces.CallBack;
import com.example.scoutchallenge.network.ApiClient;
import com.example.scoutchallenge.utils.LocalStorage;

import org.json.JSONObject;

public class LoginManager {


    public static final String SUCCESS_LOGIN = "تم تسجيل الدخول بنجاح";
    public static final String EMAIL_NOT_REGISTRED = "This Email Is not regestered!";
    public static final String WRONG_PASSWORD = "Wrong Password!";

    public void authenticate(String identifier, String password, CallBack callBack) {

        JSONObject body = new JSONObject();
        JsonHelper.put(body, "Email", identifier);
        JsonHelper.put(body, "Password", password);


        ApiClient.getInstance().perFormeRequest(D.LOGIN_ROOT, body, new CallBack() {
            @Override
            public void onResult(String response) {
                if (callBack != null) {
                    if (response != null) {
                        JSONObject resp = JsonHelper.parse(response);
                        if (resp != null) {
                            String message = resp.optString("message");
                            if (!StringHelper.isNullOrEmpty(message)) {
                                if (message.equalsIgnoreCase(SUCCESS_LOGIN)) {
                                    JSONObject data = resp.optJSONObject("data");
                                    if (data != null) {
                                        Core.getInstance().setmCurrentUserPosition(data.optString("Position"));
                                        Core.getInstance().setCurentMemberObject(data);

                                        LocalStorage.setString(LocalStorage.SELF_ID, data.optString("id"));
                                        LocalStorage.setString(LocalStorage.SQUAD, data.optString("squad"));
                                        LocalStorage.setString(LocalStorage.FAWJ, data.optString("fawj"));
                                        LocalStorage.setString(LocalStorage.MOUFAWADIYEH, data.optString("moufawadiyeh"));

                                    }
                                    callBack.onResult(SUCCESS_LOGIN);
                                    return;
                                }
                            }
                        }

                        // Failure case
                        String success = resp.optString("Success");
                        if (!StringHelper.isNullOrEmpty(success)) {
                            callBack.onResult(success);
                            return;
                        }
                    } else {
                        callBack.onResult(null);
                        return;
                    }
                    callBack.onResult(null);

                }

            }
        });
    }
}
