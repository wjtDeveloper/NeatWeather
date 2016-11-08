package com.w4lr.neatweather.controller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.w4lr.neatweather.R;
import com.w4lr.neatweather.model.Constant;
import com.w4lr.neatweather.model.bean.DailyWeatherInfo;

import java.util.List;

/**
 * Created by w4lr on 2016/11/8.
 */

public class DailyWeatherAdapter extends BaseAdapter {

    private List<DailyWeatherInfo.ResultsBean.DailyBean> mDatas;



    private Context mContext;

    public DailyWeatherAdapter(Context context, List<DailyWeatherInfo.ResultsBean.DailyBean> datas) {
        mContext = context;
        mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public DailyWeatherInfo.ResultsBean.DailyBean getItem(int position) {
        return mDatas == null ? null : mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_daily_weather,null);
            holder = new ViewHolder();
            holder.tvDay = (TextView) convertView.findViewById(R.id.tv_daily_day);
            holder.tvNight = (TextView) convertView.findViewById(R.id.tv_daily_night);
            holder.tvDate = (TextView) convertView.findViewById(R.id.tv_daily_date);
            holder.tvHigh = (TextView) convertView.findViewById(R.id.tv_daily_high);
            holder.tvLow = (TextView) convertView.findViewById(R.id.tv_daily_low);
            holder.tvWindDir = (TextView) convertView.findViewById(R.id.tv_daily_wind_dir);
            holder.tvWindScale = (TextView) convertView.findViewById(R.id.tv_daily_wind_scale);
            holder.ivPhoto = (ImageView) convertView.findViewById(R.id.iv_daily_photo);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        DailyWeatherInfo.ResultsBean.DailyBean weatherInfo = getItem(position);
        holder.tvDay.setText("白天: " + weatherInfo.getText_day());
        holder.tvNight.setText("夜晚: " + weatherInfo.getText_night());
        holder.tvDate.setText("日期: " + weatherInfo.getDate());
        holder.tvHigh.setText("最高温度: " + weatherInfo.getHigh());
        holder.tvLow.setText("最低温度: " + weatherInfo.getLow());
        holder.tvWindDir.setText("风向: " + weatherInfo.getWind_direction());
        holder.tvWindScale.setText("风力: " + weatherInfo.getWind_scale());

        int dayCode = Integer.valueOf(weatherInfo.getCode_day());
        holder.ivPhoto.setImageResource(Constant.weatherIconIds[dayCode]);


        return convertView;
    }

    static class ViewHolder {
        private TextView tvDay;
        private TextView tvNight;
        private TextView tvDate;
        private TextView tvHigh;
        private TextView tvLow;
        private TextView tvWindDir;
        private TextView tvWindScale;
        private ImageView ivPhoto;
    }
}
