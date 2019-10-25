package com.yinmimoney.web.p2pnew.service.impl;

import com.yinmimoney.web.p2pnew.dao.SysTaskHandelMapper;
import com.yinmimoney.web.p2pnew.pojo.SysTaskHandel;
import com.yinmimoney.web.p2pnew.service.ISysTaskHandel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

@Service
public class SysTaskHandelImpl extends BaseServiceImpl<SysTaskHandel, SysTaskHandelMapper, java.lang.Integer> implements ISysTaskHandel {
    @Autowired
    private SysTaskHandelMapper sysTaskHandelMapper;

    protected SysTaskHandelMapper getDao() {
        return sysTaskHandelMapper;
    }
}