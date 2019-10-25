package com.yinmimoney.web.p2pnew.service;

import com.yinmimoney.web.p2pnew.pojo.MessageFee;
import cc.s2m.web.utils.webUtils.service.BaseService;

import java.math.BigDecimal;
import java.util.Map;

public interface IMessageFee extends BaseService<MessageFee, Integer> {
    Map<String,Object> getMyMessageFeeDetail(String userCode, Integer pageNumber, Map<String,String[]> parameterMap);

    BigDecimal getMoneyOut(String userCode);
}