package com.yinmimoney.web.p2pnew.service.impl;

import com.yinmimoney.web.p2pnew.dao.AdminActionsMapper;
import com.yinmimoney.web.p2pnew.pojo.AdminActions;
import com.yinmimoney.web.p2pnew.service.IAdminActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

@Service
public class AdminActionsImpl extends BaseServiceImpl<AdminActions, AdminActionsMapper, java.lang.Integer> implements IAdminActions {
    @Autowired
    private AdminActionsMapper adminActionsMapper;

    protected AdminActionsMapper getDao() {
        return adminActionsMapper;
    }
}