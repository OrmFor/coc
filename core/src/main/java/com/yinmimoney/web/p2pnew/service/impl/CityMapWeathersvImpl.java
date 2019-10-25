package com.yinmimoney.web.p2pnew.service.impl;

import com.yinmimoney.web.p2pnew.dao.CityMapWeathersvMapper;
import com.yinmimoney.web.p2pnew.pojo.CityMapWeathersv;
import com.yinmimoney.web.p2pnew.service.ICityMapWeathersv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

@Service
public class CityMapWeathersvImpl extends BaseServiceImpl<CityMapWeathersv, CityMapWeathersvMapper, Integer> implements ICityMapWeathersv {
    @Autowired
    private CityMapWeathersvMapper cityMapWeathersvMapper;

    protected CityMapWeathersvMapper getDao() {
        return cityMapWeathersvMapper;
    }

    @Override
    public CityMapWeathersv selectByCityCode(String cityCode) {
        CityMapWeathersv cond = new CityMapWeathersv();
        cond.setCityCode(cityCode);
        return getByCondition(cond);
    }
}