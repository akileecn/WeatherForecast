package cn.tianya.weatherforecast.api.wcn;

import org.mozilla.javascript.NativeObject;

import cn.tianya.weatherforecast.api.Today;

/**
 * Created by Administrator on 2017/8/29.
 */
class WcnToday implements Today {
    private NativeObject data;

    WcnToday(NativeObject data){
        this.data = data;
    }

    @Override
    public String getWeather() {
        return String.valueOf(data.get("weather"));
    }

    @Override
    public String getTemperature() {
        return String.valueOf(data.get("temp")) + "℃";
    }
}
