package cn.tianya.weatherforecast.api;

import org.mozilla.javascript.NativeObject;

/**
 * 预测天气40天
 * Created by Administrator on 2017/10/20.
 */
public class Forecast40d implements Forecast {
    private NativeObject data;

    public Forecast40d(NativeObject data) {
        this.data = data;
    }

    @Override
    public String getDate() {
        return getValue("date") + "(" + getValue("wk") + ")";
    }

    @Override
    public String getWeather() {
        return getDayWeather() + "/" + getNightWeather();
    }

    @Override
    public String getTemp() {
        return getMaxTemp() + "/" + getMinTemp() + "℃";
    }

    private String getDayWeather() {
        return ApiConstants.translateWeather(getValue("c1"));
    }

    private String getMinTemp() {
        return getValue("min");
    }

    private String getNightWeather() {
        return ApiConstants.translateWeather(getValue("c2"));
    }

    private String getMaxTemp() {
        return getValue("max");
    }

    private String getValue(String key) {
        return String.valueOf(data.get(key));
    }
}
