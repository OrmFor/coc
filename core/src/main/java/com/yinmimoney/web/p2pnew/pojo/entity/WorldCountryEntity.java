package com.yinmimoney.web.p2pnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class WorldCountryEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String code;
	private String countryNameZhHans;
	private String countryNameZhHant;
	private String countryNameEn;
	private String continent;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date addTime;
	private String pinyin;
	private String pinyinPrefix;
	
    public Integer getId() {
        return id;
    }
	public void setId(Integer id) {
        this.id = id;
    }
    public String getCode() {
        return code;
    }
	public void setCode(String code) {
        this.code = code;
    }
    public String getCountryNameZhHans() {
        return countryNameZhHans;
    }
	public void setCountryNameZhHans(String countryNameZhHans) {
        this.countryNameZhHans = countryNameZhHans;
    }
    public String getCountryNameZhHant() {
        return countryNameZhHant;
    }
	public void setCountryNameZhHant(String countryNameZhHant) {
        this.countryNameZhHant = countryNameZhHant;
    }
    public String getCountryNameEn() {
        return countryNameEn;
    }
	public void setCountryNameEn(String countryNameEn) {
        this.countryNameEn = countryNameEn;
    }
    public String getContinent() {
        return continent;
    }
	public void setContinent(String continent) {
        this.continent = continent;
    }
    public java.util.Date getAddTime() {
        return addTime;
    }
	public void setAddTime(java.util.Date addTime) {
        this.addTime = addTime;
    }
    public String getPinyin() {
        return pinyin;
    }
	public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
    public String getPinyinPrefix() {
        return pinyinPrefix;
    }
	public void setPinyinPrefix(String pinyinPrefix) {
        this.pinyinPrefix = pinyinPrefix;
    }
}