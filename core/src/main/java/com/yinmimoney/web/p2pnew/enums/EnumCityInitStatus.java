package com.yinmimoney.web.p2pnew.enums;

public enum EnumCityInitStatus {
    PLATFORM(0,"平台出售"),
    USER_SELF(1,"所属用户出售"),
    USER(2,"用户出售"),

    ;

    private int status;

    private String name;

    private EnumCityInitStatus(int status, String name) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public int getStatus() {
        return status;
    }

}
