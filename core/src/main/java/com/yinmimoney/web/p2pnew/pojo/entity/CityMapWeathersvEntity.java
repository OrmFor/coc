package com.yinmimoney.web.p2pnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;

public class CityMapWeathersvEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String cityCode;
	private String cityName;
	private java.math.BigDecimal lon;
	private java.math.BigDecimal lat;
	private String wsId;
	private String wsCityName;
	private String country;
	private java.math.BigDecimal distance;
	private Integer population;
	private Integer isShow;
	
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
    public String getCityName() {
        return cityName;
    }
	public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public java.math.BigDecimal getLon() {
        return lon;
    }
	public void setLon(java.math.BigDecimal lon) {
        this.lon = lon;
    }
    public java.math.BigDecimal getLat() {
        return lat;
    }
	public void setLat(java.math.BigDecimal lat) {
        this.lat = lat;
    }
    public String getWsId() {
        return wsId;
    }
	public void setWsId(String wsId) {
        this.wsId = wsId;
    }
    public String getWsCityName() {
        return wsCityName;
    }
	public void setWsCityName(String wsCityName) {
        this.wsCityName = wsCityName;
    }
    public String getCountry() {
        return country;
    }
	public void setCountry(String country) {
        this.country = country;
    }
    public java.math.BigDecimal getDistance() {
        return distance;
    }
	public void setDistance(java.math.BigDecimal distance) {
        this.distance = distance;
    }
    public Integer getPopulation() {
        return population;
    }
	public void setPopulation(Integer population) {
        this.population = population;
    }
    public Integer getIsShow() {
        return isShow;
    }
	public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }
}