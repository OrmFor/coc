package com.yinmimoney.web.p2pnew.service;

import com.yinmimoney.web.p2pnew.pojo.CommonOrder;
import cc.s2m.web.utils.webUtils.service.BaseService;

public interface ICommonOrder extends BaseService<CommonOrder, Integer> {
    CommonOrder selectByOrderNo(String orderNo);
}