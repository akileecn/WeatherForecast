package cn.tianya.weatherforecast.api.task;

import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;

import java.util.ArrayList;
import java.util.List;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import cn.tianya.weatherforecast.api.ApiCallBack;
import cn.tianya.weatherforecast.api.WeatherDto;
import cn.tianya.weatherforecast.api.entity.BaseForecast;
import cn.tianya.weatherforecast.api.entity.Forecast5d;
import cn.tianya.weatherforecast.api.entity.Today;

import static cn.tianya.weatherforecast.api.ApiConstants.API_INDEX;

/**
 * 首页气象预报接口
 * Created by Administrator on 2017/8/29.
 */
public class ApiIndexTask extends BaseApiTask<WeatherDto> {

    public ApiIndexTask(String areaId, ApiCallBack<WeatherDto> callBack) {
        super(areaId, callBack);
    }

    @Override
    public String createUrl() {
        return API_INDEX.replace("{id}", areaId);
    }

    @Override
    public WeatherDto createResult(String jsResponse) throws NoSuchMethodException, ScriptException {
        String jsAppend = ";function getToday(){return dataSK;}function getForecasts(){return fc.f;}";
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
        engine.eval(jsResponse + jsAppend);
        NativeObject today = (NativeObject) ((Invocable) engine).invokeFunction("getToday");
        NativeArray forecasts = (NativeArray) ((Invocable) engine).invokeFunction("getForecasts");

        // 封装数据
        WeatherDto dto = new WeatherDto();
        dto.setToday(new Today(today));
        List<BaseForecast> forecastList = new ArrayList<>();
        for (Object forecast : forecasts) {
            forecastList.add(new Forecast5d((NativeObject) forecast));
        }
        dto.setForecastList(forecastList);
        return dto;
    }
}
