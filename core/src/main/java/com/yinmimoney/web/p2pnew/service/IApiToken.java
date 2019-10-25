package com.yinmimoney.web.p2pnew.service;

import com.yinmimoney.web.p2pnew.enums.EnumApiTokenStatus;
import com.yinmimoney.web.p2pnew.pojo.Admin;
import com.yinmimoney.web.p2pnew.pojo.ApiToken;
import com.yinmimoney.web.p2pnew.view.LoginInfo;

import cc.s2m.web.utils.webUtils.service.BaseService;

public interface IApiToken extends BaseService<ApiToken, java.lang.Integer> {
	
	/**
	 * 
	 * @Title processApiToken
	 * @Description app登录处理token
	 * @param ip ip
	 * @param deviceId 设备id
	 * @param deviceName 设备名
	 * @param email 用户邮箱
	 * @param pwd 密码
	 * @return LoginInfo
	 */
	public LoginInfo processApiToken(String ip, String deviceId, String deviceName, String email, String pwd);

	/**
	 * 
	 * @Title updateApiTokenStatus
	 * @Description 更新token状态
	 * @param apiToken
	 * @param enumApiTokenStatus
	 * @return void
	 */
	public void updateApiTokenStatus(ApiToken apiToken, EnumApiTokenStatus enumApiTokenStatus);

	ApiToken getByToken(String token);
	public String processBackApiToken(String ip, String deviceId, String deviceName, String token, Admin admin);

}