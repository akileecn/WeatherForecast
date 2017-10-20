package cn.tianya.weatherforecast.api;

/**
 * 预报数据
 * Created by Administrator on 2017/10/20.
 */
public interface Forecast {
    /**
     * 日期
     */
    String getDate();

    /**
     * 天气
     */
    String getWeather();

    /**
     * 温度
     */
    String getTemp();
}
