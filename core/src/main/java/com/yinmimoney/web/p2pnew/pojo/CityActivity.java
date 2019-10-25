package com.yinmimoney.web.p2pnew.pojo;

import com.alibaba.fastjson.JSONObject;
import com.yinmimoney.web.p2pnew.pojo.entity.CityActivityEntity;
import com.yinmimoney.web.p2pnew.util.StringUtil;

import java.math.BigDecimal;

public class CityActivity extends CityActivityEntity {
    private static final long serialVersionUID = 1L;

    private String locationPic;
    private String infoPic;
    private String activityStatus;
    private BigDecimal lat;
    private BigDecimal lon;
    private String startTimeString;
    private String endTimeString;

    public String getLocationPic() {
        String pictures = getPictures();
        if(StringUtil.isNotBlank(pictures)){
            JSONObject jsonObject = JSONObject.parseObject(pictures);
            if(jsonObject!=null){
                locationPic=jsonObject.getString("locationPic");
            }
        }
        return locationPic;
    }

    public void setLocationPic(String locationPic) {
        this.locationPic = locationPic;
    }

    public String getInfoPic() {
        String pictures = getPictures();
        if(StringUtil.isNotBlank(pictures)){
            JSONObject jsonObject = JSONObject.parseObject(pictures);
            if(jsonObject!=null){
                infoPic=jsonObject.getString("infoPic");
            }
        }
        return infoPic;
    }

    public void setInfoPic(String infoPic) {
        this.infoPic = infoPic;
    }

    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLon() {
        return lon;
    }

    public void setLon(BigDecimal lon) {
        this.lon = lon;
    }

    public String getStartTimeString() {
        return startTimeString;
    }

    public void setStartTimeString(String startTimeString) {
        this.startTimeString = startTimeString;
    }

    public String getEndTimeString() {
        return endTimeString;
    }

    public void setEndTimeString(String endTimeString) {
        this.endTimeString = endTimeString;
    }
}