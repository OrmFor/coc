package com.yinmimoney.web.p2pnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class CommonOrderDetailEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String txid;
	private String cityCode;
	private String orderNo;
	private String userCode;
	private java.math.BigDecimal amount;
	private String currency;
	private String type;
	private String operateType;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date createTime;
	private String cityName;
	
    public Integer getId() {
        return id;
    }
	public void setId(Integer id) {
        this.id = id;
    }
    public String getTxid() {
        return txid;
    }
	public void setTxid(String txid) {
        this.txid = txid;
    }
    public String getCityCode() {
        return cityCode;
    }
	public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
    public String getOrderNo() {
        return orderNo;
    }
	public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public String getUserCode() {
        return userCode;
    }
	public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
    public java.math.BigDecimal getAmount() {
        return amount;
    }
	public void setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
    }
    public String getCurrency() {
        return currency;
    }
	public void setCurrency(String currency) {
        this.currency = currency;
    }
    public String getType() {
        return type;
    }
	public void setType(String type) {
        this.type = type;
    }
    public String getOperateType() {
        return operateType;
    }
	public void setOperateType(String operateType) {
        this.operateType = operateType;
    }
    public java.util.Date getCreateTime() {
        return createTime;
    }
	public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }
    public String getCityName() {
        return cityName;
    }
	public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}