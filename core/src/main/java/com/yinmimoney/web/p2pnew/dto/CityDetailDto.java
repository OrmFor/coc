package com.yinmimoney.web.p2pnew.dto;

import com.yinmimoney.web.p2pnew.responsebody.base.CityBase;

import java.math.BigDecimal;

public class CityDetailDto extends CityBase {

    private BigDecimal lon;//经度

    private BigDecimal lat;//纬度

    private Integer intiStatus;

    private BigDecimal amount;//金额

    private String currency;

    private String message;


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

    public Integer getIntiStatus() {
        return intiStatus;
    }

    public void setIntiStatus(Integer intiStatus) {
        this.intiStatus = intiStatus;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
