package com.yinmimoney.web.p2pnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class AdsPictureEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String orderNo;
	private String txid;
	private String cityCode;
	private String adsUrl;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date createTime;
	private String cityName;
	private Integer orderNum;
	private Boolean isShow;
	
    public Integer getId() {
        return id;
    }
	public AdsPictureEntity setId(Integer id) {
        this.id = id;
        return this;
    }
    public String getOrderNo() {
        return orderNo;
    }
	public AdsPictureEntity setOrderNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
    }
    public String getTxid() {
        return txid;
    }
	public AdsPictureEntity setTxid(String txid) {
        this.txid = txid;
        return this;
    }
    public String getCityCode() {
        return cityCode;
    }
	public AdsPictureEntity setCityCode(String cityCode) {
        this.cityCode = cityCode;
        return this;
    }
    public String getAdsUrl() {
        return adsUrl;
    }
	public AdsPictureEntity setAdsUrl(String adsUrl) {
        this.adsUrl = adsUrl;
        return this;
    }
    public java.util.Date getCreateTime() {
        return createTime;
    }
	public AdsPictureEntity setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public String getCityName() {
        return cityName;
    }
	public AdsPictureEntity setCityName(String cityName) {
        this.cityName = cityName;
        return this;
    }
    public Integer getOrderNum() {
        return orderNum;
    }
	public AdsPictureEntity setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
        return this;
    }
    public Boolean getIsShow() {
        return isShow;
    }
	public AdsPictureEntity setIsShow(Boolean isShow) {
        this.isShow = isShow;
        return this;
    }
}