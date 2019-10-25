package com.yinmimoney.web.p2pnew.enums;

public enum EnumCityLock {

    LOCK(Boolean.TRUE,"锁定"),
    UN_LOCK(Boolean.FALSE,"未锁定"),
            ;

    private boolean status;

    private String name;

    private EnumCityLock(boolean status, String name) {
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
