package com.example.scoutchallenge.models;

import android.net.Uri;

import com.example.scoutchallenge.helpers.JsonHelper;
import com.example.scoutchallenge.helpers.StringHelper;

import org.json.JSONObject;

public class UserModule extends MemberModule {
    protected String mFawjeName;
    protected String mFerkaName;
    protected String mTlliaaName;

    public String getmTlliaaName() {
        return mTlliaaName;
    }

    public void setmTlliaaName(String mTlliaaName) {
        this.mTlliaaName = mTlliaaName;
    }

    protected String mFerkaId;
    protected String mMoufawadyeName;
    protected JSONObject mTaliaa;

    public String getmFerkaId() {
        return mFerkaId;
    }

    public void setmFerkaId(String mFerkaId) {
        this.mFerkaId = mFerkaId;
    }

    protected Uri mImageUri;


    public String getmMoufawadyeName() {
        return mMoufawadyeName;
    }

    public void setmMoufawadyeName(String mMoufawadyeName) {
        this.mMoufawadyeName = mMoufawadyeName;
    }

    public JSONObject getData() {
        return mData;
    }

    public void setData(JSONObject data) {
        this.mData = data;
        if (mData != null) {
            mId = mData.optString("_id");
            mPosition = mData.optString("Position");
            mName = mData.optString("Name");
            mEmail = mData.optString("Email");
            mPassword = mData.optString("Password");
            mDateOfBirth = mData.optString("Date");
            mPersonalBloodType = mData.optString("BloodType");
            mRegisterNumber = mData.optString("Number");
            mFatherName = mData.optString("FatherName");
            mfhaterBloodType = mData.optString("FatherBloodType");
            mFatherWork = mData.optString("FatherWork");
            mMotherName = mData.optString("MotherName");
            mMotherBloodType = mData.optString("MotherBloodType");
            mMotherWork = mData.optString("MotherWork");
            mPlaceOfBirth = mData.optString("PlaceOfBirth");
            mAddress = mData.optString("Address");
            mFamilyNb = mData.optString("NbOfFamily");
            mAddressType = mData.optString("AddressType");
            mSchool = mData.optString("CurrentEducation");
            mIsHasChronicDisease = mData.optString("Illness");
            mHasClothes = mData.optBoolean("Cloth");
            insurance = mData.optString("Insurance");
            mImageUrl = mData.optString("file");
            mSerialNumber = mData.optString("SerialNumber");
            JSONObject moufawadiyeh = mData.optJSONObject("moufawadiyeh");
            if (moufawadiyeh != null) {
                mMoufawadyeId = moufawadiyeh.optString("_id");
                mMoufawadyeName = moufawadiyeh.optString("name");

            }

            JSONObject fawj = mData.optJSONObject("fawj");
            if (fawj != null) {
                mFawjeId = fawj.optString("_id");
                mFawjeName = fawj.optString("name");

            }

            JSONObject squad = mData.optJSONObject("squad");
            if (squad != null) {
                mFerkaName = squad.optString("name");
                mFerkaId = squad.optString("_id");

            }

            JSONObject taliaa = mData.optJSONObject("taliaa");
            if (squad != null) {
                mTlliaaName = taliaa.optString("name");
                mTaliaaId = taliaa.optString("_id");

            }


        }
    }

    public void setTaliaaName(String mTaliaaName) {
        JsonHelper.put(mTaliaa, "name", mTaliaaName);

    }


    public String getTaliaaId() {
        if (mTaliaa != null) {
            return mTaliaa.optString("_id");
        }
        if (mData != null) {
            JSONObject taliaa = mData.optJSONObject("taliaa");
            if (taliaa != null) {
                return taliaa.optString("_id");
            }
        }
        return "";
    }

    public void setTaliaaId(String id) {
        if (mTaliaa == null) {
            mTaliaa = new JSONObject();
        }

        if (mData != null) {
            JSONObject taliaa = mData.optJSONObject("taliaa");
            if (taliaa != null) {
                JsonHelper.put(taliaa, "_id", id);
            }
        }
        JsonHelper.put(mTaliaa, "_id", id);
    }


    public String getTaliaaName() {
        if (mTaliaa != null) {
            return mTaliaa.optString("name");
        }
        return mTlliaaName;
    }


    public Uri getmImageUri() {
        return mImageUri;
    }

    public void setmImageUri(Uri mImageUri) {
        this.mImageUri = mImageUri;
    }

    public String getmProfileUrl() {
        return mProfileUrl;
    }

    public void setmProfileUrl(String mProfileUrl) {
        this.mProfileUrl = mProfileUrl;
    }

    protected String mProfileUrl;


    public String getmFawjeName() {
        return mFawjeName;
    }

    public void setmFawjeName(String mFawjeName) {
        this.mFawjeName = mFawjeName;
    }

    public String getmFerkaName() {
        return mFerkaName;
    }

    public void setmFerkaName(String mFerkaName) {
        this.mFerkaName = mFerkaName;
    }

    public void setId(String mId) {
        this.mId = mId;
        if (mData != null) {
            JsonHelper.put(mData, "_id", mId);
        }
    }

    public String getId() {
        String id = mId;
        if (StringHelper.isNullOrEmpty(mId)) {
            if (mData != null) {
                id = mData.optString("_id");
            }

        }
        return id;
    }
}
