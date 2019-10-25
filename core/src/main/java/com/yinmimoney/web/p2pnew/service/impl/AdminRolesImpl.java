package com.yinmimoney.web.p2pnew.service.impl;

import com.yinmimoney.web.p2pnew.dao.AdminRolesMapper;
import com.yinmimoney.web.p2pnew.pojo.AdminRoles;
import com.yinmimoney.web.p2pnew.service.IAdminRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

@Service
public class AdminRolesImpl extends BaseServiceImpl<AdminRoles, AdminRolesMapper, java.lang.Integer> implements IAdminRoles {
    @Autowired
    private AdminRolesMapper adminRolesMapper;

    protected AdminRolesMapper getDao() {
        return adminRolesMapper;
    }
}