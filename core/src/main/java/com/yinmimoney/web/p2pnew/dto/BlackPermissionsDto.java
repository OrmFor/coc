package com.yinmimoney.web.p2pnew.dto;

/**
 * Created by dyf on 2019/8/6 14:18
 */
public class BlackPermissionsDto {
    private String nid;
    private String name;
    //0:禁用   1:开启
    private Integer isOpen;

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }
}
