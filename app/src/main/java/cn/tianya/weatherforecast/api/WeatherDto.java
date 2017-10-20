package cn.tianya.weatherforecast.api;

import java.util.List;

/**
 * 天气信息请求响应
 * Created by Administrator on 2017/8/29.
 */
public class WeatherDto {
    private Today today; // 当天天气
    private List<Forecast5d> forecastList; // 天气预报集合

    public Today getToday() {
        return today;
    }

    public void setToday(Today today) {
        this.today = today;
    }

    public List<Forecast5d> getForecastList() {
        return forecastList;
    }

    public void setForecastList(List<Forecast5d> forecastList) {
        this.forecastList = forecastList;
    }
}
