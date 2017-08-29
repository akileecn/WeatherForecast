package cn.tianya.weatherforecast.api.sojson;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;

import cn.tianya.weatherforecast.api.ApiCallBack;
import cn.tianya.weatherforecast.api.Result;
import cn.tianya.weatherforecast.api.WeatherDto;

/**
 * 请求www.sojson.com的接口
 * Created by Administrator on 2017/8/23.
 */
public class SojsonApiTask extends JsonHttpTask {
    private static final String API_WEATHER = "http://www.sojson.com/open/api/weather/json.shtml?city=";
    private ApiCallBack callBack;

    public SojsonApiTask(String city, ApiCallBack callBack) {
        super(API_WEATHER + city);
        this.callBack = callBack;
    }

    @Override
    protected void onPostExecute(Result<String> result) {
        Result<WeatherDto> refactorResult;
        if (result.getSuccess() && !Strings.isNullOrEmpty(result.getData())) {
            WeatherResponse response = JSON.parseObject(result.getData(), WeatherResponse.class);
            if (response.isSuccess()) {
                refactorResult = Result.success(response.createWeatherDto());
            } else {
                refactorResult = Result.fail(response.getMessage());
            }
        } else {
            refactorResult = Result.fail(result.getMessage());
        }
        callBack.execute(refactorResult);
    }

}
