package com.yinmimoney.web.p2pnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class LikeLogEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String userCode;
	private String cityCode;
	private String chatCode;
	private Integer isValid;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date addTime;
	
    public Integer getId() {
        return id;
    }
	public void setId(Integer id) {
        this.id = id;
    }
    public String getUserCode() {
        return userCode;
    }
	public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
    public String getCityCode() {
        return cityCode;
    }
	public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
    public String getChatCode() {
        return chatCode;
    }
	public void setChatCode(String chatCode) {
        this.chatCode = chatCode;
    }
    public Integer getIsValid() {
        return isValid;
    }
	public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }
    public java.util.Date getAddTime() {
        return addTime;
    }
	public void setAddTime(java.util.Date addTime) {
        this.addTime = addTime;
    }
}