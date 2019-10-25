package com.yinmimoney.web.p2pnew.enums;

public enum EnumMessageIsShow {

    UN_SHOW(Boolean.FALSE,"不显示"),
    IS_SHOW(Boolean.TRUE,"显示"),
    ;

    private boolean status;

    private String name;

    private EnumMessageIsShow(boolean status, String name) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public boolean getStatus() {
        return status;
    }
}
