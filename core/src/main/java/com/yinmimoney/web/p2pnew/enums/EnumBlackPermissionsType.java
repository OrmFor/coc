package com.yinmimoney.web.p2pnew.enums;

/**
 * Created by dyf on 2019/8/6 14:49
 */
public enum  EnumBlackPermissionsType {
    CHAT_HOUSE("chat_house","聊天室"),
    CHAT_HOUSE_ADS("chat_house_ads","聊天室广告"),
    ADS_LOCATION("ads_location","广告位"),
    CITY_PHOTO("city_photo","城市相册"),
    ;
    private String nid;
    private String name;

    EnumBlackPermissionsType(String nid, String name) {
        this.nid = nid;
        this.name = name;
    }

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
}
