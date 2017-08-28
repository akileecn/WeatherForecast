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
import cn.tianya.weatherforecast.dto.WeatherResponse;
import cn.tianya.weatherforecast.entity.Today;
import cn.tianya.weatherforecast.entity.Weather;
import cn.tianya.weatherforecast.utils.BaseListAdapter;
import cn.tianya.weatherforecast.utils.WeatherApiTask;

public class MainActivity extends AppCompatActivity {
    private TextView todayWenduTv;
    private TextView todayWeatherTv;
    private ListView weatherLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_to_city) {
            startActivity(new Intent(this, CityActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        todayWeatherTv = (TextView) findViewById(R.id.text_today_weather);
        todayWenduTv = (TextView) findViewById(R.id.text_today_wendu);
        weatherLv = (ListView) findViewById(R.id.list_weather);
    }

    private void initData() {
        loadWeather("杭州市");
    }

    private void loadWeather(String city) {
        new WeatherApiTask(city, result -> {
            if (result.getSuccess()) {
                WeatherResponse response = result.getData();
                Today today = response.getToday();
                if (today != null) {
                    todayWenduTv.setText(today.getWendu());
                    todayWeatherTv.setText(today.getWeather());
                }
                List<Weather> list = response.getWeatherList();
                if (list != null) {
                    weatherLv.setAdapter(new BaseListAdapter<Weather>(list) {
                        @Override
                        public View getView(int i, View view, ViewGroup viewGroup) {
                            Weather weather = getItem(i);
                            ViewHolder holder;
                            if (view == null) {
                                view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_weather, viewGroup, false);
                                holder = new ViewHolder(view);
                                view.setTag(holder);
                            } else {
                                holder = (ViewHolder) view.getTag();
                            }
                            holder.setData(weather);
                            return view;
                        }
                    });
                }
            } else {
                if (!Strings.isNullOrEmpty(result.getMessage())) {
                    Toast.makeText(MainActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }).execute();
    }

    private static class ViewHolder {
        private TextView dataTv;
        private TextView typeTv;
        private TextView highTv;
        private TextView lowTv;

        private ViewHolder(View view) {
            dataTv = (TextView) view.findViewById(R.id.text_date);
            typeTv = (TextView) view.findViewById(R.id.text_type);
            highTv = (TextView) view.findViewById(R.id.text_high);
            lowTv = (TextView) view.findViewById(R.id.text_low);
        }

        private void setData(Weather weather) {
            dataTv.setText(weather.getDate());
            typeTv.setText(weather.getType());
            highTv.setText(weather.getHigh());
            lowTv.setText(weather.getLow());
        }
    }
}
