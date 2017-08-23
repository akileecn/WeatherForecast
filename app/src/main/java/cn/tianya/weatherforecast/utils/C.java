package cn.tianya.weatherforecast.utils;

/**
 * 常量
 * Created by Administrator on 2017/8/23.
 */

public class C {
    private static final String API_WEATHER = "http://www.sojson.com/open/api/weather/json.shtml?city=";

    public static String getWeatherUrl(String city){
        return API_WEATHER + city;
    }
}
