package com.yinmimoney.web.p2pnew.interceptors;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.yinmimoney.web.p2pnew.core.ResultCode;
import com.yinmimoney.web.p2pnew.core.StatusCode;
import com.yinmimoney.web.p2pnew.enums.EnumApiTokenStatus;
import com.yinmimoney.web.p2pnew.pojo.ApiToken;
import com.yinmimoney.web.p2pnew.pojo.SConfig;
import com.yinmimoney.web.p2pnew.service.IApiToken;
import com.yinmimoney.web.p2pnew.service.ISConfig;
import com.yinmimoney.web.p2pnew.service.IUser;
import com.yinmimoney.web.p2pnew.util.DateUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class TokenInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LogManager.getLogger(TokenInterceptor.class);

	@Autowired
	private ISConfig sConfigService;
	@Autowired
	private IApiToken apiTokenService;
	@Autowired
	private IUser userService;

	private List<String> canNoLoginUrls;// 可以不需要登录的链接，但是登录情况下又需要过token，保存用户信息

	public List<String> getCanNoLoginUrls() {
		return canNoLoginUrls;
	}

	public void setCanNoLoginUrls(List<String> canNoLoginUrls) {
		this.canNoLoginUrls = canNoLoginUrls;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String token = request.getHeader("token");
		logger.info("token={}", token);
		String uri = request.getRequestURI();
		if (!Strings.isNullOrEmpty(uri) && uri.endsWith("/")) {
			uri = uri.substring(0, uri.length() - 1);
		}
		logger.info("uri={}", uri);
		if (canNoLoginUrls != null && canNoLoginUrls.contains(uri) && (Strings.isNullOrEmpty(token)||"null".equals(token))) {
			return true;
		}
		if (Strings.isNullOrEmpty(token) || "null".equals(token)) {
			writeJson(response, new ResultCode(StatusCode.ERROR_TOKEN_EMPTY));
			return false;
		}
		String jsonStr = new String(Base64.decodeBase64(token), "UTF-8");
		JSONObject json = JSONObject.parseObject(jsonStr);
		if (json == null || json.isEmpty()) {
			writeJson(response, new ResultCode(StatusCode.ERROR_LACK_PARAM));
			return false;
		}
		// 校验用户
		String deviceId = json.getString("deviceId");
		String t = json.getString("token");
		if (Strings.isNullOrEmpty(deviceId) || Strings.isNullOrEmpty(t)) {
			writeJson(response, new ResultCode(StatusCode.ERROR_LACK_PARAM));
			return false;
		}
		ApiToken apiToken = new ApiToken();
		apiToken.setDeviceId(deviceId);
		apiToken.setToken(t);
		apiToken = apiTokenService.getByCondition(apiToken);
		if (apiToken == null) {
			writeJson(response, new ResultCode(StatusCode.ERROR_USER_OTHER_DEVICE_IS_LOGIN));
			return false;
		}
		if (apiToken.getStatus().intValue() == EnumApiTokenStatus.STATUS_EXPIRED.getStatus()) {// 过期
			writeJson(response, new ResultCode(StatusCode.ERROR_USER_TOKEN_EXPIRED));
			return false;
		} else if (apiToken.getStatus().intValue() == EnumApiTokenStatus.STATUS_DISABLED.getStatus()) {// 失效
			writeJson(response, new ResultCode(StatusCode.ERROR_USER_TOKEN_DISABLED));
			return false;
		} else if (apiToken.getStatus().intValue() == EnumApiTokenStatus.STATUS_OTHER_DEVICE_LOGIN.getStatus()) {// 另外设备登录顶号
			writeJson(response, new ResultCode(StatusCode.ERROR_USER_OTHER_DEVICE_IS_LOGIN));
			return false;
		}
		SConfig sConfig = sConfigService.getByNid("APP_EXPIRED_TIMES");
		if (sConfig != null) {
			Long appExpiredTimes = Long.parseLong(sConfig.getValue()) * 1000;
			if (DateUtil.getNow().getTime() - apiToken.getDateUpdate().getTime() >= appExpiredTimes) {// token过期
				// 更新token状态为过期
				apiTokenService.updateApiTokenStatus(apiToken, EnumApiTokenStatus.STATUS_EXPIRED);
				writeJson(response, new ResultCode(StatusCode.ERROR_USER_TOKEN_EXPIRED));
				return false;
			}
		}
		// 登出、刷新token不用判断用户激活状态
		/*if (!(uri.contains("logout") || uri.contains("refreshToken"))) {
			User user = userService.selectByUserCode(apiToken.getUserCode());
			if (user.getStatus().intValue() != EnumUserStatus.STATUS_ACTIVITY.getStatus()) {// 未激活
				writeJson(response, new ResultCode(StatusCode.ERROR_ACCOUNT_NOT_ACTIVITY));
				return false;
			}
		}*/
		// 把用户数据信息保存进去
		request.setAttribute("apiUser", apiToken);
		return true;
	}

	private void writeJson(HttpServletResponse response, ResultCode resultCode) {
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(JSONObject.toJSONString(resultCode));
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(out);
		}
	}
}
