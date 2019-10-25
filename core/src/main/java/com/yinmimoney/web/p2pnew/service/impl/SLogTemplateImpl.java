package com.yinmimoney.web.p2pnew.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yinmimoney.web.p2pnew.dao.SLogTemplateMapper;
import com.yinmimoney.web.p2pnew.pojo.SLogTemplate;
import com.yinmimoney.web.p2pnew.service.ISLogTemplate;

import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

@Service
public class SLogTemplateImpl extends BaseServiceImpl<SLogTemplate, SLogTemplateMapper, java.lang.Integer> implements ISLogTemplate {
    @Autowired
    private SLogTemplateMapper sLogTemplateMapper;

    protected SLogTemplateMapper getDao() {
        return sLogTemplateMapper;
    }

	@Override
	public SLogTemplate selectByNid(String nid) {
		SLogTemplate condition = new SLogTemplate();
		condition.setNid(nid);
		SLogTemplate sLogTemplate = getByCondition(condition);
		return sLogTemplate;
	}
}