package com.sentox.demo.function.base.preferences;

import android.content.Context;

import com.sentox.demo.function.base.application.GOEventBus;
import com.sentox.demo.function.base.preferences.event.SharedPreferencesDataLoadDoneEvent;
import com.sentox.demo.utils.AppUtils;

import java.util.HashMap;


/**
 * 用于管理SharedPreferences的数据加载与更新.<br>
 */
public class SharedPreferencesManager extends AbstractManager {
    private Context mContext;


    private PreferencesManager mDefaultPM;
    //这个缓存应该设计为静态，不然每个实例的缓存都不一样，但SP的值却是全局的，很容易混乱
    private static HashMap<String, Object> mSPData = new HashMap<>();

    public SharedPreferencesManager(Context context) {
        mContext = context;
        mDefaultPM = PreferencesManager.getDefaultSharedPreference(mContext);
    }

    @Override
    public void onStartLoader() {
        loadData();
    }

    /**
     * 检查是否升级用户(即老用户)
     */
    private boolean checkIsUpGradeUser() {
        int oldVersionCode = getInt(
                IPreferencesIds.KEY_STATISTICS_OLD_VERSION_CODE, 0);

        int currentVersionCode = AppUtils.getAppVersionCode(mContext);
        if (oldVersionCode != currentVersionCode) {
            commitInt(IPreferencesIds.KEY_STATISTICS_OLD_VERSION_CODE,
                    currentVersionCode);
        }
        if (currentVersionCode < 2) {
            return false;
        }
        if (oldVersionCode == 0) {
            oldVersionCode = currentVersionCode;
        }
        boolean isGrade = false;
        if (currentVersionCode > oldVersionCode) {
            isGrade = true;
        }
        return isGrade;
    }

    private void loadData() {
        mSPData.put(IPreferencesIds.KEY_FIRST_START_APP_TIME,
                mDefaultPM.getLong(IPreferencesIds.KEY_FIRST_START_APP_TIME, 0));

        mSPData.put(IPreferencesIds.IS_NEW_USER, mDefaultPM.getBoolean(IPreferencesIds.IS_NEW_USER, true));
    }

    @Override
    public void onLoadFinish() {
        // nothing interesting here.
    }

    @Override
    public void onPostMsg() {
        GOEventBus.post(new SharedPreferencesDataLoadDoneEvent());
    }


    public boolean getBoolean(String key, boolean defValue) {
        boolean value = defValue;
        if (mSPData.containsKey(key)) {
            value = (Boolean) mSPData.get(key);
        } else {
            if (mDefaultPM != null) {
                value = mDefaultPM.getBoolean(key, defValue);
                mSPData.put(key, value);
            }
        }
        return value;
    }

    public float getFloat(String key, float defValue) {
        float value = defValue;
        if (mSPData.containsKey(key)) {
            value = (Float) mSPData.get(key);
        } else {
            if (mDefaultPM != null) {
                value = mDefaultPM.getFloat(key, defValue);
                mSPData.put(key, value);
            }
        }
        return value;
    }

    public int getInt(String key, int defValue) {
        int value = defValue;
        if (mSPData.containsKey(key)) {
            value = (Integer) mSPData.get(key);
        } else {
            if (mDefaultPM != null) {
                value = mDefaultPM.getInt(key, defValue);
                mSPData.put(key, value);
            }
        }
        return value;
    }

    public long getLong(String key, long defValue) {
        long value = defValue;
        if (mSPData.containsKey(key)) {
            value = (Long) mSPData.get(key);
        } else {
            if (mDefaultPM != null) {
                value = mDefaultPM.getLong(key, defValue);
                mSPData.put(key, value);
            }
        }
        return value;
    }

    public String getString(String key, String defValue) {
        String value = defValue;
        if (mSPData.containsKey(key)) {
            value = (String) mSPData.get(key);
        } else {
            if (mDefaultPM != null) {
                value = mDefaultPM.getString(key, defValue);
                mSPData.put(key, value);
            }
        }
        return value;
    }

    public void commitBoolean(String key, boolean value) {
        mDefaultPM.edit();
        mDefaultPM.putBoolean(key, value);
        mDefaultPM.commit();
        mSPData.put(key, value);
    }

    public void commitInt(String key, int value) {
        mDefaultPM.edit();
        mDefaultPM.putInt(key, value);
        mDefaultPM.commit();
        mSPData.put(key, value);
    }

    public void commitFloat(String key, float value) {
        mDefaultPM.edit();
        mDefaultPM.putFloat(key, value);
        mDefaultPM.commit();
        mSPData.put(key, value);
    }

    public void commitLong(String key, long value) {
        mDefaultPM.edit();
        mDefaultPM.putLong(key, value);
        mDefaultPM.commit();
        mSPData.put(key, value);
    }

    public void commitString(String key, String value) {
        mDefaultPM.edit();
        mDefaultPM.putString(key, value);
        mDefaultPM.commit();
        mSPData.put(key, value);
    }


    public void remove(String key) {
        mDefaultPM.edit();
        mDefaultPM.remove(key);
        mDefaultPM.commit();
        mSPData.remove(key);
    }

    public boolean contains(String key) {
        return mDefaultPM.contains(key);
    }
}
