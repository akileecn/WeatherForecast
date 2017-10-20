package cn.tianya.weatherforecast.api;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * API常量
 * Created by Administrator on 2017/8/29.
 */
public class ApiConstants {
    private static final Map<String, String> WEATHER;
    private static final String API_INDEX = "http://d1.weather.com.cn/weather_index/{id}.html";
    private static final String API_CALENDAR = "http://d1.weather.com.cn/calendar_new/{year}/{id}_{year}{month}.html";
    static final String API_REFERER = "http://www.weather.com.cn/";

    static {
        WEATHER = new ImmutableMap.Builder<String, String>()
                .put("00", "晴")
                .put("01", "多云")
                .put("02", "阴")
                .put("03", "阵雨")
                .put("04", "雷阵雨")
                .put("05", "雷阵雨伴有冰雹")
                .put("06", "雨夹雪")
                .put("07", "小雨")
                .put("08", "中雨")
                .put("09", "大雨")
                .put("10", "暴雨")
                .put("11", "大暴雨")
                .put("12", "特大暴雨")
                .put("13", "阵雪")
                .put("14", "小雪")
                .put("15", "中雪")
                .put("16", "大雪")
                .put("17", "暴雪")
                .put("18", "雾")
                .put("19", "冻雨")
                .put("20", "沙尘暴")
                .put("21", "小到中雨")
                .put("22", "中到大雨")
                .put("23", "大到暴雨")
                .put("24", "暴雨到大暴雨")
                .put("25", "大暴雨到特大暴雨")
                .put("26", "小到中雪")
                .put("27", "中到大雪")
                .put("28", "大到暴雪")
                .put("29", "浮尘")
                .put("30", "扬沙")
                .put("31", "强沙尘暴")
                .put("53", "霾")
                .put("99", "无")
                .build();
    }

    static String translateWeather(String key) {
        return WEATHER.get(key);
    }

    static String getApiIndex(String id) {
        return API_INDEX.replace("{id}", id);
    }

    static String getApiCalendar(String id, int year, int month) {
        String monthStr = month < 10 ? "0" + month : String.valueOf(month);
        return API_CALENDAR.replace("{id}", id).replace("{year}", String.valueOf(year))
                .replace("{month}", monthStr);
    }
}
