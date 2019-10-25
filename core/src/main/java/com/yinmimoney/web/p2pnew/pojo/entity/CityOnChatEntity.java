package com.yinmimoney.web.p2pnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class CityOnChatEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String code;
	private String cityCode;
	private String content;
	private String userCode;
	private String userName;
	private Integer likeNumber;
	private Integer version;
	private String txid;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date addTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date updateTime;
	private Integer isHidden;
	private Integer isValid;
	private String speakType;
	
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
    public String getCityCode() {
        return cityCode;
    }
	public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
    public String getContent() {
        return content;
    }
	public void setContent(String content) {
        this.content = content;
    }
    public String getUserCode() {
        return userCode;
    }
	public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
    public String getUserName() {
        return userName;
    }
	public void setUserName(String userName) {
        this.userName = userName;
    }
    public Integer getLikeNumber() {
        return likeNumber;
    }
	public void setLikeNumber(Integer likeNumber) {
        this.likeNumber = likeNumber;
    }
    public Integer getVersion() {
        return version;
    }
	public void setVersion(Integer version) {
        this.version = version;
    }
    public String getTxid() {
        return txid;
    }
	public void setTxid(String txid) {
        this.txid = txid;
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
    public Integer getIsHidden() {
        return isHidden;
    }
	public void setIsHidden(Integer isHidden) {
        this.isHidden = isHidden;
    }
    public Integer getIsValid() {
        return isValid;
    }
	public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }
    public String getSpeakType() {
        return speakType;
    }
	public void setSpeakType(String speakType) {
        this.speakType = speakType;
    }
}