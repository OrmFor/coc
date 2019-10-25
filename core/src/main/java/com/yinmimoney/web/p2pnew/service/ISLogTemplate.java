package com.yinmimoney.web.p2pnew.service;

import com.yinmimoney.web.p2pnew.pojo.SLogTemplate;
import cc.s2m.web.utils.webUtils.service.BaseService;

public interface ISLogTemplate extends BaseService<SLogTemplate, java.lang.Integer> {
	
	/**
	 * 
	 * @Title selectByNid
	 * @Description 根据nid查询日志模板
	 * @author wzq
	 * @date 2018年11月27日 下午5:19:24
	 * @param nid
	 * @return
	 * @return SLogTemplate
	 */
	SLogTemplate selectByNid(String nid);
}