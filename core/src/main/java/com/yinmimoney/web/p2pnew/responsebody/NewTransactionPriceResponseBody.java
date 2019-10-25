package com.yinmimoney.web.p2pnew.responsebody;

import com.yinmimoney.web.p2pnew.responsebody.base.CityBase;

import java.math.BigDecimal;

public class NewTransactionPriceResponseBody extends CityBase {



    private String timeStr;


    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }
}
