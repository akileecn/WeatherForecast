package cn.tianya.weatherforecast.api;

import java.util.List;

import cn.tianya.weatherforecast.api.entity.BaseForecast;
import cn.tianya.weatherforecast.api.entity.Today;
import lombok.Data;

/**
 * 天气信息请求响应
 * Created by Administrator on 2017/8/29.
 */
@Data
public class WeatherDto {
    private Today today; // 当天天气
    private List<BaseForecast> forecastList; // 天气预报集合

}