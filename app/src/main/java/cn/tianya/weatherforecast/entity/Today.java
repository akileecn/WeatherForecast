package cn.tianya.weatherforecast.entity;

/**
 * 当天天气
 * Created by Administrator on 2017/8/25.
 */
public class Today {
    private String shidu; //"89%"
    private Integer pm25; //16
    private Integer pm10; //30
    private String quality; //"优"
    private String wendu; //"28"
    private String ganmao; //"各类人群可自由活动"

    private String weather;

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
        return wendu + "℃";
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

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }
}
