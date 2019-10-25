package com.yinmimoney.web.p2pnew.dto;

import java.math.BigDecimal;

/**
 * Created by dyf on 2019/6/26 14:40
 */
public class PlatformIncomeInfo {
    private String time;
    private BigDecimal income;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }
}
