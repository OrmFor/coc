package com.yinmimoney.web.p2pnew.service.impl;

import com.yinmimoney.web.p2pnew.dao.VersionUpManageMapper;
import com.yinmimoney.web.p2pnew.pojo.VersionUpManage;
import com.yinmimoney.web.p2pnew.service.IVersionUpManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

@Service
public class VersionUpManageImpl extends BaseServiceImpl<VersionUpManage, VersionUpManageMapper, java.lang.Integer> implements IVersionUpManage {
    @Autowired
    private VersionUpManageMapper versionUpManageMapper;

    protected VersionUpManageMapper getDao() {
        return versionUpManageMapper;
    }
}