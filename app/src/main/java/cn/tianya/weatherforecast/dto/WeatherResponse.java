package cn.tianya.weatherforecast.dto;

import java.util.List;

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
    public static class WeatherData{
        private String shidu; // "68%"
        private String pm25; // 7
        private String pm10; // 19
        private String quality; // "优"
        private String wendu; // "30"
        private String ganmao; // "各类人群可自由活动"
        private Weather yesterday;
        private List<Weather> forecast;
    }

    public Boolean isSuccess(){
        return status == 200;
    }
}
