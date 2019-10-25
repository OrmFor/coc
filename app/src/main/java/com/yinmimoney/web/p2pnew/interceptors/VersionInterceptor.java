package com.yinmimoney.web.p2pnew.interceptors;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.yinmimoney.web.p2pnew.core.ResultCode;
import com.yinmimoney.web.p2pnew.core.StatusCode;

public class VersionInterceptor extends HandlerInterceptorAdapter {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 增加版本判断
		String version = request.getHeader("version");
		if (Strings.isNullOrEmpty(version)) {
			writeJson(response, new ResultCode(StatusCode.ERROR_VERSION));
			return false;
		}
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
