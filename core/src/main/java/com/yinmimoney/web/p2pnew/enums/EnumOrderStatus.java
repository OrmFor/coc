package com.yinmimoney.web.p2pnew.enums;

public enum EnumOrderStatus {

    PRE_LOCK(0,"PRE_LOCK"),
    PENDING(1,"PENDING"),
    RECEIVED(2,"RECEIVED"),
    COMPLETED(3,"COMPLETED"),
    FAILED(4,"FAILED"),
    CLOSE(5,"CLOSE"),
    ADMIN_CLOSE(6,"ADMIN_CLOSE"),
    ;

    private int status;

    private String name;

    private EnumOrderStatus(int status, String name) {
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
