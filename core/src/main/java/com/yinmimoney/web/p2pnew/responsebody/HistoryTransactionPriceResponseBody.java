package com.yinmimoney.web.p2pnew.responsebody;

import java.math.BigDecimal;
import java.util.Date;

public class HistoryTransactionPriceResponseBody {

    private BigDecimal amount;

    private String currency;

    private Date createTime;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
