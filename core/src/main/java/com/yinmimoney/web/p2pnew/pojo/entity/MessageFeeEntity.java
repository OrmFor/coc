package com.yinmimoney.web.p2pnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class MessageFeeEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String txid;
	private String userCode;
	private String cityCode;
	private java.math.BigDecimal amount;
	private String currency;
	private String type;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date createTime;
	private String cityName;
	
    public Integer getId() {
        return id;
    }
	public MessageFeeEntity setId(Integer id) {
        this.id = id;
        return this;
    }
    public String getTxid() {
        return txid;
    }
	public MessageFeeEntity setTxid(String txid) {
        this.txid = txid;
        return this;
    }
    public String getUserCode() {
        return userCode;
    }
	public MessageFeeEntity setUserCode(String userCode) {
        this.userCode = userCode;
        return this;
    }
    public String getCityCode() {
        return cityCode;
    }
	public MessageFeeEntity setCityCode(String cityCode) {
        this.cityCode = cityCode;
        return this;
    }
    public java.math.BigDecimal getAmount() {
        return amount;
    }
	public MessageFeeEntity setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
        return this;
    }
    public String getCurrency() {
        return currency;
    }
	public MessageFeeEntity setCurrency(String currency) {
        this.currency = currency;
        return this;
    }
    public String getType() {
        return type;
    }
	public MessageFeeEntity setType(String type) {
        this.type = type;
        return this;
    }
    public java.util.Date getCreateTime() {
        return createTime;
    }
	public MessageFeeEntity setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public String getCityName() {
        return cityName;
    }
	public MessageFeeEntity setCityName(String cityName) {
        this.cityName = cityName;
        return this;
    }
}