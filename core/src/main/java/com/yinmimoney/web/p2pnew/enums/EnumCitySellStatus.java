package com.yinmimoney.web.p2pnew.enums;

public enum EnumCitySellStatus {

    SELL(1,"出售"),

    NOT_SELL(2,"暂不出售"),

    ;

    private int status;

    private String name;

    private EnumCitySellStatus(int status, String name) {
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
