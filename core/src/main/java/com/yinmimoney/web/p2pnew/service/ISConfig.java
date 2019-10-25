package com.yinmimoney.web.p2pnew.service;

import com.yinmimoney.web.p2pnew.pojo.SConfig;

import cc.s2m.web.utils.webUtils.service.BaseService;

public interface ISConfig extends BaseService<SConfig, java.lang.Integer> {

	/**
	 * 
	 * @Title getByNid
	 * @Description 根据nid获取配置
	 * @author wzq
	 * @date 2018年11月14日 下午3:07:42
	 * @param nid 唯一标识
	 * @return
	 * @return SConfig
	 */
	SConfig getByNid(String nid);

	/**
	 * 
	 * @Title getValueByNid
	 * @Description 根据nid获取配置值
	 * @author wzq
	 * @date 2018年11月14日 下午3:08:05
	 * @param nid 唯一标识
	 * @return
	 * @return String
	 */
	String getValueByNid(String nid);

}