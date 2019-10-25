package com.yinmimoney.web.p2pnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class UserEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String userCode;
	private String userName;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date registerTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date updateTime;
	
    public Integer getId() {
        return id;
    }
	public UserEntity setId(Integer id) {
        this.id = id;
        return this;
    }
    public String getUserCode() {
        return userCode;
    }
	public UserEntity setUserCode(String userCode) {
        this.userCode = userCode;
        return this;
    }
    public String getUserName() {
        return userName;
    }
	public UserEntity setUserName(String userName) {
        this.userName = userName;
        return this;
    }
    public java.util.Date getRegisterTime() {
        return registerTime;
    }
	public UserEntity setRegisterTime(java.util.Date registerTime) {
        this.registerTime = registerTime;
        return this;
    }
    public java.util.Date getUpdateTime() {
        return updateTime;
    }
	public UserEntity setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
}