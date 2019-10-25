package com.yinmimoney.web.p2pnew.service;

import com.yinmimoney.web.p2pnew.pojo.CityDetailHistory;
import cc.s2m.web.utils.webUtils.service.BaseService;
import com.yinmimoney.web.p2pnew.pojo.Order;

public interface ICityDetailHistory extends BaseService<CityDetailHistory, Integer> {


    CityDetailHistory selectByOrderNo(String orderNo);

    CityDetailHistory selectByCityCode(String cityCode);
}