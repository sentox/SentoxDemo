package com.sentox.demo.greendao.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.sentox.demo.greendao.gen.DaoMaster;
import com.sentox.demo.greendao.gen.StopWatchDataEntityDao;

/**
 * 用户数据库升级数据迁移。用greendao的会在升级时删除以往的数据
 */

public class UpdateHelper extends DaoMaster.OpenHelper {

    public UpdateHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //super.onUpgrade(db, oldVersion, newVersion);
        MigrationHelper.migrate(db, StopWatchDataEntityDao.class);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //如果不重写该方法，默认是不支持降级，会抛出不能降级的异常
        MigrationHelper.migrate(db, StopWatchDataEntityDao.class);
    }
}
