package com.yinmimoney.web.p2pnew.service.impl;

import com.yinmimoney.web.p2pnew.dao.CityHotMapper;
import com.yinmimoney.web.p2pnew.pojo.CityHot;
import com.yinmimoney.web.p2pnew.service.ICityHot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

@Service
public class CityHotImpl extends BaseServiceImpl<CityHot, CityHotMapper, Integer> implements ICityHot {
    @Autowired
    private CityHotMapper cityHotMapper;

    protected CityHotMapper getDao() {
        return cityHotMapper;
    }

    @Override
    public void deleteAll() {
        cityHotMapper.deleteAll();
    }
}