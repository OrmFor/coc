package com.yinmimoney.web.p2pnew.service;

import com.yinmimoney.web.p2pnew.pojo.CityMapWeathersv;
import cc.s2m.web.utils.webUtils.service.BaseService;

public interface ICityMapWeathersv extends BaseService<CityMapWeathersv, Integer> {
    CityMapWeathersv selectByCityCode(String cityCode);
}