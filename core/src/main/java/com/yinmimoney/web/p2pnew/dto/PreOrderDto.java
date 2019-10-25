package com.yinmimoney.web.p2pnew.dto;

import java.math.BigDecimal;
import java.util.Date;

public class PreOrderDto {

    private String cityName;

    private String cityCode;

    private String buyerCode;

    private String orderNo;

    private Date createTime;

    private String sellerCode;

    private String statusStr;

    private Integer status;

    private BigDecimal amount;

    private String timeStr;

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(Integer status) {
        switch (status) {
            case 0:
                statusStr = "预交易";
                break;
            case 6:
                statusStr = "关闭";
                break;
            default:
                statusStr = "预交易";
                break;
        }
    }


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

    public String getBuyerCode() {
        return buyerCode;
    }

    public void setBuyerCode(String buyerCode) {
        this.buyerCode = buyerCode;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSellerCode() {
        return sellerCode;
    }

    public void setSellerCode(String sellerCode) {
        this.sellerCode = sellerCode;
    }
}
