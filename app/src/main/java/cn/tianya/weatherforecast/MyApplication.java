package cn.tianya.weatherforecast;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import cn.tianya.weatherforecast.service.AlarmService;
import cn.tianya.weatherforecast.utils.Constants;
import cn.tianya.weatherforecast.utils.Helper;
import cn.tianya.weatherforecast.utils.ServiceStatusUtils;

/**
 * 自定义应用,初始化部分参数
 * Created by Administrator on 2017/10/30.
 */
public class MyApplication extends Application {
    private SharedPreferences sp;
    @Override
    public void onCreate() {
        super.onCreate();
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        Helper.init(this);
        initService();
    }

    private void initService(){
        boolean enableNotify = sp.getBoolean(Constants.SP.KEY_ENABLE_NOTIFY, false);
        if(enableNotify && !ServiceStatusUtils.isActive(this, AlarmService.class)){
            startService(new Intent(this, AlarmService.class));
        }
    }
}
