package com.yinmimoney.web.p2pnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class SConfigEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private java.lang.Integer id;
	private java.lang.String name;
	private java.lang.String nid;
	private java.lang.String value;
	private java.lang.Integer type;
	private java.lang.Integer status;
	private java.lang.String remark;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date addTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date updateTime;
	
    public java.lang.Integer getId() {
        return id;
    }
	public void setId(java.lang.Integer id) {
        this.id = id;
    }
    public java.lang.String getName() {
        return name;
    }
	public void setName(java.lang.String name) {
        this.name = name;
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
    public java.lang.Integer getType() {
        return type;
    }
	public void setType(java.lang.Integer type) {
        this.type = type;
    }
    public java.lang.Integer getStatus() {
        return status;
    }
	public void setStatus(java.lang.Integer status) {
        this.status = status;
    }
    public java.lang.String getRemark() {
        return remark;
    }
	public void setRemark(java.lang.String remark) {
        this.remark = remark;
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