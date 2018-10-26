package com.sentox.demo.function.base.application;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.sentox.demo.utils.AppUtils;

/**
 * 描述：自定义application
 * 说明：
 * Created by Sentox
 * Created on 2018/9/14
 */
public class GOApplication extends MultiDexApplication {

    private static Context sInstance;
    private static String sCurrentProcessName;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        sCurrentProcessName = AppUtils.getCurrentProcessName(getApplicationContext());
        DBManager.initSingleton(this);
        initOnDifferentProcess();
    }

    /**
     * 方法描述：在不同的进程中调用不同的初始化方法
     * 注意：
     **/
    private void initOnDifferentProcess() {
        if (isRunningOnMainProcess()) {
            //在主进程中
            onCreateForMainProcess();
        }
    }

    /**
     * 初始化主进程的方法
     **/
    public void onCreateForMainProcess() {
        GOApplication application = (GOApplication) sInstance;
        LauncherModel.initSingleton(application);
        FunctionInitManager.INSTANCE.init(application);
        LauncherModel.getInstance().startLoadGlobalData();
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    //*********************************************线程方法******************************************//

    /**
     * 是否在主进程运行<br>
     */
    public static boolean isRunningOnMainProcess() {
        return GOEnv.PACKAGE_NAME.equals(GOApplication.sCurrentProcessName);
    }

    public static Context getAppContext() {
        return sInstance;
    }
}
