package com.sentox.demo.function.base.application;

import android.app.Application;

import com.sentox.demo.function.base.preferences.IPreferencesIds;
import com.sentox.demo.function.base.preferences.SharedPreferencesManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 业务功能初始化管理器
 *
 */
public enum FunctionInitManager {
    INSTANCE;

    private Application mContext;
    private boolean mIsGlobalDataLoadingDone = false;
    private boolean mIsAgreePrivacy = false;

    public void init(Application context) {
        GOEventBus.register(this);
        mContext = context;
    }

    /**
     * 在主线程初始化非耗时的业务功能
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GlobalDataLoadingDoneEvent event) {
        mIsGlobalDataLoadingDone = true;
        startImportantBusiness();
//        if (PrivacyHelper.isAgreePrivacy() || mIsAgreePrivacy) {
//            startNormalBusiness();
//        }
        GOEventBus.unregister(FunctionInitManager.this);
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEvent(AgreeUserProgramEvent event) {
//        mIsAgreePrivacy = true;
//        if (mIsGlobalDataLoadingDone) {
//            startNormalBusiness();
//        }
//    }

    /**
     * 执行重要业务
     */
    private void startImportantBusiness() {
        SharedPreferencesManager mSPManager = LauncherModel.getInstance().getSharedPreferencesManager();
        if(!mSPManager.contains(IPreferencesIds.KEY_FIRST_START_APP_TIME)){
            mSPManager.commitLong(IPreferencesIds.KEY_FIRST_START_APP_TIME, System.currentTimeMillis());
        }else{
//            Loger.i("zhr", "KEY_FIRST_START_APP_TIME="+mSPManager.getLong(IPreferencesIds.KEY_FIRST_START_APP_TIME,0));
        }
    }

    /**
     * 开始执行普通业务
     */
    private void startNormalBusiness() {
    }
}
