package com.yinmimoney.web.p2pnew.requestbody;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class PreOrderRequestBody {

    @NotBlank(message = "{order.cityCode.empty}")
    private String cityCode;

/*    @NotNull(message = "{order.amount.empty}")
    private BigDecimal amount;

    @NotBlank(message = "{order.currency.empty}")
    private String currency;*/


    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}
