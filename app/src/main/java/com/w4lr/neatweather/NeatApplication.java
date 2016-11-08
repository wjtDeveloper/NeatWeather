package com.w4lr.neatweather;

import android.app.Application;
import android.content.Context;

/**
 * Created by w4lr on 2016/11/7.
 */

public class NeatApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getAppContext() {
        return mContext;
    }
}
