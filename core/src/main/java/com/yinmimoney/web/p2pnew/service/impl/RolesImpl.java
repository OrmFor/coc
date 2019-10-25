package com.yinmimoney.web.p2pnew.service.impl;

import com.yinmimoney.web.p2pnew.dao.RolesMapper;
import com.yinmimoney.web.p2pnew.pojo.Roles;
import com.yinmimoney.web.p2pnew.service.IRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

@Service
public class RolesImpl extends BaseServiceImpl<Roles, RolesMapper, java.lang.Integer> implements IRoles {
    @Autowired
    private RolesMapper rolesMapper;

    protected RolesMapper getDao() {
        return rolesMapper;
    }
}