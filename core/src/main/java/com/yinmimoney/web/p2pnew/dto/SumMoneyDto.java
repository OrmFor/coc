package com.yinmimoney.web.p2pnew.dto;

import java.math.BigDecimal;

public class SumMoneyDto {
    private BigDecimal moneySumIn = BigDecimal.ZERO;

    private BigDecimal moneySumOut = BigDecimal.ZERO ;

    public BigDecimal getMoneySumIn() {
        return moneySumIn;
    }

    public void setMoneySumIn(BigDecimal moneySumIn) {
        this.moneySumIn = moneySumIn;
    }

    public BigDecimal getMoneySumOut() {
        return moneySumOut;
    }

    public void setMoneySumOut(BigDecimal moneySumOut) {
        this.moneySumOut = moneySumOut;
    }
}
