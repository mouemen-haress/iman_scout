package com.example.scoutchallenge.helpers;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.scoutchallenge.App;
import com.example.scoutchallenge.interfaces.DidPermissionGrainted;

public class PermissionsManager {
    private static PermissionsManager mSharedInstance;
    private static Activity ctx;
    public static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 0;
    public static final int MY_PERMISSIONS_READ_EXTERNAL_STORAGE = 1;

    public static PermissionsManager getInstance() {
        if (mSharedInstance == null) {
            mSharedInstance = new PermissionsManager();
            ctx = App.getSharedInstance().mMyActivity;
        }
        return mSharedInstance;
    }

    public void requestStoragePermesssion(DidPermissionGrainted delegate) {
        boolean isGrainted = false;
        if (ContextCompat.checkSelfPermission(ctx,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(ctx,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {


            } else {
                ActivityCompat.requestPermissions(ctx,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_READ_EXTERNAL_STORAGE);


            }
        } else {
        }
    }


}
