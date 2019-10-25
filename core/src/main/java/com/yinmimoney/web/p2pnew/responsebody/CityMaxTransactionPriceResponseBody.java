package com.yinmimoney.web.p2pnew.responsebody;

import java.math.BigDecimal;

public class  CityMaxTransactionPriceResponseBody{

    private BigDecimal amount;

    private String cityName;

    private String currency;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
