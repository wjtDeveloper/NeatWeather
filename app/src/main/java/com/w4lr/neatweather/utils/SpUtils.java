package com.w4lr.neatweather.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.w4lr.neatweather.NeatApplication;

/**
 * Created by w4lr on 2016/11/6.
 */

public class SpUtils {

    private static SpUtils instance = new SpUtils();

    private static SharedPreferences mSp;

    private SpUtils() {

    }

    public static SpUtils getInstance() {
        if (mSp == null) {
            mSp = NeatApplication.getAppContext().getSharedPreferences("config", Context.MODE_PRIVATE);
        }

        return instance;
    }

    /**
     * 保存键值对
     *
     * @param key
     * @param obj
     */
    public void put(String key, Object obj) {
        if (obj instanceof String) {
            mSp.edit().putString(key, (String) obj).commit();
        } else if (obj instanceof Boolean) {
            mSp.edit().putBoolean(key, (Boolean) obj).commit();
        } else if (obj instanceof Integer) {
            mSp.edit().putInt(key, (Integer) obj).commit();
        }
    }

    public String getString(String key, String defaultValue) {
        return mSp.getString(key,defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        return mSp.getInt(key,defaultValue);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return mSp.getBoolean(key,defaultValue);
    }
}
