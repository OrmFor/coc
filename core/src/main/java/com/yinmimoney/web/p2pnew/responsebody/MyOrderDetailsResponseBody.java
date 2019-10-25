package com.yinmimoney.web.p2pnew.responsebody;

import com.yinmimoney.web.p2pnew.responsebody.base.CityBase;

import java.util.Date;

public class MyOrderDetailsResponseBody extends CityBase {

    private Date createTime;

    private String type;

    private String txId;

    private String tradeType;

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
