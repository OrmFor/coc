package com.yinmimoney.web.p2pnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class CityHotEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String cityCode;
	private String cityName;
	private Integer count;
	private String houseName;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date createTime;
	private String createTimeStr;
	
    public Integer getId() {
        return id;
    }
	public CityHotEntity setId(Integer id) {
        this.id = id;
        return this;
    }
    public String getCityCode() {
        return cityCode;
    }
	public CityHotEntity setCityCode(String cityCode) {
        this.cityCode = cityCode;
        return this;
    }
    public String getCityName() {
        return cityName;
    }
	public CityHotEntity setCityName(String cityName) {
        this.cityName = cityName;
        return this;
    }
    public Integer getCount() {
        return count;
    }
	public CityHotEntity setCount(Integer count) {
        this.count = count;
        return this;
    }
    public String getHouseName() {
        return houseName;
    }
	public CityHotEntity setHouseName(String houseName) {
        this.houseName = houseName;
        return this;
    }
    public java.util.Date getCreateTime() {
        return createTime;
    }
	public CityHotEntity setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public String getCreateTimeStr() {
        return createTimeStr;
    }
	public CityHotEntity setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
        return this;
    }
}