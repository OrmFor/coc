package com.yinmimoney.web.p2pnew.service.impl;

import java.util.Date;

import cc.s2m.web.utils.webUtils.StaticProp;
import com.yinmimoney.web.p2pnew.pojo.Admin;
import com.yinmimoney.web.p2pnew.service.IAdmin;
import com.yinmimoney.web.p2pnew.util.AESUtil;
import com.yinmimoney.web.p2pnew.util.RedisUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.yinmimoney.web.p2pnew.core.StatusCode;
import com.yinmimoney.web.p2pnew.dao.ApiTokenMapper;
import com.yinmimoney.web.p2pnew.enums.EnumApiTokenStatus;
import com.yinmimoney.web.p2pnew.exception.BussinessException;
import com.yinmimoney.web.p2pnew.pojo.ApiToken;
import com.yinmimoney.web.p2pnew.pojo.User;
import com.yinmimoney.web.p2pnew.service.IApiToken;
import com.yinmimoney.web.p2pnew.service.IUser;
import com.yinmimoney.web.p2pnew.util.DateUtil;
import com.yinmimoney.web.p2pnew.view.LoginInfo;

import cc.s2m.util.IDGenerator;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;
import cc.s2m.web.utils.webUtils.vo.Expressions;

@Service
public class ApiTokenImpl extends BaseServiceImpl<ApiToken, ApiTokenMapper, java.lang.Integer> implements IApiToken {

	private static final Logger logger = LogManager.getLogger(ApiTokenImpl.class);

	@Autowired
	private IUser userService;
	@Autowired
	private ApiTokenMapper apiTokenMapper;
	@Autowired
	protected RedisUtil redisUtil;
	@Autowired
	private IAdmin ydAdminService;



	protected ApiTokenMapper getDao() {
		return apiTokenMapper;
	}

	/*@Override
	public LoginInfo processApiToken(String ip, String deviceId, String deviceName, String email, String pwd) {
		if (Strings.isNullOrEmpty(deviceId)) {
			throw new BussinessException(StatusCode.ERROR_LACK_PARAM);
		}
		if (Strings.isNullOrEmpty(deviceName)) {
			throw new BussinessException(StatusCode.ERROR_LACK_PARAM);
		}
		if (Strings.isNullOrEmpty(email)) {
			throw new BussinessException(StatusCode.ERROR_LACK_PARAM);
		}
		if (Strings.isNullOrEmpty(pwd)) {
			throw new BussinessException(StatusCode.ERROR_LACK_PARAM);
		}
		// 检查用户是否存在
		User conditionUser = new User();
		conditionUser.setEmail(email);
		User user = userService.getByCondition(conditionUser);
		if (user == null) {
			throw new BussinessException(StatusCode.ERROR_ACCOUNT_NOT_EXIST);
		}

		String md5Pwd = AccountDigestUtils.getMd5Pwd(user.getUserCode(), pwd).toUpperCase();
		String userPwd = user.getPwd();
		if (!userPwd.toUpperCase().equals(md5Pwd)) {
			String code_error = (String) redisUtil.find(EnumRedisKeys.PWD_CODE + email + "_error");
			if (!Strings.isNullOrEmpty(code_error)) {
				long errorNumber = Long.parseLong(code_error);
				if (errorNumber >= 5) {
					throw new BussinessException(StatusCode.ERROR_PWD_SEND_LIMIT);
				}
			}
			redisUtil.addWithTimeout(EnumRedisKeys.PWD_CODE + email + "_error",
					Strings.isNullOrEmpty(code_error) ? "1" : (Long.parseLong(code_error) + 1)+"", 30, TimeUnit.MINUTES);

			throw new BussinessException(StatusCode.ERROR_PWD_VALIDATE);

		}


		Date now = DateUtil.getNow();
		// 新设备登录，更新原来token为另外设备登录状态
		ApiToken condition = new ApiToken();
		condition.setUserCode(user.getUserCode());
		condition.and(Expressions.ne("device_id", deviceId));
		condition.setStatus(EnumApiTokenStatus.STATUS_NORMAL.getStatus());
		ApiToken update = new ApiToken();
		update.setStatus(EnumApiTokenStatus.STATUS_OTHER_DEVICE_LOGIN.getStatus());
		update.setChangeTime(now);
		updateByCondition(update, condition);
		// 同设备登录，更新token状态为失效
		condition = new ApiToken();
		condition.setUserCode(user.getUserCode());
		condition.setDeviceId(deviceId);
		condition.setStatus(EnumApiTokenStatus.STATUS_NORMAL.getStatus());
		update = new ApiToken();
		update.setStatus(EnumApiTokenStatus.STATUS_DISABLED.getStatus());
		update.setChangeTime(now);
		updateByCondition(update, condition);
		// 插入新token信息
		String token = IDGenerator.uuid();
		ApiToken insert = new ApiToken();
		insert.setUserCode(user.getUserCode());
		insert.setDeviceId(deviceId);
		insert.setDeviceName(deviceName);
		insert.setToken(token);
		apiTokenMapper.insertSelective(insert);
		// 加密token给前端
		JSONObject json = new JSONObject();
		json.put("deviceId", deviceId);
		json.put("token", token);
		String signToken = null;
		try {
			signToken = new String(Base64.encodeBase64(json.toJSONString().getBytes()), "UTF-8");
		} catch (Exception e) {
			logger.error("加密token失败：", e);
			throw new BussinessException(StatusCode.ERROR);
		}
		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setUserName(user.getUserName());
		loginInfo.setToken(signToken);
		loginInfo.setStatus(user.getStatus());
		return loginInfo;
	}*/


