package com.example.scoutchallenge.views;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCaller;
import androidx.core.widget.NestedScrollView;

import com.example.scoutchallenge.R;
import com.example.scoutchallenge.backend.BackendProxy;
import com.example.scoutchallenge.conponents.CategoriesComponent;
import com.example.scoutchallenge.conponents.CircularImageView;
import com.example.scoutchallenge.conponents.DatePickerComponent;
import com.example.scoutchallenge.conponents.HeadComponents;
import com.example.scoutchallenge.conponents.MButtonComponent;
import com.example.scoutchallenge.conponents.MDrawableEditText;
import com.example.scoutchallenge.helpers.D;
import com.example.scoutchallenge.helpers.JsonHelper;
import com.example.scoutchallenge.helpers.PasswordHelper;
import com.example.scoutchallenge.interfaces.CallBack;
import com.example.scoutchallenge.interfaces.CategoriesMenuDelegate;
import com.example.scoutchallenge.interfaces.DidOnTap;
import com.example.scoutchallenge.modules.UserModule;
import com.github.dhaval2404.imagepicker.ImagePicker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class AddUserView extends HeadView implements CategoriesMenuDelegate, ActivityResultCaller {

    public static final String FATHER_INFOS = "?????????????? ????????";
    public static final String MOTHER_INFOS = "?????????????? ????????";
    //    public static final String SCOUT_INFOS = "?????????????? ??????????";
    public static final String SELF_INFOS = "?????????????? ??????????";
    public static final String SOCIAL_INFOS = "?????????????? ????????????????";

    public static final String OTHER = "????????";


    private static final String[] MENU_IDS = {SELF_INFOS, FATHER_INFOS, MOTHER_INFOS, SOCIAL_INFOS, OTHER};
    Map<String, LinearLayout> mContainerMap;
    UserModule mUserModule = new UserModule();

    CategoriesComponent mCategoriesMenu;
    protected NestedScrollView mScrollView;

    protected FrameLayout mMainContainer;

    protected LinearLayout mSelfContainer;
    protected CircularImageView mProfile;
    protected MDrawableEditText mName;
    protected MDrawableEditText mEmail;
    protected DatePickerComponent mDate;
    protected MDrawableEditText mBloodType;
    protected Switch mClothesSwitch;
    protected Spinner mTaliaaSpinner;
    protected MDrawableEditText mNumber;


    protected LinearLayout mFatherContainer;
    protected MDrawableEditText mFatherName;
    protected MDrawableEditText mFatherBloodType;
    protected MDrawableEditText mFatherNumber;
    protected MDrawableEditText mFatherWork;


    protected LinearLayout mMotherContainer;
    protected MDrawableEditText mMotherName;
    protected MDrawableEditText mMotherBloodType;
    protected MDrawableEditText mMotherNumber;
    protected MDrawableEditText mMotherWork;

//    protected LinearLayout mScoutContainer;
//    protected MTextView mSquad;
//    protected MTextView mFawj;


    protected LinearLayout mSocialContainer;
    protected MDrawableEditText mPlaceOfBirth;
    protected MDrawableEditText mAddress;
    protected MDrawableEditText mNbOfFamily;
    protected MDrawableEditText mAddressType;


    protected LinearLayout mOtherContainer;
    protected MDrawableEditText mCurrentEducation;
    protected MDrawableEditText mHobbies;
    protected MDrawableEditText mInsurance;
    protected MDrawableEditText mIllness;
    protected MButtonComponent mSubmitBtn;


    protected UserModule mModule;
    protected String mCurrentSelectedSection;
    private Uri mImageUri = null;
    public int mSelectedTaliaaPosition = -1;


    @Override
    public void init(Context ctx, View view) {
        super.init(ctx, view);
        mContainerMap = new HashMap<>();

        mCategoriesMenu = new CategoriesComponent(ctx);
        mCategoriesMenu.mDelegate = this;
        mCategoriesMenu.mIsNeedPreventSelection = true;
        mRootView.addView(mCategoriesMenu);

        mScrollView = new NestedScrollView(ctx);
        mRootView.addView(mScrollView);
        setUpMenu();

        mMainContainer = new FrameLayout(ctx);
        mScrollView.addView(mMainContainer);

        //  Self Infos
        mSelfContainer = new LinearLayout(ctx);
        mContainerMap.put(SELF_INFOS, mSelfContainer);
        mSelfContainer.setOrientation(LinearLayout.VERTICAL);
        mMainContainer.addView(mSelfContainer);

        mProfile = new CircularImageView(ctx);
        mProfile.setImageResource(getImage("user"));
        mProfile.setOnTapListener(new DidOnTap() {
            @Override
            public void onTap(HeadComponents view) {
                pickImage();
            }
        });
        mSelfContainer.addView(mProfile);

        mName = new MDrawableEditText(ctx);
        mName.setPlaceHolder(getString(R.string.name));
        mName.setHintText(getString(R.string.name));
        mName.addCondition(MDrawableEditText.REQUIRE);
        mSelfContainer.addView(mName);

        mEmail = new MDrawableEditText(ctx);
        mEmail.setPlaceHolder(getString(R.string.email));
        mEmail.addCondition(MDrawableEditText.REQUIRE, MDrawableEditText.EMAIL_CHECKING, MDrawableEditText.DEFAULT);
        mEmail.setHintText(getString(R.string.email));
        mEmail.setDefaultErrorMsg(getString(R.string.email_exist));
        mEmail.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    BackendProxy.getInstance().mUserManager.checkUserEmail(mEmail.getText().trim(), new CallBack() {
                        @Override
                        public void onResult(String response) {
                            if (response != null) {
                                mEmail.removeCondition(MDrawableEditText.DEFAULT);
                            } else {

                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mEmail.canGo();

                                }
                            });
                        }
                    });
                } else {
                }
            }
        });
        mSelfContainer.addView(mEmail);

        mDate = new DatePickerComponent(ctx);
        mDate.setHint(getString(R.string.date_of_birth));
        mDate.setIcon(D.getResourceId("date_icon"));
        mDate.addCondition(DatePickerComponent.REQUIRE);
        mDate.mDelegate = (new DidOnTap() {
            @Override
            public void onTap(HeadComponents view) {
                getDate();
            }
        });
        mSelfContainer.addView(mDate);

        mBloodType = new MDrawableEditText(ctx);
        mBloodType.setPlaceHolder(getString(R.string.blood_type));
        mBloodType.setHintText(getString(R.string.blood_type));
        mSelfContainer.addView(mBloodType);

        mSelfContainer.addView(generateLine());

        mClothesSwitch = new Switch(ctx);
        mClothesSwitch.setText(getString(R.string.did_you_have_clothe));
        mSelfContainer.addView(mClothesSwitch);

        mSelfContainer.addView(generateLine());


        mTaliaaSpinner = new Spinner(ctx);
        fillTaliaaList();
        mTaliaaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectedTaliaaPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mSelfContainer.addView(mTaliaaSpinner);

        mSelfContainer.addView(generateLine());


        mNumber = new MDrawableEditText(ctx);
        mNumber.setPlaceHolder(getString(R.string.number));
        mNumber.setInpuType(InputType.TYPE_CLASS_NUMBER);
        mNumber.addCondition(MDrawableEditText.REQUIRE);
        mNumber.setHintText(getString(R.string.number));
        mSelfContainer.addView(mNumber);


        //  Father's Infos
        mFatherContainer = new LinearLayout(ctx);
        mContainerMap.put(FATHER_INFOS, mFatherContainer);
        mFatherContainer.setVisibility(View.GONE);
        mFatherContainer.setOrientation(LinearLayout.VERTICAL);
        mMainContainer.addView(mFatherContainer);

        mFatherName = new MDrawableEditText(ctx);
        mFatherName.setPlaceHolder(getString(R.string.name));
        mFatherName.addCondition(MDrawableEditText.REQUIRE);
        mFatherContainer.addView(mFatherName);

