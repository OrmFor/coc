package com.yinmimoney.web.p2pnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class SysLogEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer adminId;
	private String moduleType;
	private String oprateType;
	private String name;
	private String uri;
	private String msg;
	private String ip;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date dateAdd;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date updatedAt;

    public Integer getId() {
        return id;
    }
	public void setId(Integer id) {
        this.id = id;
    }
    public Integer getAdminId() {
        return adminId;
    }
	public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }
    public String getModuleType() {
        return moduleType;
    }
	public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }
    public String getOprateType() {
        return oprateType;
    }
	public void setOprateType(String oprateType) {
        this.oprateType = oprateType;
    }
    public String getName() {
        return name;
    }
	public void setName(String name) {
        this.name = name;
    }
    public String getUri() {
        return uri;
    }
	public void setUri(String uri) {
        this.uri = uri;
    }
    public String getMsg() {
        return msg;
    }
	public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getIp() {
        return ip;
    }
	public void setIp(String ip) {
        this.ip = ip;
    }
    public java.util.Date getDateAdd() {
        return dateAdd;
    }
	public void setDateAdd(java.util.Date dateAdd) {
        this.dateAdd = dateAdd;
    }
    public java.util.Date getUpdatedAt() {
        return updatedAt;
    }
	public void setUpdatedAt(java.util.Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}