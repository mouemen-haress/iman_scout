package com.example.scoutchallenge.views;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.scoutchallenge.R;
import com.example.scoutchallenge.backend.BackendProxy;
import com.example.scoutchallenge.helpers.PermissionsManager;
import com.example.scoutchallenge.helpers.StringHelper;
import com.example.scoutchallenge.interfaces.DidPermissionGrainted;
import com.example.scoutchallenge.models.UserModule;
import com.google.zxing.Result;

public class ScanView extends HeadView {
    private CodeScanner mCodeScanner;

    @Override
    public void init(Context ctx, View view) {
        super.init(ctx, view);

        PermissionsManager.getInstance().requestCameraDelegate(new DidPermissionGrainted() {
            @Override
            public void onPermissionResult(boolean trueOrFalse) {
                if(false){
                    popBackStack();
                }
            }
        });
        CodeScannerView scannerView = new CodeScannerView(ctx);
        mRootView.addView(scannerView);
        mCodeScanner = new CodeScanner(getContext(), scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(() -> {
                    String resultText = result.getText();
                    if (!StringHelper.isNullOrEmpty(resultText)) {
                        UserModule userModel = new UserModule();
                        userModel = BackendProxy.getInstance().mUserManager.getUserBySerialNumber(resultText);
                        if (userModel != null && userModel.getData() != null) {
                            Bundle bundle = new Bundle();
                            bundle.putString("userObj", userModel.getData().toString());
                            pushView(R.id.showUserInfoView, bundle);
                        }
                    }
                });
            }
        });

        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}
