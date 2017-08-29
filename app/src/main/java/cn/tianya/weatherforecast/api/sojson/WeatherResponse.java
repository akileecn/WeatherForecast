package cn.tianya.weatherforecast.api.sojson;

import java.util.ArrayList;
import java.util.List;

import cn.tianya.weatherforecast.api.Forecast;
import cn.tianya.weatherforecast.api.WeatherDto;
import lombok.Data;

/**
 * api响应
 * Created by Administrator on 2017/8/23.
 */
@Data
public class WeatherResponse {
    private String message; // "Success !"
    private Integer status; // 200
    private String city; // "宁波"
    private Integer count; // 1
    private ForecastData data;

    public Boolean isSuccess() {
        return status == 200;
    }

    /**
     * 当天天气
     */
    private SojsonToday getToday() {
        SojsonToday today = new SojsonToday();
        today.setGanmao(data.ganmao);
        today.setPm10(data.pm10);
        today.setPm25(data.pm25);
        today.setQuality(data.quality);
        today.setShidu(data.shidu);
        today.setWendu(data.wendu);
        if (!data.forecast.isEmpty()) {
            today.setWeather(data.forecast.get(0).getType());
        }
        return today;
    }

    /**
     * 天气预报列表
     */
    private List<Forecast> getWeatherList() {
        List<Forecast> list = new ArrayList<>();
        list.add(data.yesterday);
        if (!data.forecast.isEmpty()) {
            list.addAll(data.getForecast());
        }
        return list;
    }

    @Data
    public static class ForecastData {
        private String shidu; // "68%"
        private Integer pm25; // 7
        private Integer pm10; // 19
        private String quality; // "优"
        private String wendu; // "30"
        private String ganmao; // "各类人群可自由活动"
        private SojsonWeather yesterday;
        private List<SojsonWeather> forecast;
    }

    public WeatherDto createWeatherDto() {
        WeatherDto dto = new WeatherDto();
        dto.setToday(getToday());
        dto.setForecastList(getWeatherList());
        return dto;
    }
}
