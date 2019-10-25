package com.yinmimoney.web.p2pnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class BlackPermissionsEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nid;
	private String name;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date addTime;
	
    public Integer getId() {
        return id;
    }
	public void setId(Integer id) {
        this.id = id;
    }
    public String getNid() {
        return nid;
    }
	public void setNid(String nid) {
        this.nid = nid;
    }
    public String getName() {
        return name;
    }
	public void setName(String name) {
        this.name = name;
    }
    public java.util.Date getAddTime() {
        return addTime;
    }
	public void setAddTime(java.util.Date addTime) {
        this.addTime = addTime;
    }
}