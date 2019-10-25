package com.yinmimoney.web.p2pnew.service;

import com.yinmimoney.web.p2pnew.pojo.Admin;

import cc.s2m.web.utils.webUtils.service.BaseService;

public interface IAdmin extends BaseService<Admin, java.lang.Integer> {

	/**
	 * 
	 * @Title getAdminByUserCode
	 * @Description 根据用户名获取用户
	 * @author wzq
	 * @date 2018年6月28日 下午2:26:33
	 * @param userCode 用户编号
	 * @return
	 * @return Admin
	 */
	Admin getAdminByUserCode(String userCode);
	
	/**
	 * 
	 * @Title getAdminByUserName
	 * @Description 根据用户名获取用户
	 * @author wzq
	 * @date 2018年6月28日 下午2:26:33
	 * @param userName 用户名
	 * @return
	 * @return Admin
	 */
	Admin getAdminByUserName(String userName);

	/**
	 * 
	 * @Title getAdminByMobilePhone
	 * @Description 根据手机号获取用户
	 * @author wzq
	 * @date 2018年6月28日 下午2:26:15
	 * @param mobilePhone 手机号
	 * @return
	 * @return Admin
	 */
	Admin getAdminByMobilePhone(String mobilePhone);
	
}