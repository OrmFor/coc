package com.yinmimoney.web.p2pnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class RolesEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private java.lang.Integer id;
	private java.lang.String name;
	private java.lang.String profile;
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
    public java.lang.String getProfile() {
        return profile;
    }
	public void setProfile(java.lang.String profile) {
        this.profile = profile;
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