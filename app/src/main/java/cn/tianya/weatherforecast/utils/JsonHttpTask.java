package cn.tianya.weatherforecast.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.google.common.base.Throwables;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/8/23.
 */
public class JsonHttpTask extends AsyncTask<Void, Void, Result<String>> {
    private String url;
    private static final OkHttpClient client;
    private static final String TAG = "JsonHttpTask";

    static {
        client = new OkHttpClient();
    }

    public JsonHttpTask(String url) {
        this.url = url;
    }

    @Override
    protected Result<String> doInBackground(Void... voids) {
        Request request = new Request.Builder().url(url).build();
        Result<String> result;
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                result = Result.success(response.body().string());
            } else {
                result = Result.fail(response.message());
            }
        } catch (IOException e) {
            Log.e(TAG, "http请求失败", e);
            result = Result.fail(Throwables.getStackTraceAsString(e));
        }
        return result;
    }

    @Override
    protected void onPostExecute(Result<String> stringResult) {
    }
}
