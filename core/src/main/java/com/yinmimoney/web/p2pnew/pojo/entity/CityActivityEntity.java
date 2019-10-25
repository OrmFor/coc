package com.yinmimoney.web.p2pnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class CityActivityEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	private String cityName;
	private String cityCode;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date startTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date endTime;
	private String url;
	private String pictures;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date addTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date updateTime;
	private Integer isDelete;
	
    public Integer getId() {
        return id;
    }
	public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
	public void setName(String name) {
        this.name = name;
    }
    public String getCityName() {
        return cityName;
    }
	public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public String getCityCode() {
        return cityCode;
    }
	public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
    public java.util.Date getStartTime() {
        return startTime;
    }
	public void setStartTime(java.util.Date startTime) {
        this.startTime = startTime;
    }
    public java.util.Date getEndTime() {
        return endTime;
    }
	public void setEndTime(java.util.Date endTime) {
        this.endTime = endTime;
    }
    public String getUrl() {
        return url;
    }
	public void setUrl(String url) {
        this.url = url;
    }
    public String getPictures() {
        return pictures;
    }
	public void setPictures(String pictures) {
        this.pictures = pictures;
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
    public Integer getIsDelete() {
        return isDelete;
    }
	public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}