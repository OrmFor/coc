package com.yinmimoney.web.p2pnew.requestbody;

import org.hibernate.validator.constraints.NotBlank;

import java.math.BigDecimal;

public class ModifyCityDetailRequestBody {

    private String userCode;

    @NotBlank(message = "{modify.cityCode.empty}")
    private String cityCode;

    private String message;

    private Integer citySellStatus;

    private BigDecimal amount;

    private String currency;

    private String url;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
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

    public Integer getCitySellStatus() {
        return citySellStatus;
    }

    public void setCitySellStatus(Integer citySellStatus) {
        this.citySellStatus = citySellStatus;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
