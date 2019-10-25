package com.yinmimoney.web.p2pnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class CityEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String cityCode;
	private String cityName;
	private String cityNameAll;
	private java.math.BigDecimal lat;
	private java.math.BigDecimal lon;
	private Integer initStatus;
	private Boolean isLock;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date createTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date updateTime;
	private Boolean isShow;
	
    public Integer getId() {
        return id;
    }
	public CityEntity setId(Integer id) {
        this.id = id;
        return this;
    }
    public String getCityCode() {
        return cityCode;
    }
	public CityEntity setCityCode(String cityCode) {
        this.cityCode = cityCode;
        return this;
    }
    public String getCityName() {
        return cityName;
    }
	public CityEntity setCityName(String cityName) {
        this.cityName = cityName;
        return this;
    }
    public String getCityNameAll() {
        return cityNameAll;
    }
	public CityEntity setCityNameAll(String cityNameAll) {
        this.cityNameAll = cityNameAll;
        return this;
    }
    public java.math.BigDecimal getLat() {
        return lat;
    }
	public CityEntity setLat(java.math.BigDecimal lat) {
        this.lat = lat;
        return this;
    }
    public java.math.BigDecimal getLon() {
        return lon;
    }
	public CityEntity setLon(java.math.BigDecimal lon) {
        this.lon = lon;
        return this;
    }
    public Integer getInitStatus() {
        return initStatus;
    }
	public CityEntity setInitStatus(Integer initStatus) {
        this.initStatus = initStatus;
        return this;
    }
    public Boolean getIsLock() {
        return isLock;
    }
	public CityEntity setIsLock(Boolean isLock) {
        this.isLock = isLock;
        return this;
    }
    public java.util.Date getCreateTime() {
        return createTime;
    }
	public CityEntity setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public java.util.Date getUpdateTime() {
        return updateTime;
    }
	public CityEntity setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    public Boolean getIsShow() {
        return isShow;
    }
	public CityEntity setIsShow(Boolean isShow) {
        this.isShow = isShow;
        return this;
    }
}