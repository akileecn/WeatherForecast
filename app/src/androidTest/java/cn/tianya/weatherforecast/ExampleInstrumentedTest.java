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

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

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

    @Test
    public void jsTest() throws ScriptException, NoSuchMethodException {
        String jsStr = "var cityDZ ={\"weatherinfo\":{\"city\":\"101210401\",\"cityname\":\"宁波\",\"temp\":\"35℃\",\"tempn\":\"25℃\",\"weather\":\"多云转雷阵雨\",\"wd\":\"东南风转东北风\",\"ws\":\"微风\",\"weathercode\":\"d1\",\"weathercoden\":\"n4\",\"fctime\":\"20170829080000\"}};var alarmDZ ={\"w\":[]};var dataSK = {\"nameen\":\"ningbo\",\"cityname\":\"宁波\",\"city\":\"101210401\",\"temp\":\"29\",\"tempf\":\"84\",\"WD\":\"南风\",\"wde\":\"S \",\"WS\":\"1级\",\"wse\":\"<12km/h\",\"SD\":\"77%\",\"time\":\"07:55\",\"weather\":\"晴\",\"weathere\":\"Sunny\",\"weathercode\":\"d00\",\"qy\":\"1012\",\"njd\":\"35km\",\"sd\":\"77%\",\"rain\":\"0\",\"rain24h\":\"0\",\"aqi\":\"22\",\"limitnumber\":\"\",\"aqi_pm25\":\"22\",\"date\":\"08月29日(星期二)\"};var dataZS={\"zs\":{\"date\":\"2017082908\",\"ac_name\":\"空调开启指数\",\"ac_hint\":\"部分时间开启\",\"ac_des_s\":\"天气燥热需适当开启空调，以免中暑。\",\"ag_name\":\"过敏指数\",\"ag_hint\":\"易发\",\"ag_des_s\":\"应减少外出，外出需采取防护措施。\",\"cl_name\":\"晨练指数\",\"cl_hint\":\"较适宜\",\"cl_des_s\":\"注意选择通风凉爽的地点进行晨练。\",\"co_name\":\"舒适度指数\",\"co_hint\":\"很不舒适\",\"co_des_s\":\"烈日炎炎的晴天，很热。\",\"ct_name\":\"穿衣指数\",\"ct_hint\":\"炎热\",\"ct_des_s\":\"建议穿短衫、短裤等清凉夏季服装。\",\"dy_name\":\"钓鱼指数\",\"dy_hint\":\"不宜\",\"dy_des_s\":\"天气太热，不适合垂钓。\",\"fs_name\":\"防晒指数\",\"fs_hint\":\"中等\",\"fs_des_s\":\"适合涂擦SPF大于15，PA+护肤品。\",\"gj_name\":\"逛街指数\",\"gj_hint\":\"较不宜\",\"gj_des_s\":\"天气炎热，避免长时间暴晒，小心中暑。\",\"gm_name\":\"感冒指数\",\"gm_hint\":\"少发\",\"gm_des_s\":\"感冒机率较低，避免长期处于空调屋中。\",\"hc_name\":\"划船指数\",\"hc_hint\":\"适宜\",\"hc_des_s\":\"天气较好，适宜划船及嬉玩水上运动。\",\"jt_name\":\"交通指数\",\"jt_hint\":\"良好\",\"jt_des_s\":\"气象条件良好，车辆可以正常行驶。\",\"lk_name\":\"路况指数\",\"lk_hint\":\"干燥\",\"lk_des_s\":\"天气略热，路况较好，定期让车辆休息。\",\"ls_name\":\"晾晒指数\",\"ls_hint\":\"适宜\",\"ls_des_s\":\"天气不错，抓紧时机让衣物晒太阳吧。\",\"mf_name\":\"美发指数\",\"mf_hint\":\"一般\",\"mf_des_s\":\"注意头发清洁，选用防晒型护发品。\",\"nl_name\":\"夜生活指数\",\"nl_hint\":\"较适宜\",\"nl_des_s\":\"只要您稍作准备就可以放心外出。\",\"pj_name\":\"啤酒指数\",\"pj_hint\":\"适宜\",\"pj_des_s\":\"炎热干燥，可饮用清凉的啤酒，不要贪杯。\",\"pk_name\":\"放风筝指数\",\"pk_hint\":\"不宜\",\"pk_des_s\":\"天气酷热，不适宜放风筝。\",\"pl_name\":\"空气污染扩散条件指数\",\"pl_hint\":\"中\",\"pl_des_s\":\"易感人群应适当减少室外活动。\",\"pp_name\":\"化妆指数\",\"pp_hint\":\"防脱水防晒\",\"pp_des_s\":\"请选用防脱水防晒化妆品。\",\"tr_name\":\"旅游指数\",\"tr_hint\":\"一般\",\"tr_des_s\":\"天气热，外出请注意防暑降温和防晒。\",\"uv_name\":\"紫外线强度指数\",\"uv_hint\":\"中等\",\"uv_des_s\":\"涂擦SPF大于15、PA+防晒护肤品。\",\"wc_name\":\"风寒指数\",\"wc_hint\":\"无\",\"wc_des_s\":\"暂缺\",\"xc_name\":\"洗车指数\",\"xc_hint\":\"不宜\",\"xc_des_s\":\"有雨，雨水和泥水会弄脏爱车。\",\"xq_name\":\"心情指数\",\"xq_hint\":\"较差\",\"xq_des_s\":\"气温较高，会让人觉得有些烦躁。\",\"yd_name\":\"运动指数\",\"yd_hint\":\"较不宜\",\"yd_des_s\":\"请适当减少运动时间并降低运动强度。\",\"yh_name\":\"约会指数\",\"yh_hint\":\"不适宜\",\"yh_des_s\":\"建议在室内约会，免去天气的骚扰。\",\"ys_name\":\"雨伞指数\",\"ys_hint\":\"不带伞\",\"ys_des_s\":\"天气较好，不用带雨伞。\",\"zs_name\":\"中暑指数\",\"zs_hint\":\"较易\",\"zs_des_s\":\"气温较高，避免长时间在高温中工作。\"},\"cn\":\"宁波\"};var fc={\"f\":[{\"fa\":\"01\",\"fb\":\"04\",\"fc\":\"35\",\"fd\":\"25\",\"fe\":\"东南风\",\"ff\":\"东北风\",\"fg\":\"<3级\",\"fh\":\"<3级\",\"fi\":\"8\\/29\",\"fj\":\"今天\"},{\"fa\":\"04\",\"fb\":\"01\",\"fc\":\"33\",\"fd\":\"25\",\"fe\":\"东北风\",\"ff\":\"东北风\",\"fg\":\"3-4级\",\"fh\":\"<3级\",\"fi\":\"8\\/30\",\"fj\":\"周三\"},{\"fa\":\"01\",\"fb\":\"04\",\"fc\":\"31\",\"fd\":\"24\",\"fe\":\"东北风\",\"ff\":\"东北风\",\"fg\":\"<3级\",\"fh\":\"3-4级\",\"fi\":\"8\\/31\",\"fj\":\"周四\"},{\"fa\":\"04\",\"fb\":\"01\",\"fc\":\"30\",\"fd\":\"24\",\"fe\":\"东北风\",\"ff\":\"东北风\",\"fg\":\"3-4级\",\"fh\":\"3-4级\",\"fi\":\"9\\/1\",\"fj\":\"周五\"},{\"fa\":\"04\",\"fb\":\"01\",\"fc\":\"31\",\"fd\":\"24\",\"fe\":\"东风\",\"ff\":\"东风\",\"fg\":\"3-4级\",\"fh\":\"3-4级\",\"fi\":\"9\\/2\",\"fj\":\"周六\"}]}";
        String jsAppend = ";function getToday(){return dataSK;}function getForecast(){return fc.f;}";
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
        engine.eval(jsStr + jsAppend);
        Object today = ((Invocable) engine).invokeFunction("getForecast");
        System.err.println(JSON.toJSONString(today));
    }
}
