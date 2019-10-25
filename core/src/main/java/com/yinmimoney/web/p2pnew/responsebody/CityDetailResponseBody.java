package com.yinmimoney.web.p2pnew.responsebody;

import com.yinmimoney.web.p2pnew.responsebody.base.CityBase;

import java.math.BigDecimal;

public class CityDetailResponseBody extends CityBase {

    private BigDecimal lon;//经度

    private BigDecimal lat;//纬度

    private Integer citySellStatus;

    private Integer initStatus;

    private String message;

    private boolean messageStatus;

    private String url;

    private Integer isActivityOpening;
    private String infoPic;
    private String activityUrl;

    public boolean isMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(boolean messageStatus) {
        this.messageStatus = messageStatus;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public BigDecimal getLon() {
        return lon;
    }

    public void setLon(BigDecimal lon) {
        this.lon = lon;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCitySellStatus() {
        return citySellStatus;
    }

    public void setCitySellStatus(Integer citySellStatus) {
        this.citySellStatus = citySellStatus;
    }

    public Integer getInitStatus() {
        return initStatus;
    }

    public void setInitStatus(Integer initStatus) {
        this.initStatus = initStatus;
    }

    public Integer getIsActivityOpening() {
        return isActivityOpening;
    }

    public void setIsActivityOpening(Integer isActivityOpening) {
        this.isActivityOpening = isActivityOpening;
    }

    public String getInfoPic() {
        return infoPic;
    }

    public void setInfoPic(String infoPic) {
        this.infoPic = infoPic;
    }


    public String getActivityUrl() {
        return activityUrl;
    }

    public void setActivityUrl(String activityUrl) {
        this.activityUrl = activityUrl;
    }
}
