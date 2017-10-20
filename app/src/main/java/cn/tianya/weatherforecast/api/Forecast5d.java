package cn.tianya.weatherforecast.api;

import org.mozilla.javascript.NativeObject;

/**
 * 天气预报
 * Created by Administrator on 2017/8/29.
 */
public class Forecast5d implements Forecast {
    private NativeObject data;

    public Forecast5d(NativeObject data) {
        this.data = data;
    }

    @Override
    public String getDate() {
        return getValue("fj");
    }

    @Override
    public String getWeather() {
        return getDayWeather() + "/" + getNightWeather();
    }

    @Override
    public String getTemp() {
        return getMaxTemp() + "/" + getMinTemp() + "℃";
    }

    /**
     * 白天天气
     */
    private String getDayWeather() {
        return ApiConstants.translateWeather(getValue("fa"));
    }

    /**
     * 最高温度
     */
    private String getMaxTemp() {
        return getValue("fc");
    }

    /**
     * 晚上天气
     */
    private String getNightWeather() {
        return ApiConstants.translateWeather(getValue("fb"));
    }

    /**
     * 最低温度
     */
    private String getMinTemp() {
        return getValue("fd");
    }

    private String getValue(String key) {
        return String.valueOf(data.get(key));
    }
}
