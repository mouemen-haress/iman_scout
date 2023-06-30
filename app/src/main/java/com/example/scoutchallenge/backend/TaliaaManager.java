package com.example.scoutchallenge.backend;

import com.example.scoutchallenge.App;
import com.example.scoutchallenge.Core;
import com.example.scoutchallenge.helpers.D;
import com.example.scoutchallenge.helpers.JsonHelper;
import com.example.scoutchallenge.helpers.Tools;
import com.example.scoutchallenge.interfaces.ArrayCallBack;
import com.example.scoutchallenge.interfaces.CallBack;
import com.example.scoutchallenge.models.MemberModule;
import com.example.scoutchallenge.models.TaliaaModel;
import com.example.scoutchallenge.network.ApiClient;
import com.example.scoutchallenge.utils.LocalStorage;
import com.example.scoutchallenge.utils.NotificationCenter;

import org.json.JSONArray;
import org.json.JSONObject;

public class TaliaaManager extends MainManager {
    public JSONArray mTaliaaList;

    public void getAllTaliaaOfFerka(ArrayCallBack callBack) {
        ApiClient.getInstance().perFormeRequest(D.GET_TALIAA + "/" + Core.getInstance().getFerkaId(), null, new CallBack() {
            @Override
            public void onResult(String response) {
                if (response != null) {
                    JSONObject parsedObj = Tools.getParsedResp(response);
                    if (parsedObj != null) {
                        if (Tools.isSuccess(parsedObj)) {
                            JSONArray data = parsedObj.optJSONArray("data");
                            mTaliaaList = data;
                            doCallBack(callBack, data);
                            return;
                        }
                    }
                }
                doCallBack(callBack, null);
            }
        });

    }

    public String getTaliaaNameById(String id) {
        if (mTaliaaList == null) {
            return "";
        }

        for (int i = 0; i < mTaliaaList.length(); i++) {
            JSONObject currentObj = mTaliaaList.optJSONObject(i);
            if (currentObj != null) {
                if (currentObj.optString("idTaliaa").equalsIgnoreCase(id)) {
                    return currentObj.optString("name");
                }
            }
        }

        return "";
    }

}
