package cn.tianya.weatherforecast.api;

/**
 * 回调接口
 * Created by Administrator on 2017/8/29.
 */
public interface ApiCallBack {
    void execute(Result<WeatherDto> result);
}
