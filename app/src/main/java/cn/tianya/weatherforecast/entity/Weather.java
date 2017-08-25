package cn.tianya.weatherforecast.entity;

/**
 * 天气
 * Created by Administrator on 2017/8/23.
 */
public class Weather {
    private String date; // "22日星期二"
    private String sunrise; // "05:24"
    private String high; //	"高温 33.0℃"
    private String low; // "低温 27.0℃"
    private String sunset; // "18:28"
    private Integer aqi; // 18
    private String fx; // "东南风"
    private String fl; // "3-4级"
    private String type; //	"阵雨"
    private String notice; //	"愿雨后清新的空气给您带来好心情！"

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public Integer getAqi() {
        return aqi;
    }

    public void setAqi(Integer aqi) {
        this.aqi = aqi;
    }

    public String getFx() {
        return fx;
    }

    public void setFx(String fx) {
        this.fx = fx;
    }

    public String getFl() {
        return fl;
    }

    public void setFl(String fl) {
        this.fl = fl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }
}
