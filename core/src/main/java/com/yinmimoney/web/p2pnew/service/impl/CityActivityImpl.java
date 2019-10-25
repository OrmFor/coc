package com.yinmimoney.web.p2pnew.service.impl;

import com.yinmimoney.web.p2pnew.dao.CityActivityMapper;
import com.yinmimoney.web.p2pnew.pojo.CityActivity;
import com.yinmimoney.web.p2pnew.service.ICityActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

@Service
public class CityActivityImpl extends BaseServiceImpl<CityActivity, CityActivityMapper, Integer> implements ICityActivity {
    @Autowired
    private CityActivityMapper cityActivityMapper;

    protected CityActivityMapper getDao() {
        return cityActivityMapper;
    }
}