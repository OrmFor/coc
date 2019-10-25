package com.yinmimoney.web.p2pnew.dto;

import com.yinmimoney.web.p2pnew.responsebody.base.CityBase;

import java.util.Date;

public class NewCityPriceDto extends CityBase {
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
