package com.yinmimoney.web.p2pnew.service;

import com.yinmimoney.web.p2pnew.pojo.UserInviteCodeMax;

import cc.s2m.web.utils.webUtils.service.BaseService;

public interface IUserInviteCodeMax extends BaseService<UserInviteCodeMax, java.lang.Integer> {
	/**
	 * 
	 * @Title processInviteCodeMetch
	 * @Description 获取唯一邀请码
	 * @author wzq
	 * @date 2018年11月27日 下午6:11:58
	 * @return
	 * @return String
	 */
	String processInviteCodeMetch();
}