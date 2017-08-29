package cn.tianya.weatherforecast.api;

/**
 * 当天天气接口
 * Created by Administrator on 2017/8/29.
 */
public interface Today {
    /**
     * 天气
     */
    String getWeather();

    /**
     * 温度
     */
    String getTemperature();
}
