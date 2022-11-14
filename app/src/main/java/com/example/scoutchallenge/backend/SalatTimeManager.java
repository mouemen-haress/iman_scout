package com.example.scoutchallenge.backend;

import com.example.scoutchallenge.helpers.D;
import com.example.scoutchallenge.interfaces.CallBack;
import com.example.scoutchallenge.network.ApiClient;

public class SalatTimeManager {


    public void getSlatTime(CallBack callBack) {

        ApiClient.getInstance().perFormeRequest(D.SALAT_ROOT, null, new CallBack() {
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
