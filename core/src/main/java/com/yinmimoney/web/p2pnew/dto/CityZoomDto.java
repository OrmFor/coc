package com.yinmimoney.web.p2pnew.dto;

import com.yinmimoney.web.p2pnew.responsebody.base.CityBase;

import java.math.BigDecimal;

public class CityZoomDto extends CityBase {

    private BigDecimal lon;//经度

    private BigDecimal lat;//纬度

    private Integer initStatus;

    private Integer citySellStatus;

    private BigDecimal amount;//金额

    private String currency;

    public Integer getCitySellStatus() {
        return citySellStatus;
    }

    public void setCitySellStatus(Integer citySellStatus) {
        this.citySellStatus = citySellStatus;
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

    public Integer getInitStatus() {
        return initStatus;
    }

    public void setInitStatus(Integer initStatus) {
        this.initStatus = initStatus;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
