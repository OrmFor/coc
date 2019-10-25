package com.yinmimoney.web.p2pnew.service.impl;

import com.yinmimoney.web.p2pnew.dao.BlackPermissionsMapper;
import com.yinmimoney.web.p2pnew.pojo.BlackPermissions;
import com.yinmimoney.web.p2pnew.service.IBlackPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

@Service
public class BlackPermissionsImpl extends BaseServiceImpl<BlackPermissions, BlackPermissionsMapper, Integer> implements IBlackPermissions {
    @Autowired
    private BlackPermissionsMapper blackPermissionsMapper;

    protected BlackPermissionsMapper getDao() {
        return blackPermissionsMapper;
    }
}