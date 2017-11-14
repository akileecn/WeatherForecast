package cn.tianya.weatherforecast.utils;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;

import java.util.List;

/**
 * Created by Administrator on 2015/12/4.
 * 服务状态
 */
public class ServiceStatusUtils {

    /**
     * 检测服务是否激活
     */
    public static boolean isActive(Context context, Class<? extends Service> serviceClass) {
        ActivityManager activityManager = context.getSystemService(ActivityManager.class);
        assert activityManager != null;
        List<ActivityManager.RunningServiceInfo> services = activityManager.getRunningServices(100);
        for (ActivityManager.RunningServiceInfo serviceInfo : services) {
            String className = serviceInfo.service.getClassName();
            if (className.equals(serviceClass.getName())) {
                return true;
            }
        }
        return false;
    }

}
