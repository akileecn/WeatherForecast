package cn.tianya.weatherforecast;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.io.CharStreams;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.tianya.weatherforecast.entity.City;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("cn.tianya.weatherforecast", appContext.getPackageName());
    }

    @Test
    public void cityTest() throws IOException {
        Context appContext = InstrumentationRegistry.getTargetContext();
        InputStream is = appContext.getAssets().open("cityData.js");
        String jsonStr = CharStreams.toString(new InputStreamReader(is, StandardCharsets.UTF_8));
        JSONObject json = JSON.parseObject(jsonStr);
        List<City> list = new ArrayList<>();
        // 省
        for (Map.Entry<String, Object> province : json.entrySet()) {
            String provinceName = province.getKey();
            // 市
            for (Map.Entry<String, Object> city : ((JSONObject) province.getValue()).entrySet()) {
                String cityName = city.getKey();
                // 地区
                for (Map.Entry<String, Object> area : ((JSONObject) city.getValue()).entrySet()) {
                    String areaName = area.getKey();
                    String areaId = ((JSONObject) area.getValue()).getString("AREAID");
                    City item = new City();
                    item.setArea(areaName);
                    item.setAreaId(areaId);
                    item.setCity(cityName);
                    item.setProvince(provinceName);
                    list.add(item);
                }
            }
        }
        System.err.println(JSON.toJSONString(list));
    }

}
