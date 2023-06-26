package com.example.scoutchallenge.backend;

import com.example.scoutchallenge.Core;
import com.example.scoutchallenge.helpers.D;
import com.example.scoutchallenge.helpers.JsonHelper;
import com.example.scoutchallenge.helpers.StringHelper;
import com.example.scoutchallenge.interfaces.CallBack;
import com.example.scoutchallenge.network.ApiClient;

import org.json.JSONObject;

public class LoginManager extends MainManager {


    public static final String SUCCESS_LOGIN = "SUCCESS_LOGIN";

    public void authenticate(String identifier, String password, CallBack callBack) {

        JSONObject body = new JSONObject();
        JsonHelper.put(body, "username", identifier);
        JsonHelper.put(body, "password", password);


        ApiClient.getInstance().perFormeRequest(D.LOGIN_ROOT, body, new CallBack() {
            @Override
            public void onResult(String response) {
                if (response != null) {
                    JSONObject resp = JsonHelper.parse(response);
                    if (resp != null) {
                        boolean success = resp.optBoolean("success");
                        if (success) {
                            JSONObject data = resp.optJSONObject("data");
                            if (data != null) {
                                String position = data.optString("type");
                                Core.getInstance().setmCurrentUserPosition(position);
                                Core.getInstance().setCurentMemberObject(data);
                                doCallBack(callBack, SUCCESS_LOGIN);
                                return;
                            }
                        } else {
                            String errorMessage = getErrorMessage(resp);
                            if (!StringHelper.isNullOrEmpty(errorMessage)) {
                                doCallBack(callBack, errorMessage);
                                return;
                            }
                        }

                    }
                }

                doCallBack(callBack, null);
            }

        });
    }


}
