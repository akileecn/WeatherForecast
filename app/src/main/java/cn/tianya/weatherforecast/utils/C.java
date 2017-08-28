package cn.tianya.weatherforecast.utils;

import cn.tianya.weatherforecast.entity.City;

/**
 * 常量
 * Created by Administrator on 2017/8/23.
 */

public class C {
    /**
     * SharedPreferences参数
     */
    public interface SP {
        String NAME = "WEATHER_FORECAST_SP"; // 名称
        String KEY_CITY_INIT = "CITY_INIT"; // 数据库是否初始化
        String KEY_CURRENT_CITY = "CURRENT_CITY"; // 当前城市
    }
    public static final City DEFAULT_CITY; // 默认城市
    static {
        DEFAULT_CITY = new City();
        DEFAULT_CITY.setProvince("浙江");
        DEFAULT_CITY.setCity("杭州");
        DEFAULT_CITY.setArea("杭州");
        DEFAULT_CITY.setAreaId("101210101");
    }
    private static final String API_WEATHER = "http://www.sojson.com/open/api/weather/json.shtml?city=";
    /**
     * 城市数据文件
     */
    private static final String CITY_DATA_FILE = "cityData.js";

    public static String getWeatherUrl(String city) {
        return API_WEATHER + city;
    }
}
