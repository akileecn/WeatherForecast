package cn.tianya.weatherforecast.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import java.util.List;

import cn.tianya.weatherforecast.R;
import cn.tianya.weatherforecast.service.AlarmService;

import static cn.tianya.weatherforecast.utils.Constants.SP.KEY_ENABLE_NOTIFY;
import static cn.tianya.weatherforecast.utils.Constants.SP.KEY_NOTIFY_TIME;

/**
 * 设置
 */
public class SettingsActivity extends AppCompatPreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            setResult(RESULT_OK);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.pref_headers, target);
    }

    /**
     * 通用
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class GeneralPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);
            setHasOptionsMenu(true);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * 提醒
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class NotificationPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
            addPreferencesFromResource(R.xml.pref_notification);
            setHasOptionsMenu(true);
            // 时间
            Preference time = findPreference(KEY_NOTIFY_TIME);
            time.setEnabled(sp.getBoolean(KEY_ENABLE_NOTIFY, false));
            time.setOnPreferenceChangeListener((preference, newValue) -> {
                startNotificationAlarm(true);
                preference.setSummary(newValue.toString());
                return true;
            });
            // 是否开启
            findPreference(KEY_ENABLE_NOTIFY).setOnPreferenceChangeListener((preference, newValue) -> {
                boolean value = (Boolean) newValue;
                time.setEnabled(value);
                startNotificationAlarm(value);
                return true;
            });
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        /**
         * 启动定时通知
         *
         * @param enable 是否开启
         */
        private void startNotificationAlarm(boolean enable) {
            Context context = getContext();
            if (enable) {
                context.startService(new Intent(context, AlarmService.class));
            } else {
                context.stopService(new Intent(context, AlarmService.class));
            }
        }
    }

    /**
     * 是否有效Fragment.
     */
    @Override
    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragment.class.getName().equals(fragmentName)
                || GeneralPreferenceFragment.class.getName().equals(fragmentName)
                || NotificationPreferenceFragment.class.getName().equals(fragmentName);
    }

    /**
     * 是否大屏
     */
    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

}
