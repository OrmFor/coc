package com.yinmimoney.web.p2pnew.service.impl;

import com.yinmimoney.web.p2pnew.dao.SysLogMapper;
import com.yinmimoney.web.p2pnew.pojo.SysLog;
import com.yinmimoney.web.p2pnew.service.ISysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

@Service
public class SysLogImpl extends BaseServiceImpl<SysLog, SysLogMapper, java.lang.Integer> implements ISysLog {
    @Autowired
    private SysLogMapper sysLogMapper;

    protected SysLogMapper getDao() {
        return sysLogMapper;
    }
}