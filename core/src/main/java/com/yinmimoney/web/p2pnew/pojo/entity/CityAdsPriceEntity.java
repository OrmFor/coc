package com.yinmimoney.web.p2pnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class CityAdsPriceEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String orderNo;
	private String txid;
	private java.math.BigDecimal unitPrice;
	private String unit;
	private Integer time;
	private java.math.BigDecimal amount;
	private String cityCode;
	private String cityName;
	private String userCode;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date createTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date expireTime;
	private Boolean isExpire;
	
    public Integer getId() {
        return id;
    }
	public CityAdsPriceEntity setId(Integer id) {
        this.id = id;
        return this;
    }
    public String getOrderNo() {
        return orderNo;
    }
	public CityAdsPriceEntity setOrderNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
    }
    public String getTxid() {
        return txid;
    }
	public CityAdsPriceEntity setTxid(String txid) {
        this.txid = txid;
        return this;
    }
    public java.math.BigDecimal getUnitPrice() {
        return unitPrice;
    }
	public CityAdsPriceEntity setUnitPrice(java.math.BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }
    public String getUnit() {
        return unit;
    }
	public CityAdsPriceEntity setUnit(String unit) {
        this.unit = unit;
        return this;
    }
    public Integer getTime() {
        return time;
    }
	public CityAdsPriceEntity setTime(Integer time) {
        this.time = time;
        return this;
    }
    public java.math.BigDecimal getAmount() {
        return amount;
    }
	public CityAdsPriceEntity setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
        return this;
    }
    public String getCityCode() {
        return cityCode;
    }
	public CityAdsPriceEntity setCityCode(String cityCode) {
        this.cityCode = cityCode;
        return this;
    }
    public String getCityName() {
        return cityName;
    }
	public CityAdsPriceEntity setCityName(String cityName) {
        this.cityName = cityName;
        return this;
    }
    public String getUserCode() {
        return userCode;
    }
	public CityAdsPriceEntity setUserCode(String userCode) {
        this.userCode = userCode;
        return this;
    }
    public java.util.Date getCreateTime() {
        return createTime;
    }
	public CityAdsPriceEntity setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public java.util.Date getExpireTime() {
        return expireTime;
    }
	public CityAdsPriceEntity setExpireTime(java.util.Date expireTime) {
        this.expireTime = expireTime;
        return this;
    }
    public Boolean getIsExpire() {
        return isExpire;
    }
	public CityAdsPriceEntity setIsExpire(Boolean isExpire) {
        this.isExpire = isExpire;
        return this;
    }
}