package com.yinmimoney.web.p2pnew.dao;

import com.yinmimoney.web.p2pnew.pojo.Admin;

import cc.s2m.web.utils.webUtils.dao.BaseDao;

public interface AdminMapper extends BaseDao<Admin, java.lang.Integer> {

	/**
	 * 
	 * @Title selectByUserCode
	 * @Description 根据用户编号获取用户
	 * @author wzq
	 * @date 2018年6月28日 下午2:26:33
	 * @param userCode 用户编号
	 * @return
	 * @return Admin
	 */
	Admin selectByUserCode(String userCode);

}