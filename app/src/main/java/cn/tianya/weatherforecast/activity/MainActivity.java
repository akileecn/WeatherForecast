package cn.tianya.weatherforecast.activity;

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

import cn.tianya.weatherforecast.R;
import cn.tianya.weatherforecast.api.ApiHelper;
import cn.tianya.weatherforecast.api.Forecast;
import cn.tianya.weatherforecast.api.Today;
import cn.tianya.weatherforecast.api.WeatherDto;
import cn.tianya.weatherforecast.entity.City;
import cn.tianya.weatherforecast.utils.BaseListAdapter;
import cn.tianya.weatherforecast.utils.C;
import cn.tianya.weatherforecast.utils.Helper;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_SELECT_CITY = 1;
    private TextView todayTemperatureTv;
    private TextView todayWeatherTv;
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
            City city = (City) data.getSerializableExtra(C.INTENT_EXTRA_SELECTED_CITY);
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
        todayWeatherTv = (TextView) findViewById(R.id.text_today_weather);
        todayTemperatureTv = (TextView) findViewById(R.id.text_today_temperature);
        cityTv = (TextView) findViewById(R.id.text_city);
        forecastLv = (ListView) findViewById(R.id.list_forecast);
    }

    private void initData() {
        City city = Helper.getDefaultCity(getSharedPreferences(C.SP.NAME, MODE_PRIVATE));
        loadWeather(city);
    }

    /**
     * 加载天气信息
     *
     * @param city 城市
     */
    private void loadWeather(City city) {
        ApiHelper.executeApi(city, result -> {
            if (result.getSuccess()) {
                cityTv.setText(city.getArea());
                WeatherDto response = result.getData();
                Today today = response.getToday();
                if (today != null) {
                    todayTemperatureTv.setText(today.getTemperature());
                    todayWeatherTv.setText(today.getWeather());
                }
                List<Forecast> list = response.getForecastList();
                if (list != null) {
                    forecastLv.setAdapter(new BaseListAdapter<Forecast>(list) {
                        @Override
                        public View getView(int i, View view, ViewGroup viewGroup) {
                            Forecast forecast = getItem(i);
                            ViewHolder holder;
                            if (view == null) {
                                view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_weather, viewGroup, false);
                                holder = new ViewHolder(view);
                                view.setTag(holder);
                            } else {
                                holder = (ViewHolder) view.getTag();
                            }
                            holder.setData(forecast);
                            return view;
                        }
                    });
                }
            } else {
                if (!Strings.isNullOrEmpty(result.getMessage())) {
                    Toast.makeText(MainActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private static class ViewHolder {
        private TextView dateTv;
        private TextView dayWeatherTv;
        private TextView dayTemperatureTv;
        private TextView nightWeatherTv;
        private TextView nightTemperatureTv;

        private ViewHolder(View view) {
            dateTv = (TextView) view.findViewById(R.id.text_date);
            dayWeatherTv = (TextView) view.findViewById(R.id.text_day_weather);
            dayTemperatureTv = (TextView) view.findViewById(R.id.text_day_temperature);
            nightWeatherTv = (TextView) view.findViewById(R.id.text_night_weather);
            nightTemperatureTv = (TextView) view.findViewById(R.id.text_night_temperature);
        }

        private void setData(Forecast forecast) {
            dateTv.setText(forecast.getDate());
            dayWeatherTv.setText(forecast.getDayWeather());
            dayTemperatureTv.setText(forecast.getDayTemperature());
            nightWeatherTv.setText(forecast.getNightWeather());
            nightTemperatureTv.setText(forecast.getNightTemperature());
        }
    }
}
