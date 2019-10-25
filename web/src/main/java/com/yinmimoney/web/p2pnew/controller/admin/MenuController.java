package com.yinmimoney.web.p2pnew.controller.admin;

import cc.s2m.web.utils.webUtils.vo.Expressions;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.core.ResultCode;
import com.yinmimoney.web.p2pnew.core.StatusCode;
import com.yinmimoney.web.p2pnew.enums.EnumSysType;
import com.yinmimoney.web.p2pnew.pojo.Admin;
import com.yinmimoney.web.p2pnew.pojo.AdminActions;
import com.yinmimoney.web.p2pnew.pojo.AdminRoleActions;
import com.yinmimoney.web.p2pnew.pojo.AdminRoles;
import com.yinmimoney.web.p2pnew.service.IAdminActions;
import com.yinmimoney.web.p2pnew.service.IAdminRoleActions;
import com.yinmimoney.web.p2pnew.service.IAdminRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller("admin_MenuController")
@RequestMapping("/admin")
public class MenuController extends BaseController {

	private static final String MODULE_NAME = "菜单";

	@Autowired
	private IAdminActions ydAdminActionsService;
	@Autowired
	private IAdminRoleActions ydAdminRoleActionsService;
	@Autowired
	private IAdminRoles ydAdminRolesService;

	@RequestMapping(value = "/menu", method = RequestMethod.POST)
	@ResponseBody
	private ResultCode putOwnerActions() {
		JSONObject jsonObject = new JSONObject();
		Admin admin = getSessionAdmin();
		if(admin==null){
			return new ResultCode(StatusCode.ERROR_USER_TOKEN_EXPIRED);
		}
		AdminActions adminActions = new AdminActions();
		adminActions.setIsMenu(true);
		adminActions.setOrderBy("paixu ASC");
		adminActions.setSysType(EnumSysType.TYPE_ADMING_SYS.getType());
		if (admin.getUserName().equals("admin")) {
			List<AdminActions> list = ydAdminActionsService.getList(adminActions);
            jsonObject.put("actionList",list);
			return new ResultCode(StatusCode.SUCCESS,jsonObject);
		}
		AdminRoles adminRoles = new AdminRoles();
		adminRoles.setAid(admin.getId());
		List<AdminRoles> adminRolesS = ydAdminRolesService.getList(adminRoles);
		List<Integer> roles = Lists.newArrayList(-1);
		for (AdminRoles ar : adminRolesS) {
			roles.add(ar.getRid());
		}
		AdminRoleActions adminRoleActions = new AdminRoleActions();
		adminRoleActions.and(Expressions.in("rid", roles));
		List<AdminRoleActions> adminRoleActionsS = ydAdminRoleActionsService.getList(adminRoleActions);
		List<Integer> actions = Lists.newArrayList(-1);
		for (AdminRoleActions roleActions : adminRoleActionsS) {
			actions.add(roleActions.getAid());
		}
		adminActions.and(Expressions.in("id", actions));
		List<AdminActions> list = ydAdminActionsService.getList(adminActions);
		jsonObject.put("actionList",list);
		return new ResultCode(StatusCode.SUCCESS,jsonObject);
	}

}
