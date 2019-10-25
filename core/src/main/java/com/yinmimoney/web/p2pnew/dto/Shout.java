package com.yinmimoney.web.p2pnew.dto;

import java.math.BigDecimal;

/**
 * Created by dyf on 2019/7/18 10:23
 */
public class Shout {
    private String message;
    private String token;
    private String cityCode;
    private String txId;
    private String userName;
    private BigDecimal money;
    private String currency;
    private Integer status;
    private String buyerCode;
    private String owerCode;
    private String speakCode;
    private String speakType;

    public String getSpeakType() {
        return speakType;
    }

    public void setSpeakType(String speakType) {
        this.speakType = speakType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCityCode() {
        return cityCode;
    }

    public String getSpeakCode() {
        return speakCode;
    }

    public void setSpeakCode(String speakCode) {
        this.speakCode = speakCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBuyerCode() {
        return buyerCode;
    }

    public void setBuyerCode(String buyerCode) {
        this.buyerCode = buyerCode;
    }

    public String getOwerCode() {
        return owerCode;
    }

    public void setOwerCode(String owerCode) {
        this.owerCode = owerCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Shout{" +
                "message='" + message + '\'' +
                ", token='" + token + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", txId='" + txId + '\'' +
                ", userName='" + userName + '\'' +
                ", money=" + money +
                ", currency='" + currency + '\'' +
                ", status=" + status +
                ", buyerCode='" + buyerCode + '\'' +
                ", owerCode='" + owerCode + '\'' +
                ", speakCode=" + speakCode +
                '}';
    }
}
