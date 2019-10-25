package com.yinmimoney.web.p2pnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class ChatHouseEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String cityCode;
	private String houseName;
	private String beforeName;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date addTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date updateTime;
	
    public Integer getId() {
        return id;
    }
	public void setId(Integer id) {
        this.id = id;
    }
    public String getCityCode() {
        return cityCode;
    }
	public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
    public String getHouseName() {
        return houseName;
    }
	public void setHouseName(String houseName) {
        this.houseName = houseName;
    }
    public String getBeforeName() {
        return beforeName;
    }
	public void setBeforeName(String beforeName) {
        this.beforeName = beforeName;
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