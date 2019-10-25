package com.yinmimoney.web.p2pnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class SLogTemplateEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private java.lang.Integer id;// ID
	private java.lang.String nid;// 模板类型
	private java.lang.String value;// 模板信息
	private java.lang.String name;// 名称
	private java.lang.String remark;// 模板备注
	private java.lang.Integer paymentsType;// 收支明细 0：不变，1：收入，2：支出，3：充值，4：提现，5：出售
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date addTime;// 添加时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date updateTime;// 更新时间
	
    public java.lang.Integer getId() {
        return id;
    }
	public void setId(java.lang.Integer id) {
        this.id = id;
    }
    public java.lang.String getNid() {
        return nid;
    }
	public void setNid(java.lang.String nid) {
        this.nid = nid;
    }
    public java.lang.String getValue() {
        return value;
    }
	public void setValue(java.lang.String value) {
        this.value = value;
    }
    public java.lang.String getName() {
        return name;
    }
	public void setName(java.lang.String name) {
        this.name = name;
    }
    public java.lang.String getRemark() {
        return remark;
    }
	public void setRemark(java.lang.String remark) {
        this.remark = remark;
    }
    public java.lang.Integer getPaymentsType() {
        return paymentsType;
    }
	public void setPaymentsType(java.lang.Integer paymentsType) {
        this.paymentsType = paymentsType;
    }
    public java.util.Date getAddTime() {
        return addTime;
    }
	public void setAddTime(java.util.Date addTime) {
        this.addTime = addTime;
    }
    public java.util.Date getUpdateTime() {
        return updateTime;
    }
	public void setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
    }
}