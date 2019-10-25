package com.yinmimoney.web.p2pnew.enums;

public enum EnumCityActivityStatus {
    NOT_OPEN(0,"未开始"),
    OPENING(1,"进行中"),
    HAS_END(2,"已结束"),
    ;
    private Integer status;
    private String name;

    EnumCityActivityStatus(Integer status, String name) {
        this.status = status;
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
