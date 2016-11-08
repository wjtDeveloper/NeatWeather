package com.w4lr.neatweather.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.w4lr.neatweather.R;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        enterHomeActivity();


    }

    /**
     * 进入主界面
     */
    private void enterHomeActivity() {
        startActivity(new Intent(this,HomeActivity.class));
        finish();
    }
}
