package com.yinmimoney.web.p2pnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class CityPictureEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String pictureCode;
	private String orderNo;
	private String txid;
	private String cityCode;
	private String userCode;
	private Integer likeNumber;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date createTime;
	private String cityName;
	private Boolean isShow;
	private Integer version;
	
    public Integer getId() {
        return id;
    }
	public CityPictureEntity setId(Integer id) {
        this.id = id;
        return this;
    }
    public String getPictureCode() {
        return pictureCode;
    }
	public CityPictureEntity setPictureCode(String pictureCode) {
        this.pictureCode = pictureCode;
        return this;
    }
    public String getOrderNo() {
        return orderNo;
    }
	public CityPictureEntity setOrderNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
    }
    public String getTxid() {
        return txid;
    }
	public CityPictureEntity setTxid(String txid) {
        this.txid = txid;
        return this;
    }
    public String getCityCode() {
        return cityCode;
    }
	public CityPictureEntity setCityCode(String cityCode) {
        this.cityCode = cityCode;
        return this;
    }
    public String getUserCode() {
        return userCode;
    }
	public CityPictureEntity setUserCode(String userCode) {
        this.userCode = userCode;
        return this;
    }
    public Integer getLikeNumber() {
        return likeNumber;
    }
	public CityPictureEntity setLikeNumber(Integer likeNumber) {
        this.likeNumber = likeNumber;
        return this;
    }
    public java.util.Date getCreateTime() {
        return createTime;
    }
	public CityPictureEntity setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public String getCityName() {
        return cityName;
    }
	public CityPictureEntity setCityName(String cityName) {
        this.cityName = cityName;
        return this;
    }
    public Boolean getIsShow() {
        return isShow;
    }
	public CityPictureEntity setIsShow(Boolean isShow) {
        this.isShow = isShow;
        return this;
    }
    public Integer getVersion() {
        return version;
    }
	public CityPictureEntity setVersion(Integer version) {
        this.version = version;
        return this;
    }
}