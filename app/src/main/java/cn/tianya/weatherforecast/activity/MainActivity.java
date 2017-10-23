package cn.tianya.weatherforecast.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Strings;

import java.util.List;

import cn.tianya.weatherforecast.Constants;
import cn.tianya.weatherforecast.R;
import cn.tianya.weatherforecast.api.ApiUtils;
import cn.tianya.weatherforecast.api.WeatherDto;
import cn.tianya.weatherforecast.api.entity.BaseForecast;
import cn.tianya.weatherforecast.api.entity.Today;
import cn.tianya.weatherforecast.entity.City;
import cn.tianya.weatherforecast.utils.BaseListAdapter;
import cn.tianya.weatherforecast.utils.Helper;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_SELECT_CITY = 1;
    private TodayViewHolder mTodayViewHolder;
    private TextView cityTv;
    private ListView forecastLv;

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
            City city = (City) data.getSerializableExtra(Constants.INTENT_EXTRA_SELECTED_CITY);
            loadWeather(city);
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
        int id = item.getItemId();
        if (id == R.id.action_to_city) {
            startActivityForResult(new Intent(this, CityActivity.class), REQUEST_CODE_SELECT_CITY);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTodayViewHolder = new TodayViewHolder(this);
        cityTv = (TextView) findViewById(R.id.text_city);
        forecastLv = (ListView) findViewById(R.id.list_forecast);
    }

    private void initData() {
        City city = Helper.getDefaultCity(getSharedPreferences(Constants.SP.NAME, MODE_PRIVATE));
        loadWeather(city);
    }

    /**
     * 加载天气信息
     */
    private void loadWeather(City city) {
        ApiUtils.getIndexData(city, result -> {
            if (result.getSuccess()) {
                // 城市
                cityTv.setText(city.getArea());
                WeatherDto response = result.getData();
                Today today = response.getToday();
                // 当天天气
                mTodayViewHolder.setData(today);
                // 预报
//                initForecastList(response.getForecastList());
            } else {
                if (!Strings.isNullOrEmpty(result.getMessage())) {
                    Toast.makeText(MainActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        ApiUtils.get30dData(city, result -> {
            if (result.getSuccess()) {
                // 预报
                initForecastList(result.getData());
            } else {
                if (!Strings.isNullOrEmpty(result.getMessage())) {
                    Toast.makeText(MainActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initForecastList(List<BaseForecast> list){
        if (list != null) {
            forecastLv.setAdapter(new BaseListAdapter<BaseForecast>(list) {
                @Override
                public View getView(int i, View view, ViewGroup viewGroup) {
                    BaseForecast forecast = getItem(i);
                    ItemViewHolder holder;
                    if (view == null) {
                        view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_weather, viewGroup, false);
                        holder = new ItemViewHolder(view);
                        view.setTag(holder);
                    } else {
                        holder = (ItemViewHolder) view.getTag();
                    }
                    holder.setData(forecast);
                    return view;
                }
            });
        }
    }

    private static class ItemViewHolder {
        private TextView dateTv;
        private TextView weatherTv;
        private TextView tempTv;

        private ItemViewHolder(View view) {
            dateTv = (TextView) view.findViewById(R.id.text_date);
            weatherTv = (TextView) view.findViewById(R.id.text_weather);
            tempTv = (TextView) view.findViewById(R.id.text_temp);
        }

        private void setData(BaseForecast forecast) {
            dateTv.setText(forecast.getDate());
            weatherTv.setText(forecast.getWeather());
            tempTv.setText(forecast.getTemp());
        }
    }

    private static class TodayViewHolder {
        private TextView weatherTv;
        private TextView tempTv;
        private TextView aqiTv;
        private TextView windTv;
        private TextView humidity;

        private TodayViewHolder(Activity activity) {
            weatherTv = (TextView) activity.findViewById(R.id.text_weather);
            tempTv = (TextView) activity.findViewById(R.id.text_temp);
            aqiTv = (TextView) activity.findViewById(R.id.text_aqi);
            windTv = (TextView) activity.findViewById(R.id.text_wind);
            humidity = (TextView) activity.findViewById(R.id.text_humidity);
        }

        private void setData(Today today) {
            weatherTv.setText(today.getWeather());
            tempTv.setText(today.getTemperature());
            aqiTv.setText(today.getAqi());
            windTv.setText(today.getWind());
            humidity.setText(today.getHumidity());
        }
    }
}
