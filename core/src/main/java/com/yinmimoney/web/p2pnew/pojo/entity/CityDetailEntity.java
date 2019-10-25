package com.yinmimoney.web.p2pnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class CityDetailEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String cityCode;
	private String userCode;
	private String orderNo;
	private String message;
	private Integer citySellStatus;
	private String url;
	private java.math.BigDecimal amount;
	private String currency;
	private Boolean messageStatus;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date createTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date updateTime;
	private String txId;
	
    public Integer getId() {
        return id;
    }
	public CityDetailEntity setId(Integer id) {
        this.id = id;
        return this;
    }
    public String getCityCode() {
        return cityCode;
    }
	public CityDetailEntity setCityCode(String cityCode) {
        this.cityCode = cityCode;
        return this;
    }
    public String getUserCode() {
        return userCode;
    }
	public CityDetailEntity setUserCode(String userCode) {
        this.userCode = userCode;
        return this;
    }
    public String getOrderNo() {
        return orderNo;
    }
	public CityDetailEntity setOrderNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
    }
    public String getMessage() {
        return message;
    }
	public CityDetailEntity setMessage(String message) {
        this.message = message;
        return this;
    }
    public Integer getCitySellStatus() {
        return citySellStatus;
    }
	public CityDetailEntity setCitySellStatus(Integer citySellStatus) {
        this.citySellStatus = citySellStatus;
        return this;
    }
    public String getUrl() {
        return url;
    }
	public CityDetailEntity setUrl(String url) {
        this.url = url;
        return this;
    }
    public java.math.BigDecimal getAmount() {
        return amount;
    }
	public CityDetailEntity setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
        return this;
    }
    public String getCurrency() {
        return currency;
    }
	public CityDetailEntity setCurrency(String currency) {
        this.currency = currency;
        return this;
    }
    public Boolean getMessageStatus() {
        return messageStatus;
    }
	public CityDetailEntity setMessageStatus(Boolean messageStatus) {
        this.messageStatus = messageStatus;
        return this;
    }
    public java.util.Date getCreateTime() {
        return createTime;
    }
	public CityDetailEntity setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public java.util.Date getUpdateTime() {
        return updateTime;
    }
	public CityDetailEntity setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    public String getTxId() {
        return txId;
    }
	public CityDetailEntity setTxId(String txId) {
        this.txId = txId;
        return this;
    }
}