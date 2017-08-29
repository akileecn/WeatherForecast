package cn.tianya.weatherforecast.api;

import cn.tianya.weatherforecast.api.sojson.SojsonApiTask;
import cn.tianya.weatherforecast.api.wcn.WcnApiTask;
import cn.tianya.weatherforecast.entity.City;
import cn.tianya.weatherforecast.utils.C;

/**
 * api帮助类
 * Created by Administrator on 2017/8/29.
 */
public class ApiHelper {
    /**
     * api类型
     */
    public enum ApiType {
        sojson, wcn
    }

    public static void executeApi(City city, ApiCallBack callBack) {
        switch (C.API_TYPE){
            case wcn:
                new WcnApiTask(city.getAreaId(), callBack).execute();
                break;
            case sojson:
                new SojsonApiTask(city.getCity(), callBack).execute();
                break;
        }
    }
}
