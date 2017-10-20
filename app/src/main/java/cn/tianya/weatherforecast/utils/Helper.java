package cn.tianya.weatherforecast.utils;

import android.content.SharedPreferences;

import com.alibaba.fastjson.JSON;

import cn.tianya.weatherforecast.entity.City;

import static cn.tianya.weatherforecast.Constants.SP.KEY_CURRENT_CITY;

/**
 * 帮助类
 * Created by Administrator on 2017/8/29.
 */
public class Helper {
    /**
     * 获取默认城市
     */
    public static City getDefaultCity(SharedPreferences sp) {
        String json = sp.getString(KEY_CURRENT_CITY, "");
        if (json.isEmpty()) {
            return City.getDefaultCity();
        } else {
            return JSON.parseObject(json, City.class);
        }
    }

    /**
     * 设置默认城市
     */
    public static void setDefaultCity(SharedPreferences sp, City city) {
        sp.edit().putString(KEY_CURRENT_CITY, JSON.toJSONString(city)).apply();
    }
}