//        mFatherEmail = new MDrawableEditText(ctx);
//        mFatherContainer.addView(mFatherEmail);
//
//        mFatherDate = new MDrawableEditText(ctx);
//        mFatherContainer.addView(mFatherDate);

        mFatherBloodType = new MDrawableEditText(ctx);
        mFatherBloodType.setPlaceHolder(getString(R.string.father_blood_type));
        mFatherContainer.addView(mFatherBloodType);

        mFatherNumber = new MDrawableEditText(ctx);
        mFatherNumber.setPlaceHolder(getString(R.string.father_number));
        mFatherNumber.addCondition(MDrawableEditText.REQUIRE);
        mFatherNumber.setInpuType(InputType.TYPE_CLASS_NUMBER);
        mFatherContainer.addView(mFatherNumber);

        mFatherWork = new MDrawableEditText(ctx);
        mFatherWork.setPlaceHolder(getString(R.string.father_work));
        mFatherContainer.addView(mFatherWork);


        //  Mother Infos
        mMotherContainer = new LinearLayout(ctx);
        mContainerMap.put(MOTHER_INFOS, mMotherContainer);
        mMotherContainer.setVisibility(View.GONE);
        mMotherContainer.setOrientation(LinearLayout.VERTICAL);
        mMainContainer.addView(mMotherContainer);

        mMotherName = new MDrawableEditText(ctx);
        mMotherName.setPlaceHolder(getString(R.string.name));
        mMotherName.addCondition(MDrawableEditText.REQUIRE);
        mMotherContainer.addView(mMotherName);

