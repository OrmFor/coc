package com.yinmimoney.web.p2pnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class CityDetailHistoryEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer hisId;
	private String cityCode;
	private String hisUserCode;
	private String hisOrderNo;
	private Boolean hisMessageStatus;
	private String hisMessage;
	private Integer hisCitySellStatus;
	private String hisUrl;
	private java.math.BigDecimal hisAmount;
	private String hisCurrency;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date createTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date updateTime;
	
    public Integer getId() {
        return id;
    }
	public CityDetailHistoryEntity setId(Integer id) {
        this.id = id;
        return this;
    }
    public Integer getHisId() {
        return hisId;
    }
	public CityDetailHistoryEntity setHisId(Integer hisId) {
        this.hisId = hisId;
        return this;
    }
    public String getCityCode() {
        return cityCode;
    }
	public CityDetailHistoryEntity setCityCode(String cityCode) {
        this.cityCode = cityCode;
        return this;
    }
    public String getHisUserCode() {
        return hisUserCode;
    }
	public CityDetailHistoryEntity setHisUserCode(String hisUserCode) {
        this.hisUserCode = hisUserCode;
        return this;
    }
    public String getHisOrderNo() {
        return hisOrderNo;
    }
	public CityDetailHistoryEntity setHisOrderNo(String hisOrderNo) {
        this.hisOrderNo = hisOrderNo;
        return this;
    }
    public Boolean getHisMessageStatus() {
        return hisMessageStatus;
    }
	public CityDetailHistoryEntity setHisMessageStatus(Boolean hisMessageStatus) {
        this.hisMessageStatus = hisMessageStatus;
        return this;
    }
    public String getHisMessage() {
        return hisMessage;
    }
	public CityDetailHistoryEntity setHisMessage(String hisMessage) {
        this.hisMessage = hisMessage;
        return this;
    }
    public Integer getHisCitySellStatus() {
        return hisCitySellStatus;
    }
	public CityDetailHistoryEntity setHisCitySellStatus(Integer hisCitySellStatus) {
        this.hisCitySellStatus = hisCitySellStatus;
        return this;
    }
    public String getHisUrl() {
        return hisUrl;
    }
	public CityDetailHistoryEntity setHisUrl(String hisUrl) {
        this.hisUrl = hisUrl;
        return this;
    }
    public java.math.BigDecimal getHisAmount() {
        return hisAmount;
    }
	public CityDetailHistoryEntity setHisAmount(java.math.BigDecimal hisAmount) {
        this.hisAmount = hisAmount;
        return this;
    }
    public String getHisCurrency() {
        return hisCurrency;
    }
	public CityDetailHistoryEntity setHisCurrency(String hisCurrency) {
        this.hisCurrency = hisCurrency;
        return this;
    }
    public java.util.Date getCreateTime() {
        return createTime;
    }
	public CityDetailHistoryEntity setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public java.util.Date getUpdateTime() {
        return updateTime;
    }
	public CityDetailHistoryEntity setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
}