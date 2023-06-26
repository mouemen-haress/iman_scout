package com.example.scoutchallenge.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.scoutchallenge.R;
import com.example.scoutchallenge.backend.BackendProxy;
import com.example.scoutchallenge.backend.LoginManager;
import com.example.scoutchallenge.conponents.HeadComponents;
import com.example.scoutchallenge.conponents.MButtonComponent;
import com.example.scoutchallenge.conponents.MDrawableEditText;
import com.example.scoutchallenge.conponents.MTextView;
import com.example.scoutchallenge.conponents.components_Group.registerNumberPopup;
import com.example.scoutchallenge.interfaces.CallBack;
import com.example.scoutchallenge.interfaces.DidOnTap;


public class LoginView extends HeadView {
    protected ImageView mHeroImage;
    protected MDrawableEditText mEmailEditText;
    protected MDrawableEditText mPasswordEditText;
    protected MButtonComponent mLoginBtn;
    protected MTextView mCreateAccountLabel;


    public LoginView() {
        // Required empty public constructor
    }

    public static LoginView newInstance(String param1, String param2) {
        LoginView fragment = new LoginView();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.login_view_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void init(Context ctx, View view) {
        super.init(ctx, view);

//        if (LocalStorage.getString(LocalStorage.SQUAD) != null) {
//            pushAndSetRootView(R.id.loginView, R.id.bootView);
//            return;
//        }
        mEmailEditText = getViewById(R.id.phone);
        mEmailEditText.addCondition(MDrawableEditText.REQUIRE);
        mEmailEditText.setPlaceHolder(getString(R.string.email));

        mPasswordEditText = getViewById(R.id.password);
        mPasswordEditText.setPasswordType();
        mPasswordEditText.addCondition(MDrawableEditText.REQUIRE);
        mPasswordEditText.setPlaceHolder(getString(R.string.enter_password));

        mLoginBtn = getViewById(R.id.login_btn);
        mLoginBtn.setText("تسجيل الدخول");
        mLoginBtn.setTextSize(18);
        mLoginBtn.setOnTapListener(new DidOnTap() {
            @Override
            public void onTap(HeadComponents target) {
                login();
            }
        });

//        mCreateAccountLabel = getViewById(R.id.create_account_text);
//        mCreateAccountLabel.setText("أنشأ حساب الأن");
//        mCreateAccountLabel.setTextColor(R.color.secondColor);
//        mCreateAccountLabel.setOnTapListener(() -> {
//            createNewAccount();
//        });

    }

    private void login() {

        if (true || mEmailEditText.canGo() && mPasswordEditText.canGo()) {
            showLockedLoading();
            String email = mEmailEditText.getText().trim();
            String password = mPasswordEditText.getText().trim();

//          leader
//            email = "alloush32@live.com";
//            password = "alloush32";

//            email = "helping32@live.com";
//            password = "helping32";

            //moufawad
//            email = "mfd32@live.com";
//            password = "mfd32";

//            user
//            email = "mouemen@iman.com";
//            password = "11111111";

            //           ktor user
            email = "moemen";
            password = "11111111";


            BackendProxy.getInstance().mLoginManager.authenticate(email, password, new CallBack() {
                @Override
                public void onResult(String response) {
                    runOnUiThread(() -> {
                        hideLockedLoading();
                        if (response != null) {
                            if (response.equalsIgnoreCase(LoginManager.SUCCESS_LOGIN)) {
                                pushAndSetRootView(R.id.loginView, R.id.bootView);
                                return;
                            } else {
                                showSimplePopup(response);
                            }


                        } else {
                            showSimplePopup(getString(R.string.server_error));
                        }
                    });

                }
            });

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    private void createNewAccount() {
        showPopup(new registerNumberPopup(getContext(), null));
    }
}