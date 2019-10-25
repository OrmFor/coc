package com.yinmimoney.web.p2pnew.enums;

public enum EnumInComeType {

    OTHER("other"),
    SELL("sell")    ;


    private String name ;

    EnumInComeType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
