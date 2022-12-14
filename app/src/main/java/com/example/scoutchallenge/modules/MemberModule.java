package com.example.scoutchallenge.modules;

import com.example.scoutchallenge.helpers.StringHelper;
import com.example.scoutchallenge.helpers.Tools;

import org.json.JSONObject;

public class MemberModule {
    public static final String USER = "USER";
    public static final String KAEEDD = "KAEEDD";

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

    public JSONObject mData;

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

    public String getmSerialNumber() {
        if(StringHelper.isNullOrEmpty(mSerialNumber)){
            return "";
        }
        return mSerialNumber;
    }

    public void setmSerialNumber(String mSerialNumber) {
        this.mSerialNumber = mSerialNumber;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
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


}
