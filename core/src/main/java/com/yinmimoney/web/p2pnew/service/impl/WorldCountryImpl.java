package com.yinmimoney.web.p2pnew.service.impl;

import com.yinmimoney.web.p2pnew.dao.WorldCountryMapper;
import com.yinmimoney.web.p2pnew.pojo.WorldCountry;
import com.yinmimoney.web.p2pnew.service.IWorldCountry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

@Service
public class WorldCountryImpl extends BaseServiceImpl<WorldCountry, WorldCountryMapper, Integer> implements IWorldCountry {
    @Autowired
    private WorldCountryMapper worldCountryMapper;

    protected WorldCountryMapper getDao() {
        return worldCountryMapper;
    }
}