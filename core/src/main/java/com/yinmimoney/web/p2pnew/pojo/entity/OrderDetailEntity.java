package com.yinmimoney.web.p2pnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class OrderDetailEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String orderNo;
	private String userCode;
	private java.math.BigDecimal amount;
	private String currency;
	private String type;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date createTime;
	
    public Integer getId() {
        return id;
    }
	public OrderDetailEntity setId(Integer id) {
        this.id = id;
        return this;
    }
    public String getOrderNo() {
        return orderNo;
    }
	public OrderDetailEntity setOrderNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
    }
    public String getUserCode() {
        return userCode;
    }
	public OrderDetailEntity setUserCode(String userCode) {
        this.userCode = userCode;
        return this;
    }
    public java.math.BigDecimal getAmount() {
        return amount;
    }
	public OrderDetailEntity setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
        return this;
    }
    public String getCurrency() {
        return currency;
    }
	public OrderDetailEntity setCurrency(String currency) {
        this.currency = currency;
        return this;
    }
    public String getType() {
        return type;
    }
	public OrderDetailEntity setType(String type) {
        this.type = type;
        return this;
    }
    public java.util.Date getCreateTime() {
        return createTime;
    }
	public OrderDetailEntity setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
        return this;
    }
}