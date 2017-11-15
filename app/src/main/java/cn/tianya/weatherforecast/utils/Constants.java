package cn.tianya.weatherforecast.utils;

/**
 * 常量
 * Created by Administrator on 2017/8/23.
 */

public interface Constants {
    /**
     * intent额外数据——选中城市
     */
    String INTENT_EXTRA_SELECTED_CITY = "SELECTED_CITY";

    /**
     * SharedPreferences参数
     */
    interface SP {
        String KEY_CITY_INIT = "CITY_INIT"; // 数据库是否初始化
        String KEY_CURRENT_CITY = "CURRENT_CITY"; // 当前城市
        String KEY_ENABLE_FORECAST_30D = "ENABLE_FORECAST_30D"; // 是否显示一个月天气
        String KEY_ENABLE_NOTIFY = "KEY_ENABLE_NOTIFY"; // 是否开启通知
        String KEY_NOTIFY_TIME = "KEY_NOTIFY_TIME"; // 通知时间
        String DEFAULT_NOTIFY_TIME = "07:00"; // 默认通知时间
    }

    /**
     * 每日定时提醒action
     */
    String ACTION_DAILY_ALARM = "cn.tianya.weatherforecast.action.DAILY_ALARM";
}
