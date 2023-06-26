package com.example.scoutchallenge.models;

import android.net.Uri;

import com.example.scoutchallenge.R;
import com.example.scoutchallenge.helpers.D;
import com.example.scoutchallenge.helpers.JsonHelper;
import com.example.scoutchallenge.helpers.StringHelper;
import com.example.scoutchallenge.helpers.Tools;

import org.json.JSONObject;

public class MemberModule {
    public static final String ONSOR = "ONSOR";
    public static final String HELPING_LEADER = "HELPING_LEADER";
    public static final String AMID = "AMID";

    public static final String LEADER = "LEADER";
    public static final String MOUFAWAD = "MOUFAWAD";


    protected String mSerialNumber;
    protected String mId;
    protected String mName;

    protected String mRegisterNumber;
    protected String mPlaceOfBirth;
    protected String mEmail;
    protected String mDateOfBirth;
    protected String mPersonalBloodType;
    protected String mPersonalphone;
    protected boolean mHasClothes;
    protected String mSchool;

    protected String mFatherName;
    protected String mFatherWork;
    protected String mFatherEmail;
    protected String mFhaterPhone;
    protected String mfhaterBloodType;

    protected String mMotherName;
    protected String mMotherEmail;
    protected String mMotherWork;
    protected String mMotherPhone;
    protected String mMotherBloodType;

    protected String mFamilyNb;
    protected String mAddress;

    protected String mAddressType;
    protected String mStreetName;

    protected String mBuilding;
    protected int mFloor;

    protected String mHobby;
    protected String mIsHasChronicDisease;
    protected String mIsHasOtherAssociation;

    protected String mPassword;
    protected String mPosition;

    protected String mFawjeId;
    protected String mTaliaaId;
    protected String mMoufawadyeId;

    protected String mImageUrl;
    protected String insurance;

    protected String mFawjeName;
    protected String mFerkaName;
    protected String mTlliaaName;

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public String getmFawjeId() {
        return mFawjeId;
    }

    public String getImageUrl() {
        if (mImageUrl != null) {
            String[] splitedAvater = mImageUrl.split("\\\\");
            if (splitedAvater.length >= 2) {
                String avat = splitedAvater[1];
                return D.ASSET_URL + "/" + avat;
            }
        }
        return "";
    }


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
        if (mMoufawadyeName != null) {
            return mMoufawadyeName;
        }
        if (mData != null) {
            JSONObject moufawadiyeh = mData.optJSONObject("moufawadiyeh");
            if (moufawadiyeh != null) {
                mMoufawadyeName = moufawadiyeh.optString("name");
                return mMoufawadyeName;
            }
        }

