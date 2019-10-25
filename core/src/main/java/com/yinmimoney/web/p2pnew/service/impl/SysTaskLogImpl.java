package com.yinmimoney.web.p2pnew.service.impl;

import com.yinmimoney.web.p2pnew.dao.SysTaskLogMapper;
import com.yinmimoney.web.p2pnew.pojo.SysTaskLog;
import com.yinmimoney.web.p2pnew.service.ISysTaskLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

@Service
public class SysTaskLogImpl extends BaseServiceImpl<SysTaskLog, SysTaskLogMapper, java.lang.Integer> implements ISysTaskLog {
    @Autowired
    private SysTaskLogMapper sysTaskLogMapper;

    protected SysTaskLogMapper getDao() {
        return sysTaskLogMapper;
    }
}