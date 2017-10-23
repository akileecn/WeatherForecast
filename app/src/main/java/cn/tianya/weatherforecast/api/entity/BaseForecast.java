package cn.tianya.weatherforecast.api.entity;

import com.google.common.base.Strings;

import org.mozilla.javascript.NativeObject;

import cn.tianya.weatherforecast.api.ApiUtils;

/**
 * 预报数据
 * Created by Administrator on 2017/10/20.
 */
public abstract class BaseForecast {
    private NativeObject data;

    public BaseForecast(NativeObject data) {
        this.data = data;
    }

    protected String getValue(String key) {
        return String.valueOf(data.get(key));
    }

    protected String getTranslatedWeather(String key) {
        String value = getValue(key);
        return Strings.isNullOrEmpty(value) ? value : ApiUtils.translateWeather(getValue(key));
    }

    /**
     * 日期
     */
    public abstract String getDate();

    /**
     * 天气
     */
    public String getWeather(){
        String night = getNightWeather();
        if(Strings.isNullOrEmpty(night)){
            return getDayWeather();
        }else{
            return getDayWeather() + "/" + getNightWeather();
        }
    }

    /**
     * 温度
     */
    public String getTemp() {
        return getMaxTemp() + "/" + getMinTemp() + "℃";
    }

    /**
     * 白天天气
     */
    protected abstract String getDayWeather();

    /**
     * 最高温度
     */
    protected abstract String getMaxTemp();

    /**
     * 晚上天气
     */
    protected abstract String getNightWeather();

    /**
     * 最低温度
     */
    protected abstract String getMinTemp();
}
