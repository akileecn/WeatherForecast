package cn.tianya.weatherforecast.api;

import java.util.List;

import cn.tianya.weatherforecast.api.entity.BaseForecast;
import cn.tianya.weatherforecast.api.task.Api30dTask;
import cn.tianya.weatherforecast.api.task.ApiIndexTask;
import cn.tianya.weatherforecast.entity.City;

import static cn.tianya.weatherforecast.api.ApiConstants.WEATHER;

/**
 * api帮助类
 * Created by Administrator on 2017/8/29.
 */
public class ApiUtils {

    /**
     * 获取首页数据
     */
    public static void getIndexData(City city, ApiCallBack<WeatherDto> callBack) {
        new ApiIndexTask(city.getAreaId(), callBack).execute();
    }

    /**
     * 获取30天数据
     */
    public static void get30dData(City city, ApiCallBack<List<BaseForecast>> callBack) {
        new Api30dTask(city.getAreaId(), callBack).execute();
    }

    /**
     * 翻译天气
     */
    public static String translateWeather(String key) {
        return WEATHER.get(key);
    }

}