        return "";
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
            if (taliaa != null) {
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

        if (mTlliaaName != null) {
            return mTlliaaName;
        }
        if (mData != null) {
            JSONObject taliaa = mData.optJSONObject("taliaa");
            if (taliaa != null) {
                mTlliaaName = taliaa.optString("name");
                return mTlliaaName;
            }
        }

        return "";
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


    public String getFawjeName() {
        if (mFawjeName != null) {
            return mFawjeName;
        }
        if (mData != null) {
            JSONObject fawj = mData.optJSONObject("fawj");
            if (fawj != null) {
                mFawjeId = fawj.optString("_id");
                mFawjeName = fawj.optString("name");
                return mFawjeName;
            }
        }

        return "";
    }

    public void setmFawjeName(String mFawjeName) {
        this.mFawjeName = mFawjeName;
    }

    public String getFerkaName() {
        if (mFerkaName != null) {
            return mFerkaName;
        }
        if (mData != null) {
            JSONObject squad = mData.optJSONObject("squad");
            if (squad != null) {
                mFerkaName = squad.optString("name");
                return mFerkaName;
            }
        }

        return "";
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

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public void setmFawjeId(String mFawjeId) {
        this.mFawjeId = mFawjeId;
    }

    public String getmTaliaaId() {
        return mTaliaaId;
    }

    public void setmTaliaaId(String mTaliaaId) {
        this.mTaliaaId = mTaliaaId;
    }

    public String getmMoufawadyeId() {
        return mMoufawadyeId;
    }

    public void setmMoufawadyeId(String mMoufawadyeId) {
        this.mMoufawadyeId = mMoufawadyeId;
    }

    public String getmPosition() {
        return mPosition;
    }

    public void setmPosition(String mPosition) {
        this.mPosition = mPosition;
    }

    protected JSONObject mData;

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public boolean ismHasClothes() {
        return mHasClothes;
    }

    public void setmHasClothes(boolean mHasClothes) {
        this.mHasClothes = mHasClothes;
    }

    public String getmFatherName() {
        return mFatherName;
    }

    public void setmFatherName(String mFatherName) {
        this.mFatherName = mFatherName;
    }

    public String getmSerialNumber() {
        if (StringHelper.isNullOrEmpty(mSerialNumber)) {
            return "";
        }
        return mSerialNumber;
    }

    public void setmSerialNumber(String mSerialNumber) {
        this.mSerialNumber = mSerialNumber;
    }

    public String getmId() {
        if (mData != null) {
            return mData.optString("_id");
        }

        if (mId == null) {
            mId = "";
        }
        return mId;
    }


    public String getmName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }


    public String getmRegisterNumber() {
        return mRegisterNumber;
    }

    public void setmRegisterNumber(String mRegisterNumber) {
        this.mRegisterNumber = mRegisterNumber;
    }

    public String getmPlaceOfBirth() {
        return mPlaceOfBirth;
    }

    public void setmPlaceOfBirth(String mPlaceOfBirth) {
        this.mPlaceOfBirth = mPlaceOfBirth;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmDateOfBirth() {
        return mDateOfBirth;
    }

    public void setmDateOfBirth(String mDateOfBirth) {
        this.mDateOfBirth = mDateOfBirth;
    }

    public String getmPersonalBloodType() {
        return mPersonalBloodType;
    }

    public void setmPersonalBloodType(String mPersonalBloodType) {
        this.mPersonalBloodType = mPersonalBloodType;
    }

    public String getmPersonalphone() {
        return mPersonalphone;
    }

    public void setmPersonalphone(String mPersonalphone) {
        this.mPersonalphone = mPersonalphone;
    }

    public String getmSchool() {
        return mSchool;
    }

    public void setmSchool(String mSchool) {
        this.mSchool = mSchool;
    }

    public String getmFatherWork() {
        return mFatherWork;
    }

    public void setmFatherWork(String mFatherWork) {
        this.mFatherWork = mFatherWork;
    }

    public String getmFatherEmail() {
        return mFatherEmail;
    }

    public void setmFatherEmail(String mFatherEmail) {
        this.mFatherEmail = mFatherEmail;
    }

    public String getmFhaterPhone() {
        return mFhaterPhone;
    }

    public void setmFhaterPhone(String mFhaterPhone) {
        this.mFhaterPhone = mFhaterPhone;
    }

    public String getMfhaterBloodType() {
        return mfhaterBloodType;
    }

    public void setMfhaterBloodType(String mfhaterBloodType) {
        this.mfhaterBloodType = mfhaterBloodType;
    }

    public String getmMotherName() {
        return mMotherName;
    }

    public void setmMotherName(String mMotherName) {
        this.mMotherName = mMotherName;
    }

    public String getmMotherEmail() {
        return mMotherEmail;
    }

    public void setmMotherEmail(String mMotherEmail) {
        this.mMotherEmail = mMotherEmail;
    }

    public String getmMotherWork() {
        return mMotherWork;
    }

    public void setmMotherWork(String mMotherWork) {
        this.mMotherWork = mMotherWork;
    }

    public String getmMotherPhone() {
        return mMotherPhone;
    }

    public void setmMotherPhone(String mMotherPhone) {
        this.mMotherPhone = mMotherPhone;
    }

    public String getmMotherBloodType() {
        return mMotherBloodType;
    }

    public void setmMotherBloodType(String mMotherBloodType) {
        this.mMotherBloodType = mMotherBloodType;
    }

    public String getFamilyNb() {
        return mFamilyNb;
    }


    public void setFamilyNb(String mFemalFamilyNb) {
        this.mFamilyNb = mFemalFamilyNb;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmAddressType() {
        return mAddressType;
    }

    public void setmAddressType(String mAddressType) {
        this.mAddressType = mAddressType;
    }

    public String getmStreetName() {
        return mStreetName;
    }

    public void setmStreetName(String mStreetName) {
        this.mStreetName = mStreetName;
    }

    public String getmBuilding() {
        return mBuilding;
    }

    public void setmBuilding(String mBuilding) {
        this.mBuilding = mBuilding;
    }

    public int getmFloor() {
        return mFloor;
    }

    public void setmFloor(int mFloor) {
        this.mFloor = mFloor;
    }

    public String getmHobby() {
        return mHobby;
    }

    public void setmHobby(String mHobby) {
        this.mHobby = mHobby;
    }

    public String ismIsHasChronicDisease() {
        return mIsHasChronicDisease;
    }

    public void setmIsHasChronicDisease(String mIsHasChronicDisease) {
        this.mIsHasChronicDisease = mIsHasChronicDisease;
    }

    public String getIsHasOtherAssociation() {
        return mIsHasOtherAssociation;
    }

    public void setmIsHasOtherAssociation(String mIsHasOtherAssociation) {
        this.mIsHasOtherAssociation = mIsHasOtherAssociation;
    }

    public String getPositionFullName() {
        String fullPositionText = Tools.getString(R.string.position_value);
        if (mPosition.equalsIgnoreCase(LEADER)) {
            fullPositionText = fullPositionText + ": " + Tools.getString(R.string.leader);
        }
        if (mPosition.equalsIgnoreCase(ONSOR)) {
            fullPositionText = fullPositionText + ": " + Tools.getString(R.string.user);
        }
        return fullPositionText;
    }
}
