package cn.tianya.weatherforecast.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import cn.tianya.weatherforecast.R;
import cn.tianya.weatherforecast.activity.MainActivity;

/**
 * 提醒接收器
 * Created by Administrator on 2017/11/13.
 */
public class AlarmReceiver extends BroadcastReceiver {
    private static final String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive: 天气提醒");
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1
                , new Intent(context, MainActivity.class), 0);
        Notification.Builder builder = new Notification.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("天气提醒")
                .setContentText("xxxx");
        Notification notification = builder.build();
        NotificationManager manager = context.getSystemService(NotificationManager.class);
        assert manager != null;
        manager.notify(1, notification);
    }
}
