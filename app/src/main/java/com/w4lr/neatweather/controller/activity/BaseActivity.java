package com.w4lr.neatweather.controller.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by w4lr on 2016/11/7.
 */

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
}
