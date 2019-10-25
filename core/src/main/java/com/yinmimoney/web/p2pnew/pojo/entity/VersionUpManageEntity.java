package com.yinmimoney.web.p2pnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class VersionUpManageEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private java.lang.Integer id; // ID
	private java.lang.String versionCode; // 版本编号
	private java.lang.String versionContent; // 版本内容
	private java.lang.String versionNumber; // 版本号
	private java.lang.Integer versionLevel; // 版本等级，1：低，2：中，3：高
	private java.lang.String updateUrl; // 更新地址
	private java.lang.String channel; // 渠道号
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date addTime; // 添加时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date updateTime; // 更新时间
	
    public java.lang.Integer getId() {
        return id;
    }
	public void setId(java.lang.Integer id) {
        this.id = id;
    }
    public java.lang.String getVersionCode() {
        return versionCode;
    }
	public void setVersionCode(java.lang.String versionCode) {
        this.versionCode = versionCode;
    }
    public java.lang.String getVersionContent() {
        return versionContent;
    }
	public void setVersionContent(java.lang.String versionContent) {
        this.versionContent = versionContent;
    }
    public java.lang.String getVersionNumber() {
        return versionNumber;
    }
	public void setVersionNumber(java.lang.String versionNumber) {
        this.versionNumber = versionNumber;
    }
    public java.lang.Integer getVersionLevel() {
        return versionLevel;
    }
	public void setVersionLevel(java.lang.Integer versionLevel) {
        this.versionLevel = versionLevel;
    }
    public java.lang.String getUpdateUrl() {
        return updateUrl;
    }
	public void setUpdateUrl(java.lang.String updateUrl) {
        this.updateUrl = updateUrl;
    }
    public java.lang.String getChannel() {
        return channel;
    }
	public void setChannel(java.lang.String channel) {
        this.channel = channel;
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