package com.yinmimoney.web.p2pnew.service.impl;

import com.yinmimoney.web.p2pnew.dao.CityDetailHistoryMapper;
import com.yinmimoney.web.p2pnew.pojo.CityDetailHistory;
import com.yinmimoney.web.p2pnew.service.ICityDetailHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

@Service
public class CityDetailHistoryImpl extends BaseServiceImpl<CityDetailHistory, CityDetailHistoryMapper, Integer> implements ICityDetailHistory {
    @Autowired
    private CityDetailHistoryMapper cityDetailHistoryMapper;

    protected CityDetailHistoryMapper getDao() {
        return cityDetailHistoryMapper;
    }

    @Override
    public CityDetailHistory selectByOrderNo(String orderNo) {
        CityDetailHistory condi = new CityDetailHistory();
        condi.setHisOrderNo(orderNo);
        return getByCondition(condi);
    }

    @Override
    public CityDetailHistory selectByCityCode(String cityCode) {
        CityDetailHistory condi = new CityDetailHistory();
        condi.setCityCode(cityCode);
        condi.setOrderBy("update_time desc");
        return getByCondition(condi);
    }
}