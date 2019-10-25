package com.yinmimoney.web.p2pnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class AdminActionsEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private java.lang.Integer id;
	private java.lang.String name;
	private java.lang.String url;
	private java.lang.Integer pid;
	private java.lang.Integer level;
	private java.lang.Integer paixu;
	private java.lang.Boolean isMenu;
	private java.lang.String icon;
	private java.lang.String menuPosStr;
	private java.lang.Integer sysType;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date addTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date updateTime;
	
    public java.lang.Integer getId() {
        return id;
    }
	public void setId(java.lang.Integer id) {
        this.id = id;
    }
    public java.lang.String getName() {
        return name;
    }
	public void setName(java.lang.String name) {
        this.name = name;
    }
    public java.lang.String getUrl() {
        return url;
    }
	public void setUrl(java.lang.String url) {
        this.url = url;
    }
    public java.lang.Integer getPid() {
        return pid;
    }
	public void setPid(java.lang.Integer pid) {
        this.pid = pid;
    }
    public java.lang.Integer getLevel() {
        return level;
    }
	public void setLevel(java.lang.Integer level) {
        this.level = level;
    }
    public java.lang.Integer getPaixu() {
        return paixu;
    }
	public void setPaixu(java.lang.Integer paixu) {
        this.paixu = paixu;
    }
    public java.lang.Boolean getIsMenu() {
        return isMenu;
    }
	public void setIsMenu(java.lang.Boolean isMenu) {
        this.isMenu = isMenu;
    }
    public java.lang.String getIcon() {
        return icon;
    }
	public void setIcon(java.lang.String icon) {
        this.icon = icon;
    }
    public java.lang.String getMenuPosStr() {
        return menuPosStr;
    }
	public void setMenuPosStr(java.lang.String menuPosStr) {
        this.menuPosStr = menuPosStr;
    }
    public java.lang.Integer getSysType() {
        return sysType;
    }
	public void setSysType(java.lang.Integer sysType) {
        this.sysType = sysType;
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