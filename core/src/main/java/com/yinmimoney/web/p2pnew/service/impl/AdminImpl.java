package com.yinmimoney.web.p2pnew.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yinmimoney.web.p2pnew.dao.AdminMapper;
import com.yinmimoney.web.p2pnew.pojo.Admin;
import com.yinmimoney.web.p2pnew.service.IAdmin;

import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

@Service
public class AdminImpl extends BaseServiceImpl<Admin, AdminMapper, java.lang.Integer> implements IAdmin {

	private static final Logger LOGGER = LogManager.getLogger(AdminImpl.class);

	@Autowired
	private AdminMapper adminMapper;

	protected AdminMapper getDao() {
		return adminMapper;
	}

	@Override
	public Admin getAdminByUserCode(String userCode) {
		return adminMapper.selectByUserCode(userCode);
	}

	@Override
	public Admin getAdminByUserName(String userName) {
		Admin admin = new Admin();
		admin.setUserName(userName);
		return getByCondition(admin);
	}

	@Override
	public Admin getAdminByMobilePhone(String mobilePhone) {
		Admin admin = new Admin();
		admin.setMobilePhone(mobilePhone);
		return getByCondition(admin);
	}

}