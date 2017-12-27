package cn.tianya.weatherforecast.api.entity;

import org.mozilla.javascript.NativeObject;

/**
 * 预测天气40天
 * Created by Administrator on 2017/10/20.
 */
public class Forecast40d extends BaseForecast {

    public Forecast40d(NativeObject data) {
        super(data);
    }

    /**
     * 获得原生date数据
     */
    public String getRawDate() {
        return getValue("date");
    }

    @Override
    public String getDate() {
        return getValue("date") + "(" + getValue("wk") + ")";
    }

    @Override
    protected String getDayWeather() {
        return getTranslatedWeather("c1");
    }

    @Override
    protected String getMinTemp() {
        return getValue("min");
    }

    @Override
    public String getRawWeather() {
        return getValue("c1");
    }

    @Override
    protected String getNightWeather() {
        return getTranslatedWeather("c2");
    }

    @Override
    protected String getMaxTemp() {
        return getValue("max");
    }

}
