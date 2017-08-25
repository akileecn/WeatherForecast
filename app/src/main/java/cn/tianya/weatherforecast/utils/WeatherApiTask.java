package cn.tianya.weatherforecast.utils;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;

import cn.tianya.weatherforecast.dto.WeatherResponse;

/**
 * 天气预报api请求
 * Created by Administrator on 2017/8/23.
 */
public class WeatherApiTask extends JsonHttpTask {
    private CallBack callBack;

    public WeatherApiTask(String city, CallBack callBack) {
        super(C.getWeatherUrl(city));
        this.callBack = callBack;
    }

    @Override
    protected void onPostExecute(Result<String> result) {
        Result<WeatherResponse> refactorResult;
        if (result.getSuccess() && !Strings.isNullOrEmpty(result.getData())) {
            WeatherResponse response = JSON.parseObject(result.getData(), WeatherResponse.class);
            if (response.isSuccess()) {
                refactorResult = Result.success(response);
            } else {
                refactorResult = Result.fail(response.getMessage());
            }
        } else {
            refactorResult = Result.fail(result.getMessage());
        }
        callBack.execute(refactorResult);
    }

    public interface CallBack {
        void execute(Result<WeatherResponse> result);
    }

}
