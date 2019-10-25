package com.yinmimoney.web.p2pnew.enums;

public enum EnumTradeType {

    /**广告**/
    ADS("Ads"),
    /**留言**/
    SPEAK("speak"),
    /**点赞**/
    LIKE("like"),
    /**设置课堂名称**/
    SET_HOUSE_NAME("house"),

    CHATROOM_ADS("chatroomAds"),
    CITY_PICTURE("picture"),
    PICTURE_LIKE("picture_like"),
    /**开通城市相册**/
    CITY_PICTURE_OPEN("city_picture_open"),

    CITY_ADS_PRICE("city_ads_price"),
    MODIFY_CITY_ADS_PRICE("mod_ads_price"),

    ;

    private String name;

    public String getName() {
        return name;
    }

    EnumTradeType(String name) {
        this.name = name;
    }
}
