package com.yinmimoney.web.p2pnew.responsebody;

import com.yinmimoney.web.p2pnew.responsebody.base.CityBase;

import java.math.BigDecimal;

public class MyCitiesResponseBody extends CityBase {

    private Integer citySellStatus;


    public Integer getCitySellStatus() {
        return citySellStatus;
    }

    public void setCitySellStatus(Integer citySellStatus) {
        this.citySellStatus = citySellStatus;
    }
}
