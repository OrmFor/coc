package com.yinmimoney.web.p2pnew.controller.admin;

import cc.s2m.util.IDGenerator;
import cc.s2m.util.IpUtil;
import cc.s2m.web.utils.webUtils.StaticProp;
import cc.s2m.web.utils.webUtils.util.AccountDigestUtils;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.yinmimoney.web.p2pnew.constant.NoticeConstant;
import com.yinmimoney.web.p2pnew.constant.SysLogConstant;
import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.core.ResultCode;
import com.yinmimoney.web.p2pnew.core.StatusCode;
import com.yinmimoney.web.p2pnew.enums.EnumAdminStatus;
import com.yinmimoney.web.p2pnew.enums.EnumApiTokenStatus;
import com.yinmimoney.web.p2pnew.enums.EnumRedisKeys;
import com.yinmimoney.web.p2pnew.pojo.Admin;
import com.yinmimoney.web.p2pnew.pojo.ApiToken;
import com.yinmimoney.web.p2pnew.pojo.SysLog;
import com.yinmimoney.web.p2pnew.service.IAdmin;
import com.yinmimoney.web.p2pnew.service.IApiToken;
import com.yinmimoney.web.p2pnew.service.ISysLog;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller("admin_LoginController")
@RequestMapping("/admin")
public class LoginController extends BaseController {

	private static final Logger logger = LogManager.getLogger(LoginController.class);

	private static final String MODULE_NAME = "登录/退出";

	private static final String APPKEY= StaticProp.sysConfig.get("Ali.captcha.AppKey");

	@Autowired
	private IAdmin ydAdminService;
	@Autowired
	private IApiToken ydApiTokenService;
	@Autowired
	private ISysLog ydSysLogService;
	@CrossOrigin
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ResultCode login(HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = getJsonFromRequest();
		String deviceId = json.getString("deviceId");
		String deviceName = json.getString("deviceName");
		String mobilePhone = json.getString("mobilePhone");
		String passWord = json.getString("passWord");
		Boolean auto = json.getBoolean("auto");
		String code = json.getString("code");

		if (Strings.isNullOrEmpty(deviceId)) {
			return new ResultCode(StatusCode.ERROR_LACK_PARAM);
		}
		if (Strings.isNullOrEmpty(deviceName)) {
			return new ResultCode(StatusCode.ERROR_LACK_PARAM);
		}
		if (Strings.isNullOrEmpty(mobilePhone)) {
			return new ResultCode(StatusCode.ERROR_MOBILE_EMPTY);
		}
		if (Strings.isNullOrEmpty(passWord)) {
			return new ResultCode(StatusCode.ERROR_PWD_EMPTY);
		}
		if(Strings.isNullOrEmpty(code)){
			return new ResultCode(StatusCode.ERROR_PIC_EMPTY);
		}
		//判断用户是否存在
		Admin condition = new Admin();
		condition.setUserName(mobilePhone);
		Admin admin = ydAdminService.getByCondition(condition);
		if (admin == null) {
			condition = new Admin();
			condition.setMobilePhone(mobilePhone);
			admin = ydAdminService.getByCondition(condition);
			if (admin == null) {
				return new ResultCode(StatusCode.ERROR_ACCOUNT_NOT_EXIST);
			}
		}
		String sessionId = request.getSession(true).getId();
		String s = redisStringManager.find(EnumRedisKeys.PIC_CODE.getKey() + sessionId);
		if(StringUtils.isBlank(s)||!s.equals(code)){
			redisStringManager.delete(EnumRedisKeys.PIC_CODE.getKey()+sessionId);
			return new ResultCode(StatusCode.ERROR_PIC_CODE);
		}
		//密码校验
		if(!validateRequestLimit(NoticeConstant.LOGIN_SUBMIT+"_"+mobilePhone+"_error",5)){
			return new ResultCode(StatusCode.ERROR_PWD_LOGIN);
		}
		String md5Pwd = AccountDigestUtils.getMd5Pwd(admin.getUserCode(), passWord);
		if (!admin.getPwd().equals(md5Pwd)) {
			String mobileKey=NoticeConstant.LOGIN_SUBMIT+"_"+mobilePhone+"_error";
			String value = redisStringManager.find(mobileKey);
			updateRequestLimit(mobileKey,value,15);
			return new ResultCode(StatusCode.ERROR_PWD_VALIDATE);
		}

		//用户状态验证
		if (admin.getStatus().intValue() != EnumAdminStatus.STATUS_NORMAL.getStatus()) {
			return new ResultCode(StatusCode.ERROR_ACCOUNT_LOCK_OR_LEAVE);
		}
		// web登录处理token
		String token = IDGenerator.uuid();
		String signToken = ydApiTokenService.processBackApiToken(IpUtil.getIp(getRequest()), deviceId, deviceName, token, admin);
		admin.setToken(signToken);// 传给前端token

		String uri = getRequest().getRequestURI().trim();
		uri = uri.trim();
		if (uri.endsWith("/") && uri.length() > 1) {
			uri = uri.substring(0, uri.length() - 1);
		}
		SysLog sysLog = new SysLog();
		sysLog.setAdminId(admin.getId());
		sysLog.setIp(getIp());
		sysLog.setModuleType(MODULE_NAME);
		sysLog.setOprateType(SysLogConstant.OPRATE_LOGIN);
		sysLog.setMsg(admin.getUserCode());
		sysLog.setName(MODULE_NAME);
		sysLog.setUri(uri);
		ydSysLogService.insertSelective(sysLog);
		return new ResultCode(StatusCode.SUCCESS,admin);
	}

	@RequestMapping(value = "/loginout", method = RequestMethod.POST)
	@ResponseBody
	public ResultCode loginout(HttpServletRequest request, HttpServletResponse response) {
		ApiToken apiToken = getApiToken();
		if (apiToken != null) {
			ydApiTokenService.updateApiTokenStatus(apiToken, EnumApiTokenStatus.STATUS_DISABLED);
			addSysLog(MODULE_NAME, SysLogConstant.OPRATE_LOGIN_OUT, apiToken.getUserCode());
		}
		return new ResultCode(StatusCode.SUCCESS);
	}

}
