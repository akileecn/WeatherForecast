package cn.tianya.weatherforecast.entity;

import lombok.Data;

/**
 * 地区
 * Created by Administrator on 2017/8/26.
 */
@Data
public class Area {
    private String province; // 省
    private String city; // 市
    private String area; // 地区
    private String areaId; // 地区ID
}
