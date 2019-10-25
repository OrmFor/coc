package com.yinmimoney.web.p2pnew.dto;

import java.math.BigDecimal;

/**
 * Created by dyf on 2019/6/26 13:43
 */
public class DataLookInfo {
    private String item;
    private String name;
    private BigDecimal number;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getNumber() {
        return number;
    }

    public void setNumber(BigDecimal number) {
        this.number = number;
    }
}
