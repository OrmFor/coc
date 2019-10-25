package com.yinmimoney.web.p2pnew.requestbody;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CloseOrderRequestBody {


    @NotBlank(message = "{orderNo.empty}")
    private String orderNo;


    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
