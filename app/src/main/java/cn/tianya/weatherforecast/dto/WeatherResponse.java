package cn.tianya.weatherforecast.dto;

import java.util.ArrayList;
import java.util.List;

import cn.tianya.weatherforecast.entity.Today;
import cn.tianya.weatherforecast.entity.Weather;
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
    private WeatherData data;

    @Data
    public static class WeatherData {
        private String shidu; // "68%"
        private Integer pm25; // 7
        private Integer pm10; // 19
        private String quality; // "优"
        private String wendu; // "30"
        private String ganmao; // "各类人群可自由活动"
        private Weather yesterday;
        private List<Weather> forecast;
    }

    public Boolean isSuccess() {
        return status == 200;
    }

    /**
     * 当天天气
     */
    public Today getToday() {
        if (data != null) {
            Today today = new Today();
            today.setGanmao(data.ganmao);
            today.setPm10(data.pm10);
            today.setPm25(data.pm25);
            today.setQuality(data.quality);
            today.setShidu(data.shidu);
            today.setWendu(data.wendu);
            if (data.forecast != null && !data.forecast.isEmpty()) {
                today.setWeather(data.forecast.get(0).getType());
            }
            return today;
        }
        return null;
    }

    /**
     * 天气预报列表
     */
    public List<Weather> getWeatherList() {
        List<Weather> list = new ArrayList<>();
        if (data != null) {
            if (data.yesterday != null) {
                list.add(data.yesterday);
            }
            if (data.forecast != null && !data.forecast.isEmpty()) {
                list.addAll(data.getForecast());
            }
        }
        return list;
    }
}
