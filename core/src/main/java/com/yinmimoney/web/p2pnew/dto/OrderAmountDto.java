package com.yinmimoney.web.p2pnew.dto;

import java.math.BigDecimal;

public class OrderAmountDto {

    private BigDecimal orderAmountIn;

    private BigDecimal orderAmountOut;

    public BigDecimal getOrderAmountIn() {
        return orderAmountIn;
    }

    public void setOrderAmountIn(BigDecimal orderAmountIn) {
        this.orderAmountIn = orderAmountIn;
    }

    public BigDecimal getOrderAmountOut() {
        return orderAmountOut;
    }

    public void setOrderAmountOut(BigDecimal orderAmountOut) {
        this.orderAmountOut = orderAmountOut;
    }
}
