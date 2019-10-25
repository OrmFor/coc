package com.yinmimoney.web.p2pnew.enums;

/**
 * Created by dyf on 2019/7/22 11:57
 */
public enum EnumMessageStatus {
    NO_READ(0,"未读"),
    HAS_READ(1,"已读"),
    ;
    private Integer status;
    private String name;

    EnumMessageStatus(Integer status, String name) {
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
