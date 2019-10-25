package com.yinmimoney.web.p2pnew.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.yinmimoney.web.p2pnew.constant.SysLogConstant;
import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.pojo.AdminActions;
import com.yinmimoney.web.p2pnew.pojo.AdminRoleActions;
import com.yinmimoney.web.p2pnew.pojo.Roles;
import com.yinmimoney.web.p2pnew.service.IAdminActions;
import com.yinmimoney.web.p2pnew.service.IAdminRoleActions;
import com.yinmimoney.web.p2pnew.service.IRoles;

@Controller("admin_RolesController")
@RequestMapping("/admin/roles")
public class RolesController extends BaseController {

	private static final String MODULE_NAME = "角色管理";

	@Autowired
	private IRoles rolesService;
	@Autowired
	private IAdminActions adminActionsService;
	@Autowired
	private IAdminRoleActions adminRoleActionsService;

	@RequestMapping(value = "/list")
	public String list(Model model, Roles bean) {
		model.addAttribute("bean", bean);
		if (bean == null) {
			bean = new Roles();
		}
		List<Roles> list = rolesService.getList(bean);
		model.addAttribute("list", list);
		return "admin/roles";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, java.lang.Integer id) {
		if (id != null) {
			Roles bean = rolesService.selectByPrimaryKey(id);
			model.addAttribute("bean", bean);
			// 获取该角色的权限
			AdminRoleActions condition = new AdminRoleActions();
			condition.setRid(id);
			List<AdminRoleActions> myActions = adminRoleActionsService.getList(condition);
			model.addAttribute("myActions", myActions);
		}
		// 取出所有权限
		AdminActions condition = new AdminActions();
		condition.setOrderBy("paixu ASC");
		List<AdminActions> actions = adminActionsService.getList(condition);
		model.addAttribute("actions", actions);
		return "admin/roles_add";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public String save(Model model, Roles bean, Integer[] actionsIds) {
		if (bean == null) {
			return "empty";
		}
		if (bean.getId() == null) {
			// 添加
			rolesService.insertSelective(bean);
			addSysLog(MODULE_NAME, SysLogConstant.OPRATE_ADD, JSONObject.toJSONString(bean));
		} else {
			// 修改
			rolesService.updateByPrimaryKeySelective(bean);
			addSysLog(MODULE_NAME, SysLogConstant.OPRATE_EDIT, JSONObject.toJSONString(bean));
			// 删除原来的权限
			AdminRoleActions condition = new AdminRoleActions();
			condition.setRid(bean.getId());
			adminRoleActionsService.delete(condition);
		}
		// 添加权限
		if (actionsIds != null && actionsIds.length > 0) {
			for (Integer actionId : actionsIds) {
				AdminRoleActions roleAction = new AdminRoleActions();
				roleAction.setAid(actionId);
				roleAction.setRid(bean.getId());
				adminRoleActionsService.insertSelective(roleAction);
				addSysLog(MODULE_NAME, SysLogConstant.OPRATE_DEFAULT, JSONObject.toJSONString(roleAction));
			}
		}
		return "success";
	}

	@RequestMapping(value = "/del", method = RequestMethod.POST)
	@ResponseBody
	public String del(java.lang.Integer id) {
		Roles bean = rolesService.selectByPrimaryKey(id);
		if (bean != null) {
			addSysLog(MODULE_NAME, SysLogConstant.OPRATE_DEL, JSONObject.toJSONString(bean));
			rolesService.deleteByPrimaryKey(id);
			// 删除原来的权限
			AdminRoleActions condition = new AdminRoleActions();
			condition.setRid(id);
			adminRoleActionsService.delete(condition);
		}
		return "success";
	}
}