package cn.tianya.weatherforecast.api;

import android.os.AsyncTask;

import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 请求www.weather.com.cn的接口
 * Created by Administrator on 2017/8/29.
 */
public class ApiTask extends AsyncTask<Void, Void, Result<WeatherDto>> {
    private static final OkHttpClient client;
    private ApiCallBack<WeatherDto> callBack;
    private String areaId;

    static {
        client = new OkHttpClient.Builder().build();
    }

    public ApiTask(String areaId, ApiCallBack<WeatherDto> callBack) {
        this.areaId = areaId;
        this.callBack = callBack;
    }

    @Override
    protected Result<WeatherDto> doInBackground(Void... voids) {
        Request request = new Request.Builder()
                .addHeader("Referer", ApiConstants.API_REFERER)
                .url(ApiConstants.getApiIndex(areaId)).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                // 解析js
                String jsResponse = response.body().string();
                String jsAppend = ";function getToday(){return dataSK;}function getForecasts(){return fc.f;}";
                ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
                engine.eval(jsResponse + jsAppend);
                NativeObject today = (NativeObject) ((Invocable) engine).invokeFunction("getToday");
                NativeArray forecasts = (NativeArray) ((Invocable) engine).invokeFunction("getForecasts");

                // 封装数据
                WeatherDto dto = new WeatherDto();
                dto.setToday(new Today(today));
                List<Forecast5d> forecastList = new ArrayList<>();
                for (Object forecast : forecasts) {
                    forecastList.add(new Forecast5d((NativeObject) forecast));
                }
                dto.setForecastList(forecastList);
                return Result.success(dto);
            }
        } catch (IOException | NoSuchMethodException | ScriptException e) {
            e.printStackTrace();
        }
        return Result.fail();
    }

    @Override
    protected void onPostExecute(Result<WeatherDto> weatherDtoResult) {
        if (callBack != null) {
            callBack.execute(weatherDtoResult);
        }
    }
}
