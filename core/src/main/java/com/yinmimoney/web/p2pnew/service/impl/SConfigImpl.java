package com.yinmimoney.web.p2pnew.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.yinmimoney.web.p2pnew.dao.SConfigMapper;
import com.yinmimoney.web.p2pnew.enums.EnumConfigStatus;
import com.yinmimoney.web.p2pnew.pojo.SConfig;
import com.yinmimoney.web.p2pnew.service.ISConfig;

import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

@Service
public class SConfigImpl extends BaseServiceImpl<SConfig, SConfigMapper, java.lang.Integer> implements ISConfig {
    @Autowired
    private SConfigMapper sConfigMapper;

    protected SConfigMapper getDao() {
        return sConfigMapper;
    }

	@Override
	public SConfig getByNid(String nid) {
		SConfig condition = new SConfig();
		condition.setNid(nid);
		condition.setStatus(EnumConfigStatus.STATUS_OPEN.getStatus());
		return getByCondition(condition);
	}

	@Override
	public String getValueByNid(String nid) {
		SConfig bean = getByNid(nid);
		if (bean == null) {
			return null;
		}
		String value = bean.getValue();
		if (!Strings.isNullOrEmpty(value)) {
			value = value.trim();
		}
		return value;
	}
}