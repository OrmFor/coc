package com.yinmimoney.web.p2pnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class NoticeEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private java.lang.Integer id;
	private java.lang.String nid;
	private java.lang.Integer type;
	private java.lang.String sentUser;
	private java.lang.String receiveUser;
	private java.lang.String receiveAddr;
	private java.lang.Integer status;
	private java.lang.String title;
	private java.lang.String content;
	private java.lang.String code;
	private java.lang.String result;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date addTime;
	
    public java.lang.Integer getId() {
        return id;
    }
	public void setId(java.lang.Integer id) {
        this.id = id;
    }
    public java.lang.String getNid() {
        return nid;
    }
	public void setNid(java.lang.String nid) {
        this.nid = nid;
    }
    public java.lang.Integer getType() {
        return type;
    }
	public void setType(java.lang.Integer type) {
        this.type = type;
    }
    public java.lang.String getSentUser() {
        return sentUser;
    }
	public void setSentUser(java.lang.String sentUser) {
        this.sentUser = sentUser;
    }
    public java.lang.String getReceiveUser() {
        return receiveUser;
    }
	public void setReceiveUser(java.lang.String receiveUser) {
        this.receiveUser = receiveUser;
    }
    public java.lang.String getReceiveAddr() {
        return receiveAddr;
    }
	public void setReceiveAddr(java.lang.String receiveAddr) {
        this.receiveAddr = receiveAddr;
    }
    public java.lang.Integer getStatus() {
        return status;
    }
	public void setStatus(java.lang.Integer status) {
        this.status = status;
    }
    public java.lang.String getTitle() {
        return title;
    }
	public void setTitle(java.lang.String title) {
        this.title = title;
    }
    public java.lang.String getContent() {
        return content;
    }
	public void setContent(java.lang.String content) {
        this.content = content;
    }
    public java.lang.String getCode() {
        return code;
    }
	public void setCode(java.lang.String code) {
        this.code = code;
    }
    public java.lang.String getResult() {
        return result;
    }
	public void setResult(java.lang.String result) {
        this.result = result;
    }
    public java.util.Date getAddTime() {
        return addTime;
    }
	public void setAddTime(java.util.Date addTime) {
        this.addTime = addTime;
    }
}