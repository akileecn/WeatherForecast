package cn.tianya.weatherforecast.entity;

import java.io.Serializable;

/**
 * 地区
 * Created by Administrator on 2017/8/26.
 */
public class City implements Serializable {
    private String province; // 省
    private String city; // 市
    private String area; // 地区
    private String areaId; // 地区ID

    private Boolean selected; // 是否被选中

    public static final City DEFAULT_ONE;

    static {
        DEFAULT_ONE = new City();
        DEFAULT_ONE.setProvince("浙江");
        DEFAULT_ONE.setCity("杭州");
        DEFAULT_ONE.setArea("杭州");
        DEFAULT_ONE.setAreaId("101210101");
    }

    /**
     * 默认城市
     */
    public static City getDefaultCity() {
        return DEFAULT_ONE;
    }

    /**
     * 获得完整地域名称
     */
    public String getFullArea() {
        return area + "," + city + "," + province;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
