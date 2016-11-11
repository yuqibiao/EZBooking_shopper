package com.yyyu.ezbooking_shopper.bean.foo;

import java.io.Serializable;

/**
 * 功能：地图标注信息实体类
 *
 * @author yyyu
 * @date 2016/9/16
 */
public class MapMarkInfoBean implements Serializable{

    /*经度*/
    private double longitude;
    /*纬度*/
    private double latitue;
    /*标注名称（商店名称）*/
    private String name;
    /*标注介绍*/
    private String intro;

    public void MapMarkInfoBean(){

    }

    public MapMarkInfoBean(double longitude, double latitue, String name, String intro) {
        this.longitude = longitude;
        this.latitue = latitue;
        this.name = name;
        this.intro = intro;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitue() {
        return latitue;
    }

    public void setLatitue(double latitue) {
        this.latitue = latitue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

}
