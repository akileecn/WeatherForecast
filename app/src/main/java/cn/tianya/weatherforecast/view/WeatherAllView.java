package cn.tianya.weatherforecast.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Strings;

import java.util.List;

import cn.tianya.weatherforecast.R;
import cn.tianya.weatherforecast.api.ApiUtils;
import cn.tianya.weatherforecast.api.WeatherDto;
import cn.tianya.weatherforecast.api.entity.BaseForecast;
import cn.tianya.weatherforecast.api.entity.Today;
import cn.tianya.weatherforecast.entity.City;
import cn.tianya.weatherforecast.utils.BaseListAdapter;
import cn.tianya.weatherforecast.utils.Constants;

/**
 * 展示气象全部信息view
 * Created by Administrator on 2017/10/27.
 */
public class WeatherAllView extends LinearLayout {
    private TodayViewHolder mTodayViewHolder;
    private TextView cityTv;
    private ListView forecastLv;
    private SharedPreferences mSp;
    private City mCurrentCity;

    public WeatherAllView(Context context) {
        super(context);
        initView();
    }

    public WeatherAllView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public WeatherAllView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public WeatherAllView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        setOrientation(VERTICAL);
        LayoutInflater.from(getContext()).inflate(R.layout.pager_item_weather_all, this);
        mTodayViewHolder = new TodayViewHolder();
        cityTv = findViewById(R.id.text_city);
        forecastLv = findViewById(R.id.list_forecast);
        mSp = PreferenceManager.getDefaultSharedPreferences(getContext());
    }

    /**
     * 加载天气信息
     */
    public void loadWeather(City city) {
        boolean is30d = mSp.getBoolean(Constants.SP.KEY_ENABLE_FORECAST_30D, false);
        ApiUtils.getIndexData(city, result -> {
            if (result.getSuccess()) {
                // 城市
                cityTv.setText(city.getArea());
                WeatherDto response = result.getData();
                Today today = response.getToday();
                // 当天天气
                mTodayViewHolder.setData(today);
                // 预报
                if (!is30d) {
                    initForecastList(response.getForecastList());
                }
            } else {
                if (!Strings.isNullOrEmpty(result.getMessage())) {
                    Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        if (is30d) {
            ApiUtils.get30dData(city, result -> {
                if (result.getSuccess()) {
                    // 预报
                    initForecastList(result.getData());
                } else {
                    if (!Strings.isNullOrEmpty(result.getMessage())) {
                        Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /**
     * 初始化天气预报列表
     */
    private void initForecastList(List<BaseForecast> list) {
        if (list != null) {
            forecastLv.setAdapter(new BaseListAdapter<BaseForecast>(list) {
                @Override
                public View getView(int i, View view, ViewGroup viewGroup) {
                    BaseForecast forecast = getItem(i);
                    ItemViewHolder holder;
                    if (view == null) {
                        view = LayoutInflater.from(getContext()).inflate(R.layout.item_weather, viewGroup, false);
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

    /**
     * 预报列表条目视图容器
     */
    private static class ItemViewHolder {
        private TextView dateTv;
        private TextView weatherTv;
        private TextView tempTv;

        private ItemViewHolder(View view) {
            dateTv = view.findViewById(R.id.text_date);
            weatherTv = view.findViewById(R.id.text_weather);
            tempTv = view.findViewById(R.id.text_temp);
        }

        private void setData(BaseForecast forecast) {
            dateTv.setText(forecast.getDate());
            weatherTv.setText(forecast.getWeather());
            tempTv.setText(forecast.getTemp());
        }
    }

    /**
     * 当天天气预报视图容器
     */
    private class TodayViewHolder {
        private TextView weatherTv;
        private TextView tempTv;
        private TextView aqiTv;
        private TextView windTv;
        private TextView humidity;

        private TodayViewHolder() {
            weatherTv = findViewById(R.id.text_weather);
            tempTv = findViewById(R.id.text_temp);
            aqiTv = findViewById(R.id.text_aqi);
            windTv = findViewById(R.id.text_wind);
            humidity = findViewById(R.id.text_humidity);
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
