package cn.tianya.weatherforecast.api.task;

import android.os.AsyncTask;

import java.io.IOException;

import javax.script.ScriptException;

import cn.tianya.weatherforecast.api.ApiCallBack;
import cn.tianya.weatherforecast.api.ApiConstants;
import cn.tianya.weatherforecast.api.Result;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * api请求基类
 *
 * @param <T> 结果
 */
abstract class BaseApiTask<T> extends AsyncTask<Void, Void, Result<T>> {
    private static final OkHttpClient client;
    private ApiCallBack<T> callBack;
    protected String areaId;

    static {
        client = new OkHttpClient.Builder().build();
    }

    protected BaseApiTask(String areaId, ApiCallBack<T> callBack) {
        this.areaId = areaId;
        this.callBack = callBack;
    }

    @Override
    protected Result<T> doInBackground(Void... voids) {
        Request request = new Request.Builder()
                .addHeader("Referer", ApiConstants.API_REFERER)
                .url(createUrl()).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                // 解析js
                String jsResponse = response.body().string();
                return Result.success(createResult(jsResponse));
            }
        } catch (IOException | NoSuchMethodException | ScriptException e) {
            e.printStackTrace();
        }
        return Result.fail();
    }

    @Override
    protected void onPostExecute(Result<T> result) {
        if (callBack != null) {
            callBack.execute(result);
        }
    }

    /**
     * 创建api请求url
     */
    abstract String createUrl();

    /**
     * 处理请求结果
     *
     * @throws NoSuchMethodException 解析js时可能抛出的异常
     * @throws ScriptException       解析js时可能抛出的异常
     */
    abstract T createResult(String jsResponse) throws NoSuchMethodException, ScriptException;
}
