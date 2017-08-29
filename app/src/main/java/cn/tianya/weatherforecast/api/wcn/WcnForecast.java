package cn.tianya.weatherforecast.api.wcn;

import org.mozilla.javascript.NativeObject;

import cn.tianya.weatherforecast.api.Forecast;

/**
 * Created by Administrator on 2017/8/29.
 */
class WcnForecast implements Forecast {
    private NativeObject data;

    WcnForecast(NativeObject data) {
        this.data = data;
    }

    @Override
    public String getDate() {
        return getValue("fj");
    }

    @Override
    public String getDayWeather() {
        return WcnConstants.translateWeather(getValue("fa"));
    }

    @Override
    public String getDayTemperature() {
        return getValue("fc") + "℃";
    }

    @Override
    public String getNightWeather() {
        return WcnConstants.translateWeather(getValue("fb"));
    }

    @Override
    public String getNightTemperature() {
        return getValue("fd") + "℃";
    }

    private String getValue(String key) {
        return String.valueOf(data.get(key));
    }
}
