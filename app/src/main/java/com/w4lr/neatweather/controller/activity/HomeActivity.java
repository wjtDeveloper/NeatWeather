package com.w4lr.neatweather.controller.activity;

import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.w4lr.neatweather.R;
import com.w4lr.neatweather.controller.adapter.DailyWeatherAdapter;
import com.w4lr.neatweather.model.Constant;
import com.w4lr.neatweather.model.bean.DailyWeatherInfo;
import com.w4lr.neatweather.utils.MD5Encoder;
import com.w4lr.neatweather.utils.SpUtils;

import java.io.IOException;
import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "HomeActivity";

    private ImageView ivMenu;

    private DrawerLayout drawer;
    private TextView tvMenuSetting;
    private TextView tvMenuUser;
    private ListView lvHome;
    private TextView tvLocation;
    private LocationClient mLocationClient;
    private BDLocationListener mLocationListener;

    private DailyWeatherInfo mDailyResult;

    /**
     * 当前定位的城市，默认为北京
     */
    private String mCity;
    private OkHttpClient mOkHttpClient;
    private DailyWeatherAdapter mAdapter;
    private String mKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();

        initListener();

        try {
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        initLocation();

    }

    /**
     * 初始化位置信息
     */
    private void initLocation() {

        mLocationListener = new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                String city = bdLocation.getCity();
                if (!TextUtils.isEmpty(city)) {
                    mCity = city;
                    SpUtils.getInstance().put("city",mCity);
                    tvLocation.setText(bdLocation.getCity());
                    mLocationClient.stop();
                    updateWeather();
                }
            }
        };

        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);      //需要地址信息
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死


        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(mLocationListener);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    /**
     * 更新天气信息
     */
    private void updateWeather() {
        mOkHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        builder.url(Constant.DAILY_WEATHER_API + URLEncoder.encode(mCity));
        builder.method("GET", null);
        Request request = builder.build();
        Call newCall = mOkHttpClient.newCall(request);
        newCall.enqueue(new Callback() {



            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(HomeActivity.this, getResources().getString(
                                R.string.get_weather_failed), Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                Log.e(TAG, "onResponse: " + response.body().string());
                processData(response.body().string());
            }
        });
    }

    /**
     * 将json转为对象，并显示
     * @param json
     */
    private void processData(String json) {
        //缓存数据
        SpUtils.getInstance().put(mKey,json);

        mDailyResult = JSON.parseObject(json, DailyWeatherInfo.class);
        mAdapter = new DailyWeatherAdapter(HomeActivity.this,mDailyResult.getResults().get(0).getDaily());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lvHome.setAdapter(mAdapter);
            }
        });
    }

    private void loadData() throws Exception {
        mCity = SpUtils.getInstance().getString("city",getResources().getString(R.string.current_city));
        tvLocation.setText(mCity);
        //取出缓存的json数据
        mKey = MD5Encoder.encode(mCity);
        String json = SpUtils.getInstance().getString(mKey,null);
        if (!TextUtils.isEmpty(json)) {
            processData(json);
        }
    }

    /**
     * 初始化界面控件
     */
    private void initViews() {
        ivMenu = (ImageView) findViewById(R.id.iv_title_menu);
        drawer = (DrawerLayout) findViewById(R.id.drawer_home);
        tvMenuSetting = (TextView) findViewById(R.id.menu_setting);
        tvMenuUser = (TextView) findViewById(R.id.menu_user);
        tvLocation = (TextView) findViewById(R.id.tv_location);
        lvHome = (ListView) findViewById(R.id.lv_home);
    }

    /**
     * 初始化监听事件
     */
    private void initListener() {
        ivMenu.setOnClickListener(this);
        tvMenuSetting.setOnClickListener(this);
        tvMenuUser.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_menu:
                //打开侧滑菜单
                drawer.openDrawer(Gravity.LEFT);
                break;
            case R.id.menu_setting:
                break;
            case R.id.menu_user:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        if (mLocationClient != null && mLocationClient.isStarted()) {
            mLocationClient.stop();
        }
        super.onDestroy();
    }
}
