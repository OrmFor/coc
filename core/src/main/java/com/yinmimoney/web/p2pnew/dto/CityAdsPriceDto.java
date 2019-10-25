package com.yinmimoney.web.p2pnew.dto;

import java.math.BigDecimal;
import java.util.Date;

public class CityAdsPriceDto {
    private String cityName;

    private BigDecimal unitPrice;

    private String cityCode;

    private Date createTime;

    private Date expireTime;

    private BigDecimal amount;

    private int citySellStatus;

    public int getCitySellStatus() {
        return citySellStatus;
    }

    public void setCitySellStatus(int citySellStatus) {
        this.citySellStatus = citySellStatus;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}
