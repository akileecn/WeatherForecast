package cn.tianya.weatherforecast.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.alibaba.fastjson.JSON;

import cn.tianya.weatherforecast.entity.City;
import okhttp3.Cache;

import static cn.tianya.weatherforecast.utils.Constants.SP.KEY_CURRENT_CITY;

/**
 * 帮助类
 * Created by Administrator on 2017/8/29.
 */
public class Helper {
    private static Cache sCache;
    private static SharedPreferences sSp;

    /**
     * 初始化
     */
    public static void init(Context context) {
        sSp = PreferenceManager.getDefaultSharedPreferences(context);
        // 申请10MB缓存
        sCache = new Cache(context.getCacheDir(), 100 * 1024 * 1024);
    }

    /**
     * 获取默认城市
     */
    public static City getDefaultCity() {
        String json = sSp.getString(KEY_CURRENT_CITY, "");
        if (json.isEmpty()) {
            return City.getDefaultCity();
        } else {
            return JSON.parseObject(json, City.class);
        }
    }

    /**
     * 设置默认城市
     */
    public static void setDefaultCity(City city) {
        sSp.edit().putString(KEY_CURRENT_CITY, JSON.toJSONString(city)).apply();
    }

    /**
     * 获取缓存
     */
    public static Cache getCache() {
        return sCache;
    }
}
