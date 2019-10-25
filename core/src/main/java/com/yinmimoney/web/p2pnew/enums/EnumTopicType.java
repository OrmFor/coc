package com.yinmimoney.web.p2pnew.enums;

/**
 * Created by dyf on 2019/7/19 9:15
 * 订阅主题名称枚举
 */
public enum EnumTopicType {
    TOPIC_SPEAK("/topic/speak/","留言"),
    TOPIC_LIKE("/topic/like/","点赞"),
    ;
    String code;
    String name;

    EnumTopicType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
