package com.yinmimoney.web.p2pnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class OrderEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String buyerCode;
	private String sellerCode;
	private String cityCode;
	private String orderNo;
	private Integer status;
	private java.math.BigDecimal amount;
	private String currency;
	private String txid;
	private String normalizedTxid;
	private String satoshis;
	private String buttonData;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date createTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date updateTime;
	private java.math.BigDecimal platAmount;
	private java.math.BigDecimal sellerAmount;
	
    public Integer getId() {
        return id;
    }
	public OrderEntity setId(Integer id) {
        this.id = id;
        return this;
    }
    public String getBuyerCode() {
        return buyerCode;
    }
	public OrderEntity setBuyerCode(String buyerCode) {
        this.buyerCode = buyerCode;
        return this;
    }
    public String getSellerCode() {
        return sellerCode;
    }
	public OrderEntity setSellerCode(String sellerCode) {
        this.sellerCode = sellerCode;
        return this;
    }
    public String getCityCode() {
        return cityCode;
    }
	public OrderEntity setCityCode(String cityCode) {
        this.cityCode = cityCode;
        return this;
    }
    public String getOrderNo() {
        return orderNo;
    }
	public OrderEntity setOrderNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
    }
    public Integer getStatus() {
        return status;
    }
	public OrderEntity setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public java.math.BigDecimal getAmount() {
        return amount;
    }
	public OrderEntity setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
        return this;
    }
    public String getCurrency() {
        return currency;
    }
	public OrderEntity setCurrency(String currency) {
        this.currency = currency;
        return this;
    }
    public String getTxid() {
        return txid;
    }
	public OrderEntity setTxid(String txid) {
        this.txid = txid;
        return this;
    }
    public String getNormalizedTxid() {
        return normalizedTxid;
    }
	public OrderEntity setNormalizedTxid(String normalizedTxid) {
        this.normalizedTxid = normalizedTxid;
        return this;
    }
    public String getSatoshis() {
        return satoshis;
    }
	public OrderEntity setSatoshis(String satoshis) {
        this.satoshis = satoshis;
        return this;
    }
    public String getButtonData() {
        return buttonData;
    }
	public OrderEntity setButtonData(String buttonData) {
        this.buttonData = buttonData;
        return this;
    }
    public java.util.Date getCreateTime() {
        return createTime;
    }
	public OrderEntity setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public java.util.Date getUpdateTime() {
        return updateTime;
    }
	public OrderEntity setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    public java.math.BigDecimal getPlatAmount() {
        return platAmount;
    }
	public OrderEntity setPlatAmount(java.math.BigDecimal platAmount) {
        this.platAmount = platAmount;
        return this;
    }
    public java.math.BigDecimal getSellerAmount() {
        return sellerAmount;
    }
	public OrderEntity setSellerAmount(java.math.BigDecimal sellerAmount) {
        this.sellerAmount = sellerAmount;
        return this;
    }
}