package com.sentox.demo.function.base.application;

import android.content.Context;

import com.sentox.demo.function.base.preferences.SharedPreferencesManager;

/**
 * 描述：核心数据加载类
 * 说明：数据
 * Created by Sentox
 * Created on 2018/9/17
 */
public class LauncherModel {

    private Context mContext;
    private static LauncherModel sInstance;
    private static volatile SharedPreferencesManager mSharedPreferencesManager;
    private boolean mIsInit;
    private boolean mGlobalDataLoadingDone;

    private LauncherModel(Context context) {
        mGlobalDataLoadingDone = false;
        mContext = context.getApplicationContext();
        initSp(mContext);
    }

    /**
     * 初始化单例,在程序启动时调用
     */
    static void initSingleton(Context context) {
        sInstance = new LauncherModel(context);
    }

    public static SharedPreferencesManager initSp(Context context) {
        if (mSharedPreferencesManager == null) {
            synchronized (SharedPreferencesManager.class) {
                if (mSharedPreferencesManager == null) {
                    mSharedPreferencesManager = new SharedPreferencesManager(context.getApplicationContext());
                }
            }
        }
        return mSharedPreferencesManager;
    }

    /**
     * 获取实例
     */
    public static LauncherModel getInstance() {
        if (sInstance == null) {
            sInstance = new LauncherModel(GOApplication.getAppContext());
        }
        return sInstance;
    }

    /**
     * 开始加载全局的必要数据,在程序启动时调用
     */
    void startLoadGlobalData() {
        if (mIsInit) {
            return;
        }
        mIsInit = true;

        new Thread("LauncherModel-Thread") {
            @Override
            public void run() {
                // 处理加载各个模块的程序必要全局数据
                startLoadData();
                notifyLoadingDone();
            }

            private void notifyLoadingDone() {
                ThreadHelper.postRunOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onGlobalDataLoadingDone();
                    }
                });
            }
        }.start();
    }

    /**
     * 开始加载各个模块的数据.<br>
     * 在非主线程调用.<br>
     */
    private void startLoadData() {
        mSharedPreferencesManager.startLoader();
        mSharedPreferencesManager.loadFinish();
    }

    /**
     * 全局数据加载完成时的回调，在主线程回调<br>
     */
    private void onGlobalDataLoadingDone() {
        mGlobalDataLoadingDone = true;
        GOEventBus.post(new GlobalDataLoadingDoneEvent());
    }

    /**
     * 全局数据是否加载完成。若为false表示数据未加载完成，<br>
     * 此时可监听事件
     */
    public boolean isGlobalDataLoadingDone() {
        return mGlobalDataLoadingDone;
    }

    public SharedPreferencesManager getSharedPreferencesManager() {
        return mSharedPreferencesManager;
    }

}
