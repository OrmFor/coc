package com.yinmimoney.web.p2pnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class BlackListEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String userCode;
	private String permissionsNid;
	private Integer isValid;
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
    public String getUserCode() {
        return userCode;
    }
	public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
    public String getPermissionsNid() {
        return permissionsNid;
    }
	public void setPermissionsNid(String permissionsNid) {
        this.permissionsNid = permissionsNid;
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
    public java.util.Date getUpdateTime() {
        return updateTime;
    }
	public void setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
    }
}