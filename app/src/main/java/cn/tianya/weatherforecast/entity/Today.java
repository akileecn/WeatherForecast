package cn.tianya.weatherforecast.entity;

import lombok.Data;

/**
 * 当天天气
 * Created by Administrator on 2017/8/25.
 */
@Data
public class Today {
    private String shidu; //"89%"
    private Integer pm25; //16
    private Integer pm10; //30
    private String quality; //"优"
    private String wendu; //"28"
    private String ganmao; //"各类人群可自由活动"

    private String weather;

    public String getWeather() {
        return weather + "℃";
    }
}
