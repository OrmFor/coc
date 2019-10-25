package com.yinmimoney.web.p2pnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class MessageSendEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String event;
	private String dataId;
	private String expandDataId;
	private Integer status;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date createTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date updateTime;
	
    public Integer getId() {
        return id;
    }
	public MessageSendEntity setId(Integer id) {
        this.id = id;
        return this;
    }
    public String getEvent() {
        return event;
    }
	public MessageSendEntity setEvent(String event) {
        this.event = event;
        return this;
    }
    public String getDataId() {
        return dataId;
    }
	public MessageSendEntity setDataId(String dataId) {
        this.dataId = dataId;
        return this;
    }
    public String getExpandDataId() {
        return expandDataId;
    }
	public MessageSendEntity setExpandDataId(String expandDataId) {
        this.expandDataId = expandDataId;
        return this;
    }
    public Integer getStatus() {
        return status;
    }
	public MessageSendEntity setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public java.util.Date getCreateTime() {
        return createTime;
    }
	public MessageSendEntity setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public java.util.Date getUpdateTime() {
        return updateTime;
    }
	public MessageSendEntity setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
}