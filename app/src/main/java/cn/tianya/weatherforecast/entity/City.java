package cn.tianya.weatherforecast.entity;

import lombok.Data;

/**
 * 地区
 * Created by Administrator on 2017/8/26.
 */
@Data
public class City {
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
     * 获得完整地域名称
     */
    public String getFullArea() {
        return area + "," + city + "," + province;
    }

}
