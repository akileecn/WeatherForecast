package cn.tianya.weatherforecast.api;

/**
 * 天气接口
 * Created by Administrator on 2017/8/29.
 */
public interface Forecast {
    /**
     * 日期
     */
    String getDate();

    /**
     * 白天天气
     */
    String getDayWeather();

    /**
     * 白天温度
     */
    String getDayTemperature();

    /**
     * 晚上天气
     */
    String getNightWeather();

    /**
     * 晚上天气
     */
    String getNightTemperature();
}
