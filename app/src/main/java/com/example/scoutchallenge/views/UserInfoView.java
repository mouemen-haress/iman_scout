package com.example.scoutchallenge.views;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.scoutchallenge.App;
import com.example.scoutchallenge.Core;
import com.example.scoutchallenge.R;
import com.example.scoutchallenge.backend.BackendProxy;
import com.example.scoutchallenge.conponents.MDrawableEditText;
import com.example.scoutchallenge.helpers.JsonHelper;
import com.example.scoutchallenge.interfaces.CallBack;
import com.example.scoutchallenge.network.MImageLoader;

import org.json.JSONArray;
import org.json.JSONObject;

public class UserInfoView extends AddUserView {
    protected JSONObject mUserObject;


    @Override
    public void init(Context ctx, View view) {
        super.init(ctx, view);

        mSubmitBtn.setOnTapListener(view1 -> {
            updateUser();
        });

        mProfile.setOnTapListener(view1 -> {
            showToast(getString(R.string.you_can_not_update_profile));
        });

        mPassword.show();
        injectUIData();

    }

    private void updateUser() {
        if (!Core.getInstance().isLeader()) {
            showSimplePopup(getString(R.string.you_dont_have_a_permission));
            return;
        }
        fillUserModel();
        showLockedLoading();
        BackendProxy.getInstance().mUserManager.updateUser(mUserModule, new CallBack() {
            @Override
            public void onResult(String response) {
                if (response != null) {
                    App.getSharedInstance().getMainActivity().injectTaliaaUSerData(new CallBack() {
                        @Override
                        public void onResult(String response) {
                            runOnUiThread(() -> {
                                hideLockedLoading();
                                showToast(getString(R.string.user_updated_success));
                                popBackStack();
                            });
                        }
                    });
                } else {
                    showSimplePopup(getString(R.string.server_error));
                }
            }
        });
    }

    @Override
    public void layoutViews() {
        super.layoutViews();


        int margin = dpToPx(16);
        int btnHeight = dpToPx(40);
        int icon = dpToPx(120);

        mCategoriesMenu.measure(0, 0);
        int categoriesHeight = mCategoriesMenu.getMeasuredHeight();

    }

    @Override
    public void FillPassedData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String dataString = bundle.getString("userObj");
            JSONObject obj = JsonHelper.parse(dataString);
            if (obj != null) {
                mUserObject = obj;
                mUserModule.setData(mUserObject);

            }
        }

    }

    private void injectUIData() {
        mName.setText(mUserModule.getmName());
        MImageLoader.loadWithGlide(mUserModule.getImageUrl(), 0, mProfile.getImage());

        mEmail.setText(mUserModule.getmEmail());
        mEmail.removeCondition(MDrawableEditText.DEFAULT);

        mDate.setHint(mUserModule.getmDateOfBirth());
        mDate.mIsNewDataSelected = true;
        mBloodType.setText(mUserModule.getmPersonalBloodType());
        mClothesSwitch.setChecked(mUserModule.ismHasClothes());
        fillTaliaaList();
        mNumber.setText(mUserModule.getmRegisterNumber());
        mPassword.setText(mUserModule.getmPassword());

        mFatherName.setText(mUserModule.getmFatherName());
        mFatherBloodType.setText(mUserModule.getMfhaterBloodType());
        mFatherNumber.setText(mUserModule.getmFhaterPhone());
        mFatherWork.setText(mUserModule.getmFatherWork());

        mMotherName.setText(mUserModule.getmMotherName());
        mMotherBloodType.setText(mUserModule.getmMotherBloodType());
        mMotherNumber.setText(mUserModule.getmMotherPhone());
        mMotherWork.setText(mUserModule.getmMotherWork());

        mPlaceOfBirth.setText(mUserModule.getmPlaceOfBirth());
        mAddress.setText(mUserModule.getmAddress());
        mNbOfFamily.setText(mUserModule.getFamilyNb());
        mAddressType.setText(mUserModule.getmAddressType());
        mCurrentEducation.setText(mUserModule.getmSchool());
        mHobbies.setText(mUserModule.getmHobby());
        mInsurance.setText(mUserModule.getInsurance());
        mIllness.setText(mUserModule.ismIsHasChronicDisease());

        mSubmitBtn.setText(getString(R.string.update_user));

    }

    private void fillTaliaaList() {
        JSONArray taliaaList = BackendProxy.getInstance().mTaliaaManager.mTaliaaList;
        int targetTaliaaIndex = -1;
        if (taliaaList != null) {

            String[] taliaasNames = new String[taliaaList.length()];
            for (int i = 0; i < taliaaList.length(); i++) {
                JSONObject currentObjet = taliaaList.optJSONObject(i);
                if (currentObjet != null) {
                    taliaasNames[i] = currentObjet.optString("name");
                    if (taliaasNames[i].equalsIgnoreCase(mUserModule.getTaliaaName())) {
                        targetTaliaaIndex = i;
                        mSelectedTaliaaPosition = targetTaliaaIndex;
                    }
                }
            }

            ArrayAdapter aa = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, taliaasNames);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mTaliaaSpinner.setAdapter(aa);

            if (targetTaliaaIndex > 0) {
                mTaliaaSpinner.setSelection(targetTaliaaIndex);
            }
        }
    }

    @Override
    public boolean isImageEmpty() {
        return false;
    }

    @Override
    public void checkEmail(boolean hasFocus) {
        if (mUserModule.getmEmail().equalsIgnoreCase(mEmail.getText())) {

        } else {
            super.checkEmail(hasFocus);
        }
    }

    @Override
    public String getPassword() {
        return mPassword.getText();
    }

    @Override
    public void canPassSelfInfoContainer(CallBack callBack) {

        Boolean nameGo = mName.canGo();
        Boolean dateGo = mDate.canGo();
        Boolean numberGo = mNumber.canGo();
        Boolean passwordPass = mPassword.canGo();

        if (mUserModule.getmEmail().equalsIgnoreCase(mEmail.getText())) {
            boolean canSelfPass = nameGo && dateGo && numberGo && passwordPass && !isImageEmpty();
            if (callBack != null) {
                if (canSelfPass) {
                    callBack.onResult("");
                } else {
                    callBack.onResult(null);
                }
            }

        } else {
            if (!mPassword.canGo()) {
                return;
            }
            super.canPassSelfInfoContainer(callBack);
        }
    }
}
