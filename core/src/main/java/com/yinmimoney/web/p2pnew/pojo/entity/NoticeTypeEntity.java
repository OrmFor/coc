package com.yinmimoney.web.p2pnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class NoticeTypeEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private java.lang.Integer id;
	private java.lang.Integer type;
	private java.lang.String nid;
	private java.lang.Integer noticeType;
	private java.lang.String name;
	private java.lang.String titleTemplet;
	private java.lang.String templet;
	private java.lang.Integer send;
	private java.lang.Integer canSwitch;
	private java.lang.String remark;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date addTime;
	private java.lang.String addIp;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date updateTime;
	private java.lang.String updateIp;
	
    public java.lang.Integer getId() {
        return id;
    }
	public void setId(java.lang.Integer id) {
        this.id = id;
    }
    public java.lang.Integer getType() {
        return type;
    }
	public void setType(java.lang.Integer type) {
        this.type = type;
    }
    public java.lang.String getNid() {
        return nid;
    }
	public void setNid(java.lang.String nid) {
        this.nid = nid;
    }
    public java.lang.Integer getNoticeType() {
        return noticeType;
    }
	public void setNoticeType(java.lang.Integer noticeType) {
        this.noticeType = noticeType;
    }
    public java.lang.String getName() {
        return name;
    }
	public void setName(java.lang.String name) {
        this.name = name;
    }
    public java.lang.String getTitleTemplet() {
        return titleTemplet;
    }
	public void setTitleTemplet(java.lang.String titleTemplet) {
        this.titleTemplet = titleTemplet;
    }
    public java.lang.String getTemplet() {
        return templet;
    }
	public void setTemplet(java.lang.String templet) {
        this.templet = templet;
    }
    public java.lang.Integer getSend() {
        return send;
    }
	public void setSend(java.lang.Integer send) {
        this.send = send;
    }
    public java.lang.Integer getCanSwitch() {
        return canSwitch;
    }
	public void setCanSwitch(java.lang.Integer canSwitch) {
        this.canSwitch = canSwitch;
    }
    public java.lang.String getRemark() {
        return remark;
    }
	public void setRemark(java.lang.String remark) {
        this.remark = remark;
    }
    public java.util.Date getAddTime() {
        return addTime;
    }
	public void setAddTime(java.util.Date addTime) {
        this.addTime = addTime;
    }
    public java.lang.String getAddIp() {
        return addIp;
    }
	public void setAddIp(java.lang.String addIp) {
        this.addIp = addIp;
    }
    public java.util.Date getUpdateTime() {
        return updateTime;
    }
	public void setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
    }
    public java.lang.String getUpdateIp() {
        return updateIp;
    }
	public void setUpdateIp(java.lang.String updateIp) {
        this.updateIp = updateIp;
    }
}