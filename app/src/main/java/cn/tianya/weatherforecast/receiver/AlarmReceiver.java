package cn.tianya.weatherforecast.receiver;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.tianya.weatherforecast.R;
import cn.tianya.weatherforecast.activity.MainActivity;
import cn.tianya.weatherforecast.api.entity.Today;
import cn.tianya.weatherforecast.api.task.ApiIndexTask;
import cn.tianya.weatherforecast.utils.Constants;

/**
 * 提醒接收器
 * Created by Administrator on 2017/11/13.
 */
public class AlarmReceiver extends BroadcastReceiver {
    private static final String TAG = "AlarmReceiver";
    private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(2);

    @Override
    public void onReceive(Context context, Intent intent) {
        // 通知家和公司的天气
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String homeAreaId = sp.getString(Constants.SP.KEY_HOME_AREA_ID, "");
        String companyAreaId = sp.getString(Constants.SP.KEY_COMPANY_AREA_ID, "");
        Log.i(TAG, "onReceive: 天气提醒 homeAreaId<" + homeAreaId + "> companyAreaId<" + companyAreaId + ">");
        List<String> areaIdList = new ArrayList<>();
        if (!Strings.isNullOrEmpty(homeAreaId)) {
            areaIdList.add(homeAreaId);
        }
        if (!Strings.isNullOrEmpty(companyAreaId)) {
            areaIdList.add(companyAreaId);
        }
        sendMultipleNotification(context, areaIdList);
    }

    private void sendMultipleNotification(Context context, List<String> areaIdList) {
        if(areaIdList.isEmpty()){
            return;
        }
        new MultipleNotificationTask(context, areaIdList).execute();
    }

    private static class MultipleNotificationTask extends AsyncTask<Void, Void, List<Today>> {
        @SuppressLint("StaticFieldLeak")
        private Context context;
        private List<String> areaIdList;

        private MultipleNotificationTask(Context context, List<String> areaIdList) {
            this.context = context;
            this.areaIdList = areaIdList;
        }

        @Override
        protected List<Today> doInBackground(Void... voids) {
            CyclicBarrier barrier = new CyclicBarrier(areaIdList.size() + 1);
            List<Today> todayList = Collections.synchronizedList(new ArrayList<>());
            for (String areaId : areaIdList) {
                THREAD_POOL.submit(() -> new ApiIndexTask(areaId, result -> {
                    if (result.getSuccess()) {
                        Today today = result.getData().getToday();
                        todayList.add(today);
                        try {
                            barrier.await();
                        } catch (InterruptedException | BrokenBarrierException ignore) {
                        }
                    }
                }).syncExecute());
            }
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException ignore) {
            }
            return todayList;
        }

        @Override
        protected void onPostExecute(List<Today> todayList) {
            StringBuilder sb = new StringBuilder();
            for (Today today : todayList) {
                sb.append(today.getCity()).append(" ")
                        .append(today.getWeather()).append(" ")
                        .append(today.getTemperature()).append(" ");

            }
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 1
                    , new Intent(context, MainActivity.class), 0);
            Notification.Builder builder = new Notification.Builder(context)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("天气提醒")
                    .setContentText(sb.toString());
            Notification notification = builder.build();
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            assert manager != null;
            manager.notify(1, notification);
        }
    }

}
