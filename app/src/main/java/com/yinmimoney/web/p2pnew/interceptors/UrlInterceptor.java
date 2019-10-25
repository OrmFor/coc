package com.yinmimoney.web.p2pnew.interceptors;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.yinmimoney.web.p2pnew.core.ResultCode;
import com.yinmimoney.web.p2pnew.core.StatusCode;
import com.yinmimoney.web.p2pnew.util.RedisUtil;

import cc.s2m.util.IpUtil;
import cc.s2m.web.utils.webUtils.StaticProp;

public class UrlInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LogManager.getLogger(UrlInterceptor.class);

	@Autowired
	private RedisUtil redisUtil;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 增加url判断
		String uri = request.getRequestURI();
		if (!Strings.isNullOrEmpty(uri) && uri.endsWith("/")) {
			uri = uri.substring(0, uri.length() - 1);
		}
		String ip = IpUtil.getIp(request);
		logger.info("uri={}，ip={}", uri, ip);
		String key = ip + uri;
		boolean exist = redisUtil.exist(key);
		String timeoutStr = StaticProp.sysConfig.get("url.request.time.timeout");
		Integer timeout = 500;
		if (!Strings.isNullOrEmpty(timeoutStr))
			timeout = Integer.parseInt(timeoutStr);
		redisUtil.addOrUpdateObjectWithTimeout(key, "1", timeout, TimeUnit.MILLISECONDS);
		if (exist) {
			logger.error("访问太过频繁，请稍后重试，uri={}，ip={}", uri, ip);
			writeJson(response, new ResultCode(StatusCode.ERROR_411));
			return false;
		}
		String time = request.getParameter("time");
		if (Strings.isNullOrEmpty(time)) {
			logger.error("缺少参数time，uri={}，ip={}", uri, ip);
			writeJson(response, new ResultCode(StatusCode.ERROR_LACK_PARAM));
			return false;
		}
		String validate = request.getParameter("validate");
		if (Strings.isNullOrEmpty(validate)) {
			logger.error("缺少参数validate，uri={}，ip={}", uri, ip);
			writeJson(response, new ResultCode(StatusCode.ERROR_LACK_PARAM));
			return false;
		}
		String validateStr = StaticProp.sysConfig.get("url.request.validate");
		String sign = DigestUtils.md5Hex(time + validateStr);
		if (!sign.equals(validate)) {
			logger.error("验签异常，uri={}，ip={}", uri, ip);
			writeJson(response, new ResultCode(StatusCode.ERROR_PARAM));
			return false;
		}
		logger.info("请求，uri={}，ip={}，time={}，validate={}", uri, ip, time, validate);
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
