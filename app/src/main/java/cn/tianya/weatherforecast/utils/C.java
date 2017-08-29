package cn.tianya.weatherforecast.utils;

import com.alibaba.fastjson.JSON;

import cn.tianya.weatherforecast.api.ApiHelper;
import cn.tianya.weatherforecast.entity.City;

/**
 * 常量
 * Created by Administrator on 2017/8/23.
 */

public class C {
    /**
     * 接口类型
     */
    public static final ApiHelper.ApiType API_TYPE = ApiHelper.ApiType.wcn;
    /**
     * intent额外数据——选中城市
     */
    public static final String INTENT_EXTRA_SELECTED_CITY = "SELECTED_CITY";

    /**
     * SharedPreferences参数
     */
    public interface SP {
        String NAME = "WEATHER_FORECAST_SP"; // 名称
        String KEY_CITY_INIT = "CITY_INIT"; // 数据库是否初始化
        String KEY_CURRENT_CITY = "CURRENT_CITY"; // 当前城市
    }

    public static final String DEFAULT_CITY; // 默认城市
    /**
     * 城市数据文件
     */
    public static final String CITY_DATA_FILE = "cityData.js";

    static {
        City city = new City();
        city.setProvince("浙江");
        city.setCity("杭州");
        city.setArea("杭州");
        city.setAreaId("101210101");
        DEFAULT_CITY = JSON.toJSONString(city);
    }


}
