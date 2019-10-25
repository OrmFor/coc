package com.yinmimoney.web.p2pnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;

public class UserInviteCodeMaxEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private java.lang.Integer id;// 
	private java.lang.Long curNumber;// 
	
    public java.lang.Integer getId() {
        return id;
    }
	public void setId(java.lang.Integer id) {
        this.id = id;
    }
    public java.lang.Long getCurNumber() {
        return curNumber;
    }
	public void setCurNumber(java.lang.Long curNumber) {
        this.curNumber = curNumber;
    }
}