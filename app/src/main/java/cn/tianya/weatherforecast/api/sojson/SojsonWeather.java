package cn.tianya.weatherforecast.api.sojson;

import cn.tianya.weatherforecast.api.Forecast;
import lombok.Data;

/**
 * 天气
 * Created by Administrator on 2017/8/23.
 */
@Data
public class SojsonWeather implements Forecast{
    private String date; // "22日星期二"
    private String sunrise; // "05:24"
    private String high; //	"高温 33.0℃"
    private String low; // "低温 27.0℃"
    private String sunset; // "18:28"
    private Integer aqi; // 18
    private String fx; // "东南风"
    private String fl; // "3-4级"
    private String type; //	"阵雨"
    private String notice; //	"愿雨后清新的空气给您带来好心情！"

    @Override
    public String getDayWeather() {
        return type;
    }

    @Override
    public String getDayTemperature() {
        return high;
    }

    @Override
    public String getNightWeather() {
        return "";
    }

    @Override
    public String getNightTemperature() {
        return low;
    }
}