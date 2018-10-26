package com.sentox.demo.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.sentox.demo.greendao.entry.StopWatchDataEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "STOP_WATCH_DATA_ENTITY".
*/
public class StopWatchDataEntityDao extends AbstractDao<StopWatchDataEntity, Long> {

    public static final String TABLENAME = "STOP_WATCH_DATA_ENTITY";

    /**
     * Properties of entity StopWatchDataEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, long.class, "id", true, "_id");
        public final static Property MCountTime = new Property(1, long.class, "mCountTime", false, "M_COUNT_TIME");
        public final static Property MStrCountTime = new Property(2, String.class, "mStrCountTime", false, "M_STR_COUNT_TIME");
        public final static Property MStrLastTimePlus = new Property(3, String.class, "mStrLastTimePlus", false, "M_STR_LAST_TIME_PLUS");
    }


    public StopWatchDataEntityDao(DaoConfig config) {
        super(config);
    }
    
    public StopWatchDataEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"STOP_WATCH_DATA_ENTITY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY NOT NULL ," + // 0: id
                "\"M_COUNT_TIME\" INTEGER NOT NULL ," + // 1: mCountTime
                "\"M_STR_COUNT_TIME\" TEXT," + // 2: mStrCountTime
                "\"M_STR_LAST_TIME_PLUS\" TEXT);"); // 3: mStrLastTimePlus
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"STOP_WATCH_DATA_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, StopWatchDataEntity entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
        stmt.bindLong(2, entity.getMCountTime());
 
        String mStrCountTime = entity.getMStrCountTime();
        if (mStrCountTime != null) {
            stmt.bindString(3, mStrCountTime);
        }
 
        String mStrLastTimePlus = entity.getMStrLastTimePlus();
        if (mStrLastTimePlus != null) {
            stmt.bindString(4, mStrLastTimePlus);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, StopWatchDataEntity entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
        stmt.bindLong(2, entity.getMCountTime());
 
        String mStrCountTime = entity.getMStrCountTime();
        if (mStrCountTime != null) {
            stmt.bindString(3, mStrCountTime);
        }
 
        String mStrLastTimePlus = entity.getMStrLastTimePlus();
        if (mStrLastTimePlus != null) {
            stmt.bindString(4, mStrLastTimePlus);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override
    public StopWatchDataEntity readEntity(Cursor cursor, int offset) {
        StopWatchDataEntity entity = new StopWatchDataEntity( //
            cursor.getLong(offset + 0), // id
            cursor.getLong(offset + 1), // mCountTime
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // mStrCountTime
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // mStrLastTimePlus
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, StopWatchDataEntity entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setMCountTime(cursor.getLong(offset + 1));
        entity.setMStrCountTime(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setMStrLastTimePlus(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(StopWatchDataEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(StopWatchDataEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(StopWatchDataEntity entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
