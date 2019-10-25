package com.yinmimoney.web.p2pnew.dto;

import java.math.BigDecimal;

/**
 * Created by dyf on 2019/8/9 14:23
 */
public class BillStatisticsDto {
    private String type;
    private String name;
    private BigDecimal money;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
