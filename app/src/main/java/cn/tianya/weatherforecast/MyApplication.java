package cn.tianya.weatherforecast;

import android.app.Application;

import cn.tianya.weatherforecast.utils.Helper;

/**
 * 自定义应用,初始化部分参数
 * Created by Administrator on 2017/10/30.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Helper.init(this);
    }
}
