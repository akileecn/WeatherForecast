package cn.tianya.weatherforecast;

/**
 * 常量
 * Created by Administrator on 2017/8/23.
 */

public class Constants {
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

}
