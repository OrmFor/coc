package com.yinmimoney.web.p2pnew.dto;

import java.math.BigDecimal;

public class CheapCity {
    private String cityCode;

    private String cityName;

    private BigDecimal amount;


    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
