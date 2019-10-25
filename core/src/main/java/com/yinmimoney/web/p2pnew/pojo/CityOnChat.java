package com.yinmimoney.web.p2pnew.pojo;

import com.yinmimoney.web.p2pnew.pojo.entity.CityOnChatEntity;

import java.io.UnsupportedEncodingException;

public class CityOnChat extends CityOnChatEntity {
    private static final long serialVersionUID = 1L;
    private Integer isLike;
    private Integer isSelf;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    private String cityName;


    public Integer getIsSelf() {
        if(isSelf==null){
            isSelf=0;
        }
        return isSelf;
    }

    public void setIsSelf(Integer isSelf) {
        this.isSelf = isSelf;
    }

    public Integer getIsLike() {
        if(isLike==null){
            isLike=0;

        }
        return isLike;
    }

    public void setIsLike(Integer isLike) {
        this.isLike = isLike;
    }
}