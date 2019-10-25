package com.yinmimoney.web.p2pnew.service.impl;

import com.yinmimoney.web.p2pnew.dao.PlatformMapper;
import com.yinmimoney.web.p2pnew.pojo.Platform;
import com.yinmimoney.web.p2pnew.service.IPlatform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

@Service
public class PlatformImpl extends BaseServiceImpl<Platform, PlatformMapper, Integer> implements IPlatform {
    @Autowired
    private PlatformMapper platformMapper;

    protected PlatformMapper getDao() {
        return platformMapper;
    }

    @Override
    public Platform getByPlatCode(String platCode) {
        Platform condi = new Platform();
        condi.setPlatformCode(platCode);

        return this.getByCondition(condi);
    }
}