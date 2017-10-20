package cn.tianya.weatherforecast.api;

import android.os.AsyncTask;

import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 40天天气预报接口
 * Created by Administrator on 2017/10/20.
 */
public class ApiCalendarTask extends AsyncTask<Void, Void, Result<List<Forecast40d>>> {
    private static final OkHttpClient client;
    private ApiCallBack<List<Forecast40d>> callBack;
    private String areaId;

    static {
        client = new OkHttpClient.Builder().build();
    }

    public ApiCalendarTask(String areaId, ApiCallBack<List<Forecast40d>> callBack) {
        this.areaId = areaId;
        this.callBack = callBack;
    }

    @Override
    protected Result<List<Forecast40d>> doInBackground(Void... voids) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        Request request = new Request.Builder()
                .addHeader("Referer", ApiConstants.API_REFERER)
                .url(ApiConstants.getApiCalendar(areaId, year, month)).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                // 解析js
                String jsResponse = response.body().string();
                String jsAppend = ";function getForecasts(){return fc40;}";
                ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
                engine.eval(jsResponse + jsAppend);
                NativeArray forecasts = (NativeArray) ((Invocable) engine).invokeFunction("getForecasts");

                // 封装数据
                List<Forecast40d> forecastList = new ArrayList<>();
                for (Object forecast : forecasts) {
                    forecastList.add(new Forecast40d((NativeObject) forecast));
                }
                return Result.success(forecastList);
            }
        } catch (IOException | NoSuchMethodException | ScriptException e) {
            e.printStackTrace();
        }
        return Result.fail();
    }

    @Override
    protected void onPostExecute(Result<List<Forecast40d>> result) {
        if (callBack != null) {
            callBack.execute(result);
        }
    }
}
