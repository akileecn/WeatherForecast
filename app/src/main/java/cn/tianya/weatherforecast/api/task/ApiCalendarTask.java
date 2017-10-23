package cn.tianya.weatherforecast.api.task;

import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import cn.tianya.weatherforecast.api.ApiCallBack;
import cn.tianya.weatherforecast.api.entity.BaseForecast;
import cn.tianya.weatherforecast.api.entity.Forecast40d;

import static cn.tianya.weatherforecast.api.ApiConstants.API_CALENDAR;

/**
 * 日历气象预报接口
 * Created by Administrator on 2017/10/20.
 */
class ApiCalendarTask extends BaseApiTask<List<BaseForecast>> {
    private int amount; //月份偏移量

    public ApiCalendarTask(String areaId, ApiCallBack<List<BaseForecast>> callBack, int amount) {
        super(areaId, callBack);
        this.amount = amount;
    }

    @Override
    public String createUrl() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, amount);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        String monthStr = month < 10 ? "0" + month : String.valueOf(month);
        return API_CALENDAR.replace("{id}", areaId).replace("{year}", String.valueOf(year))
                .replace("{month}", monthStr);
    }

    @Override
    public List<BaseForecast> createResult(String jsResponse) throws NoSuchMethodException, ScriptException {
        String jsAppend = ";function getForecasts(){return fc40;}";
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
        engine.eval(jsResponse + jsAppend);
        NativeArray forecasts = (NativeArray) ((Invocable) engine).invokeFunction("getForecasts");

        // 封装数据
        List<BaseForecast> forecastList = new ArrayList<>();
        for (Object forecast : forecasts) {
            forecastList.add(new Forecast40d((NativeObject) forecast));
        }
        return forecastList;
    }

}
