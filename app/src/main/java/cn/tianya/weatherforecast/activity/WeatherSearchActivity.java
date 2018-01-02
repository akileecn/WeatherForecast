package cn.tianya.weatherforecast.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.tianya.weatherforecast.R;
import cn.tianya.weatherforecast.api.entity.BaseForecast;
import cn.tianya.weatherforecast.api.task.Api30dTask;
import cn.tianya.weatherforecast.dao.CityDao;
import cn.tianya.weatherforecast.entity.City;
import cn.tianya.weatherforecast.utils.Helper;
import lombok.Data;

public class WeatherSearchActivity extends AppCompatActivity {
    private Spinner citySpinner;
    private Spinner mainWeatherSpinner;
    private Spinner secondaryWeatherSpinner;
    private NumberPicker dayNumberNp;
    private Button searchBtn;
    private TextView resultTv;
    private ProgressBar searchPb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        setContentView(R.layout.activity_weather_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        citySpinner = findViewById(R.id.city_spinner);
        mainWeatherSpinner = findViewById(R.id.main_weather_spinner);
        secondaryWeatherSpinner = findViewById(R.id.secondary_weather_spinner);
        dayNumberNp = findViewById(R.id.day_number_np);
        searchBtn = findViewById(R.id.search_btn);
        resultTv = findViewById(R.id.result_tv);
        searchPb = findViewById(R.id.search_pb);
    }

    private void initData() {
        citySpinner.setAdapter(new ArrayAdapter<>(this
                , android.R.layout.simple_spinner_dropdown_item, listCity()));
        List<SpinnerData> weatherList = new ArrayList<>();
        weatherList.add(new SpinnerData("00", "晴"));
        weatherList.add(new SpinnerData("01", "多云"));
        mainWeatherSpinner.setAdapter(new ArrayAdapter<>(this
                , android.R.layout.simple_spinner_dropdown_item, weatherList));
        secondaryWeatherSpinner.setAdapter(new ArrayAdapter<>(this
                , android.R.layout.simple_spinner_dropdown_item, weatherList));
        dayNumberNp.setMinValue(1);
        dayNumberNp.setMaxValue(10);

        searchBtn.setOnClickListener(v -> {
            String cityAreaId = ((SpinnerData) citySpinner.getSelectedItem()).getKey();
            String mainWeather = ((SpinnerData) mainWeatherSpinner.getSelectedItem()).getKey();
            String secondaryWeather = ((SpinnerData) secondaryWeatherSpinner.getSelectedItem()).getKey();
            int dayNumber = dayNumberNp.getValue();
            search(cityAreaId, mainWeather, secondaryWeather, dayNumber);
        });
    }

    private List<SpinnerData> listCity() {
        List<SpinnerData> result = new ArrayList<>();
        List<City> list = new CityDao(this).listSelected();
        if (list == null || list.isEmpty()) {
            result.add(new SpinnerData(Helper.getDefaultCity()));
            return result;
        }
        for (City city : list) {
            result.add(new SpinnerData(city));
        }
        return result;
    }

    @Data
    private class SpinnerData {
        private String key;
        private String value;

        public SpinnerData(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public SpinnerData(City city) {
            this(city.getAreaId(), city.getArea());
        }

        @Override
        public String toString() {
            return value;
        }
    }

    private void search(String cityAreaId, String mainWeather, String secondaryWeather, int dayNumber) {
        searchPb.setVisibility(View.VISIBLE);
        new Api30dTask(cityAreaId, result -> {
            searchPb.setVisibility(View.INVISIBLE);
            if (result.getSuccess()) {
                List<BaseForecast> list = result.getData();
                int count = 0;
                String beginDay = "";
                for (BaseForecast forecast : list) {
                    String rawWeather = forecast.getRawWeather();
                    boolean validWeather = (mainWeather.equals(rawWeather))
                            || (!TextUtils.isEmpty(secondaryWeather) && secondaryWeather.equals(rawWeather));
                    if (validWeather) {
                        if (count == 0) {
                            beginDay = forecast.getDate();
                        }
                        count++;
                    } else {
                        count = 0;
                        beginDay = null;
                    }
                    if (count >= dayNumber) {
                        String text = beginDay + " - " + forecast.getDate();
                        resultTv.setText(text);
                        return;
                    }
                }
            }
            resultTv.setText(R.string.not_found);
        }).execute();
    }
}