	public LoginInfo processApiToken(String ip, String deviceId, String deviceName, String id, String name) {
		if (Strings.isNullOrEmpty(deviceId)) {
			throw new BussinessException(StatusCode.ERROR_LACK_PARAM);
		}
		if (Strings.isNullOrEmpty(deviceName)) {
			throw new BussinessException(StatusCode.ERROR_LACK_PARAM);
		}
		if (Strings.isNullOrEmpty(id)) {
			throw new BussinessException(StatusCode.ERROR_LACK_PARAM);
		}
		if (Strings.isNullOrEmpty(name)) {
			throw new BussinessException(StatusCode.ERROR_LACK_PARAM);
		}
		// 检查用户是否存在
		User conditionOauth = new User();
		conditionOauth.setUserCode(id);
		User user = userService.getByCondition(conditionOauth);
		if (user == null) {
			throw new BussinessException(StatusCode.ERROR_ACCOUNT_NOT_EXIST);
		}

		Date now = DateUtil.getNow();
		// 新设备登录，更新原来token为另外设备登录状态
		ApiToken condition = new ApiToken();
		condition.setUserCode(user.getUserCode());
		condition.and(Expressions.ne("device_id", deviceId));
		condition.setStatus(EnumApiTokenStatus.STATUS_NORMAL.getStatus());
		ApiToken update = new ApiToken();
		update.setStatus(EnumApiTokenStatus.STATUS_OTHER_DEVICE_LOGIN.getStatus());
		update.setChangeTime(now);
		updateByCondition(update, condition);
		// 同设备登录，更新token状态为失效
		condition = new ApiToken();
		condition.setUserCode(user.getUserCode());
		condition.setDeviceId(deviceId);
		condition.setStatus(EnumApiTokenStatus.STATUS_NORMAL.getStatus());
		update = new ApiToken();
		update.setStatus(EnumApiTokenStatus.STATUS_DISABLED.getStatus());
		update.setChangeTime(now);
		updateByCondition(update, condition);
		// 插入新token信息
		String token = IDGenerator.uuid();
		ApiToken insert = new ApiToken();
		insert.setUserCode(user.getUserCode());
		insert.setDeviceId(deviceId);
		insert.setDeviceName(deviceName);
		insert.setToken(token);
		apiTokenMapper.insertSelective(insert);
		// 加密token给前端
		JSONObject json = new JSONObject();
		json.put("deviceId", deviceId);
		json.put("token", token);
		String signToken = null;
		try {
			signToken = new String(Base64.encodeBase64(json.toJSONString().getBytes()), "UTF-8");
		} catch (Exception e) {
			logger.error("加密token失败：", e);
			throw new BussinessException(StatusCode.ERROR);
		}
		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setUserName(user.getUserName());
		loginInfo.setToken(signToken);
		loginInfo.setEncodeUserName(AESUtil.encrypt(user.getUserName(),StaticProp.sysConfig.get("url.request.validate")));
		loginInfo.setEncodeUserCode(AESUtil.encrypt(user.getUserCode(),StaticProp.sysConfig.get("url.request.validate")));
		//loginInfo.setStatus(user.getStatus());
		return loginInfo;
	}

	@Override
	public void updateApiTokenStatus(ApiToken apiToken, EnumApiTokenStatus enumApiTokenStatus) {
		ApiToken update = new ApiToken();
		update.setId(apiToken.getId());
		update.setStatus(enumApiTokenStatus.getStatus());
		update.setChangeTime(DateUtil.getNow());
		updateByPrimaryKeySelective(update);
	}

	@Override
	public ApiToken getByToken(String token) {
		ApiToken apiToken = new ApiToken();
		apiToken.setToken(token);
		return this.getByCondition(apiToken);
	}

	@Override
	public String processBackApiToken(String ip, String deviceId, String deviceName, String token, Admin admin) {
		Admin updateAdmin = new Admin();
		updateAdmin.setId(admin.getId());
		updateAdmin.setLoginTime(DateUtil.getNow());
		updateAdmin.setLoginIp(ip);
		ydAdminService.updateByPrimaryKeySelective(updateAdmin);
		// 新设备登录，更新原来token为另外设备登录状态
		ApiToken condition = new ApiToken();
		condition.setUserCode(admin.getUserCode());
		condition.and(Expressions.ne("device_id", deviceId));
		condition.setStatus(EnumApiTokenStatus.STATUS_NORMAL.getStatus());
		ApiToken update = new ApiToken();
		update.setStatus(EnumApiTokenStatus.STATUS_OTHER_DEVICE_LOGIN.getStatus());
		update.setChangeTime(DateUtil.getNow());
		updateByCondition(update, condition);
		// 同设备登录，更新token状态为失效
		condition = new ApiToken();
		condition.setUserCode(admin.getUserCode());
		condition.setDeviceId(deviceId);
		condition.setStatus(EnumApiTokenStatus.STATUS_NORMAL.getStatus());
		update = new ApiToken();
		update.setStatus(EnumApiTokenStatus.STATUS_DISABLED.getStatus());
		update.setChangeTime(DateUtil.getNow());
		updateByCondition(update, condition);
		// 插入新token信息
		ApiToken insert = new ApiToken();
		insert.setDeviceId(deviceId);
		insert.setDeviceName(deviceName);
		insert.setToken(token);
		insert.setUserCode(admin.getUserCode());
		insert.setDateAdd(DateUtil.getNow());
		insert.setDateUpdate(DateUtil.getNow());
		apiTokenMapper.insertSelective(insert);
		// 加密token给前端
		JSONObject json = new JSONObject();
		json.put("device_id", deviceId);
		json.put("token", token);
		String signToken = null;
		try {
			signToken = new String(Base64.encodeBase64(json.toJSONString().getBytes()), "UTF-8");
		} catch (Exception e) {
			logger.error("加密token失败：", e);
		}
		return signToken;
	}

}