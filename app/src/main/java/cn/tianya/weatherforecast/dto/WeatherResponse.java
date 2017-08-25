package cn.tianya.weatherforecast.dto;

import java.util.ArrayList;
import java.util.List;

import cn.tianya.weatherforecast.entity.Today;
import cn.tianya.weatherforecast.entity.Weather;

/**
 * api响应
 * Created by Administrator on 2017/8/23.
 */
public class WeatherResponse {
    private String message; // "Success !"
    private Integer status; // 200
    private String city; // "宁波"
    private Integer count; // 1
    private WeatherData data;

    public Boolean isSuccess() {
        return status == 200;
    }

    /**
     * 当天天气
     */
    public Today getToday() {
        if (data != null) {
            Today today = new Today();
            today.setGanmao(data.ganmao);
            today.setPm10(data.pm10);
            today.setPm25(data.pm25);
            today.setQuality(data.quality);
            today.setShidu(data.shidu);
            today.setWendu(data.wendu);
            if (data.forecast != null && !data.forecast.isEmpty()) {
                today.setWeather(data.forecast.get(0).getType());
            }
            return today;
        }
        return null;
    }

    /**
     * 天气预报列表
     */
    public List<Weather> getWeatherList() {
        List<Weather> list = new ArrayList<>();
        if (data != null) {
            if (data.yesterday != null) {
                list.add(data.yesterday);
            }
            if (data.forecast != null && !data.forecast.isEmpty()) {
                list.addAll(data.getForecast());
            }
        }
        return list;
    }

    public static class WeatherData {
        private String shidu; // "68%"
        private Integer pm25; // 7
        private Integer pm10; // 19
        private String quality; // "优"
        private String wendu; // "30"
        private String ganmao; // "各类人群可自由活动"
        private Weather yesterday;
        private List<Weather> forecast;

        public String getShidu() {
            return shidu;
        }

        public void setShidu(String shidu) {
            this.shidu = shidu;
        }

        public Integer getPm25() {
            return pm25;
        }

        public void setPm25(Integer pm25) {
            this.pm25 = pm25;
        }

        public Integer getPm10() {
            return pm10;
        }

        public void setPm10(Integer pm10) {
            this.pm10 = pm10;
        }

        public String getQuality() {
            return quality;
        }

        public void setQuality(String quality) {
            this.quality = quality;
        }

        public String getWendu() {
            return wendu;
        }

        public void setWendu(String wendu) {
            this.wendu = wendu;
        }

        public String getGanmao() {
            return ganmao;
        }

        public void setGanmao(String ganmao) {
            this.ganmao = ganmao;
        }

        public Weather getYesterday() {
            return yesterday;
        }

        public void setYesterday(Weather yesterday) {
            this.yesterday = yesterday;
        }

        public List<Weather> getForecast() {
            return forecast;
        }

        public void setForecast(List<Weather> forecast) {
            this.forecast = forecast;
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public WeatherData getData() {
        return data;
    }

    public void setData(WeatherData data) {
        this.data = data;
    }
}
