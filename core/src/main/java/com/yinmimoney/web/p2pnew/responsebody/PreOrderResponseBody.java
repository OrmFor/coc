package com.yinmimoney.web.p2pnew.responsebody;

import java.math.BigDecimal;

public class PreOrderResponseBody {

    private String platWalletAddress;

    private String sellerWalletAddress;

    private String orderNo;

    private String cityName;

    private BigDecimal amount;

    private BigDecimal platAmount;

    private BigDecimal sellerAmount;

    private String currency;


    public BigDecimal getPlatAmount() {
        return platAmount;
    }

    public void setPlatAmount(BigDecimal platAmount) {
        this.platAmount = platAmount;
    }

    public BigDecimal getSellerAmount() {
        return sellerAmount;
    }

    public void setSellerAmount(BigDecimal sellerAmount) {
        this.sellerAmount = sellerAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPlatWalletAddress() {
        return platWalletAddress;
    }

    public void setPlatWalletAddress(String platWalletAddress) {
        this.platWalletAddress = platWalletAddress;
    }

    public String getSellerWalletAddress() {
        return sellerWalletAddress;
    }

    public void setSellerWalletAddress(String sellerWalletAddress) {
        this.sellerWalletAddress = sellerWalletAddress;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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
