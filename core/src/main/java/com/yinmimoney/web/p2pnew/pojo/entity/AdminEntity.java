package com.yinmimoney.web.p2pnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class AdminEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private java.lang.Integer id;
	private java.lang.String userCode;
	private java.lang.String userName;
	private java.lang.String realName;
	private java.lang.String mobilePhone;
	private java.lang.String pwd;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date loginTime;
	private java.lang.String loginIp;
	private java.lang.Integer status;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date registerTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date updateTime;
	
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
    public java.lang.String getUserName() {
        return userName;
    }
	public void setUserName(java.lang.String userName) {
        this.userName = userName;
    }
    public java.lang.String getRealName() {
        return realName;
    }
	public void setRealName(java.lang.String realName) {
        this.realName = realName;
    }
    public java.lang.String getMobilePhone() {
        return mobilePhone;
    }
	public void setMobilePhone(java.lang.String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
    public java.lang.String getPwd() {
        return pwd;
    }
	public void setPwd(java.lang.String pwd) {
        this.pwd = pwd;
    }
    public java.util.Date getLoginTime() {
        return loginTime;
    }
	public void setLoginTime(java.util.Date loginTime) {
        this.loginTime = loginTime;
    }
    public java.lang.String getLoginIp() {
        return loginIp;
    }
	public void setLoginIp(java.lang.String loginIp) {
        this.loginIp = loginIp;
    }
    public java.lang.Integer getStatus() {
        return status;
    }
	public void setStatus(java.lang.Integer status) {
        this.status = status;
    }
    public java.util.Date getRegisterTime() {
        return registerTime;
    }
	public void setRegisterTime(java.util.Date registerTime) {
        this.registerTime = registerTime;
    }
    public java.util.Date getUpdateTime() {
        return updateTime;
    }
	public void setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
    }
}