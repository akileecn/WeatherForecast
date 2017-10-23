package cn.tianya.weatherforecast.api.entity;

import com.google.common.base.Strings;

import org.mozilla.javascript.NativeObject;

/**
 * 当天天气接口
 * Created by Administrator on 2017/8/29.
 */
public class Today {
    private NativeObject data;

    public Today(NativeObject data) {
        this.data = data;
    }

    /**
     * 天气
     */
    public String getWeather() {
        return String.valueOf(data.get("weather"));
    }

    /**
     * 温度
     */
    public String getTemperature() {
        return String.valueOf(data.get("temp")) + "℃";
    }

    /**
     * 空气质量
     */
    public String getAqi() {
        String rawAqi = String.valueOf(data.get("aqi"));
        if (Strings.isNullOrEmpty(rawAqi)) {
            return "空气质量未知";
        } else {
            int aqi = Integer.parseInt(rawAqi);
            return "空气质量 " + aqi + createAqiDesc(aqi);
        }
    }

    /**
     * 生成空气治疗描述
     */
    private String createAqiDesc(int aqi) {
        if (aqi < 50) {
            return "优";
        } else if (aqi < 100) {
            return "良";
        } else if (aqi < 150) {
            return "轻度污染";
        } else if (aqi < 200) {
            return "中度污染";
        } else if (aqi < 300) {
            return "重度污染";
        } else {
            return "严重污染";
        }
    }

    /**
     * 风向风力
     */
    public String getWind() {
        return String.valueOf(data.get("WD")) + "" + String.valueOf(data.get("WS"));
    }

    /**
     * 湿度
     */
    public String getHumidity() {
        return "湿度" + String.valueOf(data.get("sd"));
    }
}
