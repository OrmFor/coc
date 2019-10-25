package com.yinmimoney.web.p2pnew.service;

import com.yinmimoney.web.p2pnew.pojo.CityHot;
import cc.s2m.web.utils.webUtils.service.BaseService;

public interface ICityHot extends BaseService<CityHot, Integer> {
    void deleteAll();
}