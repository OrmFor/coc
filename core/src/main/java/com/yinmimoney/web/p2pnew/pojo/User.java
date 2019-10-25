package com.yinmimoney.web.p2pnew.pojo;

import com.yinmimoney.web.p2pnew.pojo.entity.UserEntity;

public class User extends UserEntity {
    private static final long serialVersionUID = 1L;

    private Integer ownCityNumber;

    public Integer getOwnCityNumber() {
        return ownCityNumber;
    }

    public void setOwnCityNumber(Integer ownCityNumber) {
        this.ownCityNumber = ownCityNumber;
    }
}