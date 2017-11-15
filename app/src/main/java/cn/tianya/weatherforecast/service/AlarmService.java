package cn.tianya.weatherforecast.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Calendar;

import cn.tianya.weatherforecast.utils.DateUtils;

import static cn.tianya.weatherforecast.utils.Constants.ACTION_DAILY_ALARM;
import static cn.tianya.weatherforecast.utils.Constants.SP.DEFAULT_NOTIFY_TIME;
import static cn.tianya.weatherforecast.utils.Constants.SP.KEY_NOTIFY_TIME;

public class AlarmService extends Service {
    private static final String TAG = "AlarmService";
    private AlarmManager mAlarmManager;
    private PendingIntent mIntent;

    public AlarmService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        //TODO 进程被杀死后无法获得通知
        mAlarmManager = getSystemService(AlarmManager.class);
        mIntent = PendingIntent.getBroadcast(this, 0
                , new Intent(ACTION_DAILY_ALARM), 0);
        String time = sp.getString(KEY_NOTIFY_TIME, DEFAULT_NOTIFY_TIME);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, DateUtils.getHour(time));
        cal.set(Calendar.MINUTE, DateUtils.getMinute(time));
        cal.set(Calendar.SECOND, 0);
        mAlarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis()
                , AlarmManager.INTERVAL_DAY, mIntent);
        Log.i(TAG, "onCreate: 开启通知");
    }

    @Override
    public void onDestroy() {
        mAlarmManager.cancel(mIntent);
        Log.i(TAG, "onDestroy: 关闭通知");

    }
}
