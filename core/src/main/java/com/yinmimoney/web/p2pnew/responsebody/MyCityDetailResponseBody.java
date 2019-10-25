package com.yinmimoney.web.p2pnew.responsebody;

import java.math.BigDecimal;

public class MyCityDetailResponseBody {

    private String cityCode;

    private String cityName;

    private String message;

    private String currency;

    private BigDecimal amount;

    private String url;

    private Integer citySellStatus;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getCitySellStatus() {
        return citySellStatus;
    }

    public void setCitySellStatus(Integer citySellStatus) {
        this.citySellStatus = citySellStatus;
    }
}
