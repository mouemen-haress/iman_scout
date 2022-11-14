package com.example.scoutchallenge.backend;


public class BackendProxy {


    private static BackendProxy singletonInstance;
    public SalatTimeManager mSalatTimeManager;
    public UserManager mUserManager;
    public TaliaaManager mTaliaaManager;
    public LoginManager mLoginManager;
    public ActivityManager mActivityManager;


    private BackendProxy() {
        init();
    }

    public static BackendProxy getInstance() {
        if (singletonInstance == null) {
            singletonInstance = new BackendProxy();
        }
        return singletonInstance;
    }


    private void init() {
        mSalatTimeManager = new SalatTimeManager();
        mUserManager = new UserManager();
        mTaliaaManager = new TaliaaManager();
        mLoginManager = new LoginManager();
        mActivityManager = new ActivityManager();
    }

}
