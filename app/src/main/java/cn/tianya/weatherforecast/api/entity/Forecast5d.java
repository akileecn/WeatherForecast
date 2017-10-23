package cn.tianya.weatherforecast.api.entity;

import org.mozilla.javascript.NativeObject;

/**
 * 天气预报
 * Created by Administrator on 2017/8/29.
 */
public class Forecast5d extends BaseForecast {

    public Forecast5d(NativeObject data) {
        super(data);
    }

    @Override
    public String getDate() {
        return getValue("fj");
    }

    @Override
    protected String getDayWeather() {
        return getTranslatedWeather("fa");
    }

    @Override
    protected String getMaxTemp() {
        return getValue("fc");
    }

    @Override
    protected String getNightWeather() {
        return getTranslatedWeather("fb");
    }

    @Override
    protected String getMinTemp() {
        return getValue("fd");
    }

}
