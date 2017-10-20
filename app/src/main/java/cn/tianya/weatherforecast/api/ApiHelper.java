package cn.tianya.weatherforecast.api;

import cn.tianya.weatherforecast.entity.City;

/**
 * api帮助类
 * Created by Administrator on 2017/8/29.
 */
public class ApiHelper {

    public static void executeApi(City city, ApiCallBack callBack) {
        new ApiTask(city.getAreaId(), callBack).execute();
    }

}
