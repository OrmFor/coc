package com.yinmimoney.web.p2pnew.dto;

import java.util.Date;

public class CityPictureDto {

    private String  cityCode;

    private String pictureUserCode;

    private String pictureUserName;

    private Integer likeNum;

    private String cityName;

    private Integer isLike;

    private String txid;

    private String cityUserCode;

    private String pictureCode;

    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPictureCode() {
        return pictureCode;
    }

    public void setPictureCode(String pictureCode) {
        this.pictureCode = pictureCode;
    }

    public String getCityUserCode() {
        return cityUserCode;
    }

    public void setCityUserCode(String cityUserCode) {
        this.cityUserCode = cityUserCode;
    }

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public Integer getIsLike() {
        return isLike;
    }

    public void setIsLike(Integer isLike) {
        this.isLike = isLike;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getPictureUserCode() {
        return pictureUserCode;
    }

    public void setPictureUserCode(String pictureUserCode) {
        this.pictureUserCode = pictureUserCode;
    }

    public String getPictureUserName() {
        return pictureUserName;
    }

    public void setPictureUserName(String pictureUserName) {
        this.pictureUserName = pictureUserName;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
