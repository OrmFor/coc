package com.yinmimoney.web.p2pnew.service.impl;

import com.yinmimoney.web.p2pnew.dao.AdminRoleActionsMapper;
import com.yinmimoney.web.p2pnew.pojo.AdminRoleActions;
import com.yinmimoney.web.p2pnew.service.IAdminRoleActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

@Service
public class AdminRoleActionsImpl extends BaseServiceImpl<AdminRoleActions, AdminRoleActionsMapper, java.lang.Integer> implements IAdminRoleActions {
    @Autowired
    private AdminRoleActionsMapper adminRoleActionsMapper;

    protected AdminRoleActionsMapper getDao() {
        return adminRoleActionsMapper;
    }
}