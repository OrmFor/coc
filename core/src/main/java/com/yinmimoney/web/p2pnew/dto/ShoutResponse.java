package com.yinmimoney.web.p2pnew.dto;

import java.util.Date;

/**
 * Created by dyf on 2019/7/19 9:23
 */
public class ShoutResponse {
    private Integer code;
    private String content;
    private String speakCode;
    private String userName;
    private String userCode;
    private Date addTime;
    private String txid;
    private String speakType;

    public ShoutResponse() {
    }

    public ShoutResponse(Integer code, String content) {
        this.code = code;
        this.content = content;
    }

    public String getSpeakType() {
        return speakType;
    }

    public void setSpeakType(String speakType) {
        this.speakType = speakType;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public String getSpeakCode() {
        return speakCode;
    }

    public void setSpeakCode(String speakCode) {
        this.speakCode = speakCode;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}
