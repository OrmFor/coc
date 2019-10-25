package com.yinmimoney.web.p2pnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class CommonOrderEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String orderNo;
	private String operatorCode;
	private String operateType;
	private String cityCode;
	private String txid;
	private Integer status;
	private java.math.BigDecimal amount;
	private String currency;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date createTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date updateTime;
	private String acceptorCode;
	
    public Integer getId() {
        return id;
    }
	public void setId(Integer id) {
        this.id = id;
    }
    public String getOrderNo() {
        return orderNo;
    }
	public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public String getOperatorCode() {
        return operatorCode;
    }
	public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }
    public String getOperateType() {
        return operateType;
    }
	public void setOperateType(String operateType) {
        this.operateType = operateType;
    }
    public String getCityCode() {
        return cityCode;
    }
	public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
    public String getTxid() {
        return txid;
    }
	public void setTxid(String txid) {
        this.txid = txid;
    }
    public Integer getStatus() {
        return status;
    }
	public void setStatus(Integer status) {
        this.status = status;
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
    public java.util.Date getCreateTime() {
        return createTime;
    }
	public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }
    public java.util.Date getUpdateTime() {
        return updateTime;
    }
	public void setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
    }
    public String getAcceptorCode() {
        return acceptorCode;
    }
	public void setAcceptorCode(String acceptorCode) {
        this.acceptorCode = acceptorCode;
    }
}