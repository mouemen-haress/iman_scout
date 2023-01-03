package com.example.scoutchallenge.interfaces;

import androidx.annotation.NonNull;

public interface OnPermissinResult {
    public void onPermissionsResult(int requestCode, String[] permissions, int[] grantResults);
}
