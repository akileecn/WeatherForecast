package cn.tianya.weatherforecast.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Calendar;

import static cn.tianya.weatherforecast.utils.Constants.ACTION_DAILY_ALARM;

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
        //TODO 进程被杀死后无法获得通知
        mAlarmManager = getSystemService(AlarmManager.class);
        mIntent = PendingIntent.getBroadcast(this, 0
                , new Intent(ACTION_DAILY_ALARM), 0);
        //TODO 时间配置
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 6);
        cal.set(Calendar.MINUTE, 0);
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
