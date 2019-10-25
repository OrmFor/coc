package com.yinmimoney.web.p2pnew.interceptors;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.yinmimoney.web.p2pnew.core.ResultCode;
import com.yinmimoney.web.p2pnew.core.StatusCode;
import com.yinmimoney.web.p2pnew.enums.EnumSysType;
import com.yinmimoney.web.p2pnew.pojo.*;
import com.yinmimoney.web.p2pnew.service.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @ClassName: AdminInterceptor
 * @Description: 管理员权限过滤判断
 * @author 龚国君
 * @date 2015年2月8日 下午3:02:25
 *
 */
public class AuthorityInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private IAdmin ydAdminService;
	@Autowired
	private IAdminActions ydAdminActionsService;
	@Autowired
	private IAdminRoleActions ydAdminRoleActionsService;
	@Autowired
	private IAdminRoles ydAdminRolesService;
	@Autowired
	private IApiToken apiTokenService;

	private List<String> ignoreUrls;// 需要登录但不进行权限判断的链接（无条件放行）

	public List<String> getIgnoreUrls() {
		return ignoreUrls;
	}

	public void setIgnoreUrls(List<String> ignoreUrls) {
		this.ignoreUrls = ignoreUrls;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 如果不用token使用cookie获取登录信息
//		Admin admin = (Admin) request.getAttribute("sessionAdmin");

		ApiToken ydApiToken = (ApiToken) request.getAttribute("apiUser");
		if (ydApiToken == null) {
			writeJson(response, new ResultCode(StatusCode.ERROR_407));
			return false;
		}
		Admin ydAdmin = ydAdminService.getAdminByUserCode(ydApiToken.getUserCode());
		// List<AdminRoles> role = adminRolesService.getList();
		// 去掉末尾的‘/’符号
		String uri = request.getRequestURI();
		if (!Strings.isNullOrEmpty(uri) && uri.endsWith("/")) {
			uri = uri.substring(0, uri.length() - 1);
		}

		// 无条件放行的页面，直接返回
		if (ignoreUrls != null && ignoreUrls.contains(uri)) {
			request.setAttribute("sessionAdmin",ydAdmin);// 放入request
			return true;
		}
		AdminActions actions = new AdminActions();
		actions.setUrl(uri);
		actions.setSysType(EnumSysType.TYPE_ADMING_SYS.getType());
		actions = ydAdminActionsService.getByCondition(actions);
		if (actions == null) {
			actions = new AdminActions();
			actions.setUrl(uri);
			actions.setName("未分配");
			actions.setLevel(1);
			actions.setSysType(EnumSysType.TYPE_ADMING_SYS.getType());
			ydAdminActionsService.insertSelective(actions);
		}
		// 超级管理员跳过校验
		if (ydAdmin.getUserName().equals("admin")) {
			// writeJson(response, new ResultCode(StatusCode.ERROR_409));
			request.setAttribute("sessionAdmin", ydAdmin);// 放入request
			return true;
		}

		// 进行权限判断
		AdminRoleActions roleActions = new AdminRoleActions();
		roleActions.setAid(actions.getId());
		List<AdminRoleActions> roleActionsList = ydAdminRoleActionsService.getList(roleActions);
		if (roleActionsList.isEmpty()) {
			writeJson(response, new ResultCode(StatusCode.ERROR_406));
			return false;
		}
		boolean pass = false;// 是否放行
		for (AdminRoleActions roleActions_ : roleActionsList) {
			AdminRoles adminRole = new AdminRoles();
			adminRole.setAid(ydAdmin.getId());
			adminRole.setRid(roleActions_.getRid());
			adminRole = ydAdminRolesService.getByCondition(adminRole);
			if (adminRole != null) {
				pass = true;
				break;
			}
		}
		if (!pass) {
			writeJson(response, new ResultCode(StatusCode.ERROR_406));
			return false;
		}
		request.setAttribute("sessionAdmin", ydAdmin);// 放入request
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
