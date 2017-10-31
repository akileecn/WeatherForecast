package cn.tianya.weatherforecast.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import cn.tianya.weatherforecast.R;
import cn.tianya.weatherforecast.dao.CityDao;
import cn.tianya.weatherforecast.entity.City;
import cn.tianya.weatherforecast.utils.Helper;
import cn.tianya.weatherforecast.view.WeatherAllView;

/**
 * 主程序
 */
public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_SELECT_CITY = 1;
    private static final int REQUEST_CODE_SETTINGS = 2;
    private ViewPager mWeatherPager;
    private List<City> mCityList;
    private CityDao mCityDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_CITY && resultCode == RESULT_OK) {
            reloadCity();
        }
        if (requestCode == REQUEST_CODE_SETTINGS && resultCode == RESULT_OK) {
            reloadCity();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_to_city:
                startActivityForResult(new Intent(this, CityActivity.class), REQUEST_CODE_SELECT_CITY);
                return true;
            case R.id.menu_to_settings:
                startActivityForResult(new Intent(this, SettingsActivity.class), REQUEST_CODE_SETTINGS);
                return true;
            case R.id.menu_test:
                //TODO test
                test();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mWeatherPager = findViewById(R.id.pager_weather);
        TabLayout weatherTab = findViewById(R.id.tab_weather);
        weatherTab.setupWithViewPager(mWeatherPager);
    }

    private void initData() {
        mCityDao = new CityDao(this);
        mCityList = new ArrayList<>();
        mWeatherPager.setAdapter(new WeatherPagerAdapter());
        reloadCity();
    }

    private class WeatherPagerAdapter extends PagerAdapter {
        @Override
        public CharSequence getPageTitle(int position) {
            return mCityList.get(position).getArea();
        }

        @Override
        public int getCount() {
            return mCityList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return object.equals(view);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            City city = mCityList.get(position);
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT);
            WeatherAllView view = new WeatherAllView(MainActivity.this);
            container.addView(view, params);
            view.loadWeather(city);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    private void reloadCity() {
        List<City> list = mCityDao.listSelected();
        mCityList.clear();
        if (list != null && !list.isEmpty()) {
            mCityList.addAll(list);
        } else {
            mCityList.add(Helper.getDefaultCity());
        }
        mWeatherPager.getAdapter().notifyDataSetChanged();
        mWeatherPager.setCurrentItem(0);
    }

    private void test() {
        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("天气提醒")
                .setContentText("xxxx");
        Notification notification = builder.build();
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.notify(1, notification);
    }
}