//        mMotherEmail = new MDrawableEditText(ctx);
//        mMotherContainer.addView(mMotherEmail);
//
//        mMotherDate = new MDrawableEditText(ctx);
//        mMotherContainer.addView(mMotherDate);

        mMotherBloodType = new MDrawableEditText(ctx);
        mMotherBloodType.setPlaceHolder(getString(R.string.mother_blood_type));
        mMotherContainer.addView(mMotherBloodType);

        mMotherNumber = new MDrawableEditText(ctx);
        mMotherNumber.setPlaceHolder(getString(R.string.mother_number));
        mMotherNumber.setInpuType(InputType.TYPE_CLASS_NUMBER);
        mMotherNumber.addCondition(MDrawableEditText.REQUIRE);
        mMotherContainer.addView(mMotherNumber);

        mMotherWork = new MDrawableEditText(ctx);
        mMotherWork.setPlaceHolder(getString(R.string.mother_work));
        mMotherContainer.addView(mMotherWork);


        //  Scout Infos
//        mScoutContainer = new LinearLayout(ctx);
//        mContainerMap.put(SCOUT_INFOS, mScoutContainer);
//        mScoutContainer.setVisibility(View.GONE);
//        mScoutContainer.setOrientation(LinearLayout.VERTICAL);
//        mMainContainer.addView(mScoutContainer);
//
//        mSquad = new MTextView(ctx);
//        mSquad.setText("??????????????");
//        mScoutContainer.addView(mSquad);
//
//        mFawj = new MTextView(ctx);
//        mSquad.setText("?????????????? ?????????????? ?????? ?????????? ????????????");
//        mScoutContainer.addView(mFawj);


        //  Social Infos
        mSocialContainer = new LinearLayout(ctx);
        mContainerMap.put(SOCIAL_INFOS, mSocialContainer);
        mSocialContainer.setVisibility(View.GONE);
        mSocialContainer.setOrientation(LinearLayout.VERTICAL);
        mMainContainer.addView(mSocialContainer);

        mPlaceOfBirth = new MDrawableEditText(ctx);
        mPlaceOfBirth.setPlaceHolder(getString(R.string.place_Of_birth));
        mSocialContainer.addView(mPlaceOfBirth);

        mAddress = new MDrawableEditText(ctx);
        mAddress.setPlaceHolder(getString(R.string.address));
        mAddress.addCondition(MDrawableEditText.REQUIRE);
        mSocialContainer.addView(mAddress);

        mNbOfFamily = new MDrawableEditText(ctx);
        mNbOfFamily.setPlaceHolder(getString(R.string.nb_of_family));
        mSocialContainer.addView(mNbOfFamily);

        mAddressType = new MDrawableEditText(ctx);
        mAddressType.setPlaceHolder(getString(R.string.address_type));
        mSocialContainer.addView(mAddressType);


        //  Other Infos
        mOtherContainer = new LinearLayout(ctx);
        mContainerMap.put(OTHER, mOtherContainer);
        mOtherContainer.setVisibility(View.GONE);
        mOtherContainer.setOrientation(LinearLayout.VERTICAL);
        mMainContainer.addView(mOtherContainer);

        mCurrentEducation = new MDrawableEditText(ctx);
        mCurrentEducation.setPlaceHolder(getString(R.string.current_education));
        mCurrentEducation.addCondition(MDrawableEditText.REQUIRE);
        mOtherContainer.addView(mCurrentEducation);

        mHobbies = new MDrawableEditText(ctx);
        mHobbies.setPlaceHolder(getString(R.string.hobbies));
        mOtherContainer.addView(mHobbies);

        mInsurance = new MDrawableEditText(ctx);
        mInsurance.setPlaceHolder(getString(R.string.is_there_insurance));
        mOtherContainer.addView(mInsurance);

        mIllness = new MDrawableEditText(ctx);
        mIllness.setPlaceHolder(getString(R.string.is_there_illness));
        mOtherContainer.addView(mIllness);

        mSubmitBtn = new MButtonComponent(ctx);
        mSubmitBtn.setText(getString(R.string.add));
        mOtherContainer.addView(mSubmitBtn);
        mSubmitBtn.setOnTapListener(new DidOnTap() {
            @Override
            public void onTap(HeadComponents view) {
                addUser();
            }
        });

        requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

        mCurrentSelectedSection = SELF_INFOS;
        layoutViews();
    }

    private void fillTaliaaList() {
        JSONArray taliaaList = BackendProxy.getInstance().mTaliaaManager.mTaliaaList;
        if (taliaaList != null) {
            String[] taliaasName = new String[taliaaList.length()];
            for (int i = 0; i < taliaaList.length(); i++) {
                JSONObject currentObjet = taliaaList.optJSONObject(i);
                if (currentObjet != null) {
                    taliaasName[i] = currentObjet.optString("name");
                }
            }
            ArrayAdapter aa = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, taliaasName);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mTaliaaSpinner.setAdapter(aa);
        }
    }

    private void pickImage() {


        ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .createIntent(new Function1<Intent, Unit>() {
                    @Override
                    public Unit invoke(Intent intent) {
                        mLauncher.launch(intent);
                        return null;
                    }
                });

    }

    private void addUser() {
        if (canPassOtherContainer() || D.IS_STIMILATE_ADD_USER_ENABLED) {
            mUserModule.setName(mName.getText().trim());
            mUserModule.setmEmail(mEmail.getText().trim());
            mUserModule.setmDateOfBirth(mDate.getText());
            mUserModule.setmRegisterNumber(mNumber.getText().trim());
            mUserModule.setmPersonalBloodType(mBloodType.getText().trim());
            mUserModule.setmHasClothes(mClothesSwitch.hasSelection());
            if (true || mSelectedTaliaaPosition != -1) {
                JSONArray taliaaList = BackendProxy.getInstance().mTaliaaManager.mTaliaaList;
                JSONObject taliaaObj = JsonHelper.getJSONObject(taliaaList, 0);

                mUserModule.setTaliaaId(taliaaObj.optString("_id"));

            }
            mUserModule.setmFerkaName(mFatherName.getText().trim());
            mUserModule.setMfhaterBloodType(mFatherBloodType.getText().trim());
            mUserModule.setmFhaterPhone(mFatherNumber.getText().trim());
            mUserModule.setmFatherWork(mFatherWork.getText().trim());
            mUserModule.setmMotherName(mMotherName.getText().trim());
            mUserModule.setmMotherBloodType(mMotherBloodType.getText().trim());
            mUserModule.setmMotherPhone(mMotherNumber.getText().trim());
            mUserModule.setmMotherWork(mMotherWork.getText().trim());
            mUserModule.setmPlaceOfBirth(mPlaceOfBirth.getText().trim());
            mUserModule.setmAddress(mAddress.getText().trim());
            mUserModule.setFamilyNb(mNbOfFamily.getText().trim());
            mUserModule.setmAddressType(mAddressType.getText().trim());
            mUserModule.setmSchool(mCurrentEducation.getText().trim());
            mUserModule.setmIsHasChronicDisease(mIllness.getText().trim());
            mUserModule.setmIsHasOtherAssociation(mInsurance.getText().trim());
            mUserModule.setmPassword(PasswordHelper.generateRandomdPassword(8));
            showLockedLoading();

            BackendProxy.getInstance().mUserManager.addUser(mUserModule, new CallBack() {
                @Override
                public void onResult(String response) {
                    runOnUiThread(() -> {
                        hideLockedLoading();

                        if (response != null) {
                            BackendProxy.getInstance().mUserManager.getAllUser(null);
                            showSimplePopup(getString(R.string.user_added_success));
                        } else {
                            showSimplePopup(getString(R.string.server_error));
                        }
                    });
                }
            });
        }
    }


    public void layoutViews() {
        int margin = dpToPx(16);
        int btnHeight = dpToPx(40);
        int icon = dpToPx(120);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMarginStart(margin);
        params.topMargin = margin;
        mCategoriesMenu.setLayoutParams(params);

        mCategoriesMenu.measure(0, 0);
        int categoriesHeight = mCategoriesMenu.getMeasuredHeight();

        params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMarginStart(margin);
        params.setMarginEnd(margin);
        params.topMargin = margin + categoriesHeight;
        params.bottomMargin = margin + getBottomNavHeight();

        mScrollView.setLayoutParams(params);


        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearParams.setMarginStart(margin);
        linearParams.topMargin = margin;
        mProfile.setLayoutParams(linearParams);
        mName.setLayoutParams(linearParams);
        mEmail.setLayoutParams(linearParams);
        mDate.setLayoutParams(linearParams);
        mBloodType.setLayoutParams(linearParams);
        mTaliaaSpinner.setLayoutParams(linearParams);
        mTaliaaSpinner.setPadding(margin / 2, margin / 2, margin / 2, margin / 2);
        mNumber.setLayoutParams(linearParams);
        mFatherName.setLayoutParams(linearParams);
//        mFatherDate.setLayoutParams(linearParams);
        mFatherBloodType.setLayoutParams(linearParams);
        mFatherNumber.setLayoutParams(linearParams);
        mFatherWork.setLayoutParams(linearParams);
        mMotherName.setLayoutParams(linearParams);
//        mMotherDate.setLayoutParams(linearParams);
        mMotherBloodType.setLayoutParams(linearParams);
        mMotherNumber.setLayoutParams(linearParams);
        mMotherWork.setLayoutParams(linearParams);
        mPlaceOfBirth.setLayoutParams(linearParams);
        mAddress.setLayoutParams(linearParams);
        mNbOfFamily.setLayoutParams(linearParams);
        mAddressType.setLayoutParams(linearParams);
        mCurrentEducation.setLayoutParams(linearParams);
        mHobbies.setLayoutParams(linearParams);
        mInsurance.setLayoutParams(linearParams);
        mIllness.setLayoutParams(linearParams);


        linearParams = new LinearLayout.LayoutParams(icon, icon);
        linearParams.gravity = Gravity.CENTER_HORIZONTAL;
        linearParams.topMargin = margin;
        mProfile.setLayoutParams(linearParams);


        linearParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearParams.gravity = Gravity.CENTER_HORIZONTAL;
        linearParams.topMargin = margin;
        linearParams.setMarginStart(margin);
        linearParams.setMarginEnd(margin);
        mClothesSwitch.setPadding(margin / 2, margin / 2, margin / 2, margin / 2);
        mClothesSwitch.setLayoutParams(linearParams);


        linearParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, btnHeight);
        linearParams.setMarginStart(margin);
        linearParams.topMargin = margin * 2;
        mSubmitBtn.setLayoutParams(linearParams);


    }

    private void setUpMenu() {
        JSONArray array = new JSONArray();
        for (int i = 0; i < MENU_IDS.length; i++) {
            JSONObject object = new JSONObject();
            JsonHelper.put(object, "title", MENU_IDS[i]);
            JsonHelper.put(object, "id", MENU_IDS[i]);
            boolean isSelected = false;
            if (i == 0) {
                isSelected = true;
            }
            JsonHelper.put(object, "isSelected", isSelected);
            array.put(object);
        }
        mCategoriesMenu.setUpMenuArray(array);
    }

    @Override
    public void didPreventSelectCategoryCell(JSONObject data, View view, int position) {
        boolean shouldReturn = false;
        if (D.IS_STIMILATE_ADD_USER_ENABLED) {
            addUser();
            return;
        }
        switch (mCurrentSelectedSection) {
            case SELF_INFOS:
                if (!canPassSelfInfoContainer()) {
                    shouldReturn = true;
                }
                break;

            case FATHER_INFOS:
                if (!canPassFatherContainer()) {
                    shouldReturn = true;
                }
                break;


            case MOTHER_INFOS:
                if (!canPassMotherContainer()) {
                    shouldReturn = true;
                }
                break;


            case SOCIAL_INFOS:
                if (!canPassSocialContainer()) {
                    shouldReturn = true;
                }
                break;

            case OTHER:
                if (!canPassOtherContainer()) {
                    shouldReturn = true;
                }
                break;
            default:
                break;
        }
        if (shouldReturn) {
            return;
        }

        mCategoriesMenu.selectionAction(view, position);
        didSelectCategoryCell(data);
    }


    @Override
    public void didSelectCategoryCell(JSONObject data) {
        if (data != null) {
            String id = data.optString("id");
            mCurrentSelectedSection = id;
            handleContainerVisibility(id);
            switch (id) {
                case SELF_INFOS:
                    mSelfContainer.setVisibility(View.VISIBLE);

                    break;
                default:
                    break;
            }
        }
    }


    private void getDate() {
        final Calendar myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                String myFormat = "MM/dd/yy";
                SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
                mDate.setHint(dateFormat.format(myCalendar.getTime()));
            }
        };

        new DatePickerDialog(getContext(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }


    private void handleContainerVisibility(String id) {
        Set<String> mapKeys = mContainerMap.keySet();
        for (String key : mapKeys) {
            if (key.equalsIgnoreCase(id)) {
                mContainerMap.get(key).setVisibility(View.VISIBLE);
            } else {
                mContainerMap.get(key).setVisibility(View.GONE);

            }
        }
    }

    public boolean canPassSelfInfoContainer() {
        Boolean nameGo = mName.canGo();
        Boolean dateGo = mDate.canGo();
        Boolean numberGo = mNumber.canGo();
        Boolean emailGo = mEmail.canGo();

        if (mImageUri == null) {
            Toast.makeText(getActivity(), getString(R.string.image_require), Toast.LENGTH_LONG).show();
        }

        return nameGo && dateGo && numberGo && emailGo && mImageUri != null;
    }

    public boolean canPassFatherContainer() {
        Boolean fatherNameGo = mFatherName.canGo();
        Boolean fatherNumberGo = mFatherNumber.canGo();

        return fatherNameGo && fatherNumberGo;
    }

    public boolean canPassMotherContainer() {
        Boolean motherNameGo = mMotherName.canGo();
        Boolean motherNumberGo = mMotherNumber.canGo();

        return motherNameGo && motherNumberGo;
    }

    public boolean canPassSocialContainer() {
        Boolean addressGo = mAddress.canGo();
        return addressGo;
    }

    public boolean canPassOtherContainer() {

        Boolean currentEducationGo = mCurrentEducation.canGo();
        return currentEducationGo;
    }

    @Override
    public void onResultFromLauncher(ActivityResult result) {
        super.onResultFromLauncher(result);
        if (result.getResultCode() == RESULT_OK) {
            Uri uri = result.getData().getData();
            mProfile.setImageURI(uri);
            mImageUri = uri;
            mUserModule.setmImageUri(uri);
        } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
//            showSimplePopup();
        }
    }

    @Override
    public void onPermissionResult(boolean result) {
        super.onPermissionResult(result);
        if (result) {
            // PERMISSION GRANTED
        } else {
            popBackStack();
        }
    }
}
