package cn.tianya.weatherforecast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.io.CharStreams;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import cn.tianya.weatherforecast.entity.City;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void apiTest() throws IOException {
        String referer = "http://www.weather.com.cn/";
        String api = "http://d1.weather.com.cn/weather_index/101210401.html";

        Request request = new Request.Builder().addHeader("Referer", referer).url(api).build();
        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();
        if(response.isSuccessful()){
            System.err.println(response.body().string());
        }

    }

    @Test
    public void fileTest() throws IOException{
        InputStream is = new FileInputStream("E:\\Software\\Android\\StudioProjects\\WeatherForecast\\app\\src\\main\\assets\\cityData.js");
        String jsonStr = CharStreams.toString(new InputStreamReader(is, StandardCharsets.UTF_8));
        JSONObject json = JSON.parseObject(jsonStr);
        List<City> list = new ArrayList<>();
        // 省
        json.forEach((pk, pv) -> {
            // 市
            ((JSONObject) pv).forEach((ck, cv) -> {
                // 地区
                ((JSONObject) cv).forEach((ak, av) -> {
                    String areaId = ((JSONObject) av).get("AREAID").toString();
                    City item = new City();
                    item.setArea(ak);
                    item.setAreaId(areaId);
                    item.setCity(ck);
                    item.setProvince(pk);
                    list.add(item);
                });
            });
        });
        System.err.println(JSON.toJSONString(list));
    }
}