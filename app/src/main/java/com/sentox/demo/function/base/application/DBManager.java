package com.sentox.demo.function.base.application;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.sentox.demo.greendao.gen.DaoMaster;
import com.sentox.demo.greendao.gen.DaoSession;
import com.sentox.demo.greendao.helper.UpdateHelper;

/**
 * greendao管理类
 */

public class DBManager {

    public static DBManager sInstance;
    private static final String DB_NAME = "sentox_demo_db";
    private Context mContext;

    private DBManager(Context context) {
        this.mContext = context;
    }

    public static void initSingleton(Context context) {
        if (sInstance == null) {
            synchronized (DBManager.class) {
                if (sInstance == null) {
                    sInstance = new DBManager(context);
                }
            }
        }
    }

    public static DBManager getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException("You must be initSingleton first");
        }
        return sInstance;
    }

    public DaoSession getDaoSession() {
        //通过updatehelper在检测到数据库版本更新的时候会做数据库升级
        DaoMaster.OpenHelper openHelper = new UpdateHelper(mContext, DB_NAME, null);
        SQLiteDatabase sqLiteDatabase = openHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(sqLiteDatabase);
        return daoMaster.newSession();
    }
}
