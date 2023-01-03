package com.example.scoutchallenge.helpers;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.scoutchallenge.App;
import com.example.scoutchallenge.R;
import com.example.scoutchallenge.conponents.HeadComponents;
import com.example.scoutchallenge.interfaces.DidOnTap;
import com.example.scoutchallenge.interfaces.DidPermissionGrainted;
import com.example.scoutchallenge.interfaces.OnPermissinResult;

public class PermissionsManager {
    private static PermissionsManager mSharedInstance;
    private static Activity ctx;
    public static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 0;
    public static final int MY_PERMISSIONS_READ_EXTERNAL_STORAGE = 1;
    public static final int MY_PERMISSIONS_CAMERA_CODE = 2;
    public static final int MY_PERMISSIONS_STORAGE_CODE = 3;

    public DidPermissionGrainted mCameraDelegate;
    public DidPermissionGrainted mStorageDelegate;

    public static PermissionsManager getInstance() {
        if (mSharedInstance == null) {
            mSharedInstance = new PermissionsManager();
            ctx = App.getSharedInstance().mMyActivity;
        }
        return mSharedInstance;
    }

    public void requestStoragePermesssion(DidPermissionGrainted delegate) {
        mStorageDelegate = delegate;

        if (ContextCompat.checkSelfPermission(App.getSharedInstance().mMyActivity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(App.getSharedInstance().mMyActivity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    ActivityCompat.requestPermissions(App.getSharedInstance().mMyActivity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_STORAGE_CODE);
                } else {
                    Tools.showAskingPopup(ctx.getString(R.string.you_should_admit_the_storage), new DidOnTap() {
                        @Override
                        public void onTap(HeadComponents view) {

                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", App.getSharedInstance().getMainActivity().getPackageName(), null);
                            intent.setData(uri);
                            App.getSharedInstance().getMainActivity().startActivity(intent);
                        }
                    }, new DidOnTap() {
                        @Override
                        public void onTap(HeadComponents view) {

                        }
                    });

                }

                mStorageDelegate.onPermissionResult(false);

            }
        } else {
            mStorageDelegate.onPermissionResult(true);
        }

    }

    public void requestCameraDelegate(DidPermissionGrainted delegate) {
        mCameraDelegate = delegate;

        if (ContextCompat.checkSelfPermission(App.getSharedInstance().mMyActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(App.getSharedInstance().mMyActivity, Manifest.permission.CAMERA)) {
                    ActivityCompat.requestPermissions(App.getSharedInstance().mMyActivity, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_CAMERA_CODE);
                } else {
                    Tools.showAskingPopup(ctx.getString(R.string.you_should_admit_the_camera_permission), new DidOnTap() {
                        @Override
                        public void onTap(HeadComponents view) {

                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", App.getSharedInstance().getMainActivity().getPackageName(), null);
                            intent.setData(uri);
                            App.getSharedInstance().getMainActivity().startActivity(intent);
                        }
                    }, new DidOnTap() {
                        @Override
                        public void onTap(HeadComponents view) {

                        }
                    });

                }

                mCameraDelegate.onPermissionResult(false);

            }
        } else {
            mCameraDelegate.onPermissionResult(true);
        }

    }

    public void handlePermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_CAMERA_CODE:
                if (mCameraDelegate == null) {
                    return;
                }
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mCameraDelegate.onPermissionResult(true);
                    return;
                }

                mCameraDelegate.onPermissionResult(false);
                break;

            case MY_PERMISSIONS_STORAGE_CODE:
                if (mStorageDelegate == null) {
                    return;
                }
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mStorageDelegate.onPermissionResult(true);
                    return;
                }

                mStorageDelegate.onPermissionResult(false);
                break;
        }
    }


}
