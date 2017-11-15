package cn.tianya.weatherforecast.utils;

/**
 * 时间工具类
 * Created by Administrator on 2017/11/15.
 */
public class DateUtils {

    public static int getHour(String time) {
        String[] pieces = time.split(":");
        return (Integer.parseInt(pieces[0]));
    }

    public static int getMinute(String time) {
        String[] pieces = time.split(":");
        return (Integer.parseInt(pieces[1]));
    }
}
