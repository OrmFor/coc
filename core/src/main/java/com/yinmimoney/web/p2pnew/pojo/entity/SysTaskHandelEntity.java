package com.yinmimoney.web.p2pnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class SysTaskHandelEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private java.lang.Integer id;
	private java.lang.String moduleName;
	private java.lang.String hostname;
	private java.lang.String ipAddress;
	private java.lang.Boolean isEnabled;
	private java.lang.String remark;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date createdAt;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date updatedAt;
	
    public java.lang.Integer getId() {
        return id;
    }
	public void setId(java.lang.Integer id) {
        this.id = id;
    }
    public java.lang.String getModuleName() {
        return moduleName;
    }
	public void setModuleName(java.lang.String moduleName) {
        this.moduleName = moduleName;
    }
    public java.lang.String getHostname() {
        return hostname;
    }
	public void setHostname(java.lang.String hostname) {
        this.hostname = hostname;
    }
    public java.lang.String getIpAddress() {
        return ipAddress;
    }
	public void setIpAddress(java.lang.String ipAddress) {
        this.ipAddress = ipAddress;
    }
    public java.lang.Boolean getIsEnabled() {
        return isEnabled;
    }
	public void setIsEnabled(java.lang.Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }
    public java.lang.String getRemark() {
        return remark;
    }
	public void setRemark(java.lang.String remark) {
        this.remark = remark;
    }
    public java.util.Date getCreatedAt() {
        return createdAt;
    }
	public void setCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
    }
    public java.util.Date getUpdatedAt() {
        return updatedAt;
    }
	public void setUpdatedAt(java.util.Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}