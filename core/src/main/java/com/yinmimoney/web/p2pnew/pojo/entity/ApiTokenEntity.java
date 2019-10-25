package com.yinmimoney.web.p2pnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class ApiTokenEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private java.lang.Integer id;
	private java.lang.String userCode;
	private java.lang.String deviceId;
	private java.lang.String deviceName;
	private java.lang.String token;
	private java.lang.Integer status;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date changeTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date dateAdd;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date dateUpdate;
	
    public java.lang.Integer getId() {
        return id;
    }
	public void setId(java.lang.Integer id) {
        this.id = id;
    }
    public java.lang.String getUserCode() {
        return userCode;
    }
	public void setUserCode(java.lang.String userCode) {
        this.userCode = userCode;
    }
    public java.lang.String getDeviceId() {
        return deviceId;
    }
	public void setDeviceId(java.lang.String deviceId) {
        this.deviceId = deviceId;
    }
    public java.lang.String getDeviceName() {
        return deviceName;
    }
	public void setDeviceName(java.lang.String deviceName) {
        this.deviceName = deviceName;
    }
    public java.lang.String getToken() {
        return token;
    }
	public void setToken(java.lang.String token) {
        this.token = token;
    }
    public java.lang.Integer getStatus() {
        return status;
    }
	public void setStatus(java.lang.Integer status) {
        this.status = status;
    }
    public java.util.Date getChangeTime() {
        return changeTime;
    }
	public void setChangeTime(java.util.Date changeTime) {
        this.changeTime = changeTime;
    }
    public java.util.Date getDateAdd() {
        return dateAdd;
    }
	public void setDateAdd(java.util.Date dateAdd) {
        this.dateAdd = dateAdd;
    }
    public java.util.Date getDateUpdate() {
        return dateUpdate;
    }
	public void setDateUpdate(java.util.Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }
}