package com.yinmimoney.web.p2pnew.enums;

public enum  EnumOrderDetailType {

    IN("IN"),
    OUT("OUT")
    ;


    private String name;

    private EnumOrderDetailType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
