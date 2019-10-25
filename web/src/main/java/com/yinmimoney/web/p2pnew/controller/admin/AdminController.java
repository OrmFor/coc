package com.yinmimoney.web.p2pnew.controller.admin;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.yinmimoney.web.p2pnew.constant.SysLogConstant;
import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.controller.dto.AdminDto;
import com.yinmimoney.web.p2pnew.enums.EnumAdminStatus;
import com.yinmimoney.web.p2pnew.enums.EnumUserCodeType;
import com.yinmimoney.web.p2pnew.pojo.Admin;
import com.yinmimoney.web.p2pnew.pojo.AdminRoles;
import com.yinmimoney.web.p2pnew.pojo.Roles;
import com.yinmimoney.web.p2pnew.service.IAdmin;
import com.yinmimoney.web.p2pnew.service.IAdminRoles;
import com.yinmimoney.web.p2pnew.service.IRoles;
import com.yinmimoney.web.p2pnew.util.BeanCovertUtil;
import com.yinmimoney.web.p2pnew.util.UserCodeGenerator;

import cc.s2m.util.Page;
import cc.s2m.web.utils.webUtils.util.AccountDigestUtils;

@Controller("admin_AdminController")
@RequestMapping("/admin/admin")
public class AdminController extends BaseController {

	private static final String MODULE_NAME = "管理员管理";

	@Autowired
	private IAdmin adminService;
	@Autowired
	private IAdminRoles adminRolesService;
	@Autowired
	private IRoles rolesService;
	@Autowired
	private UserCodeGenerator userCodeGenerator;

	@RequestMapping(value = "/list")
	public String list(Model model, Admin bean, Integer page) {
		model.addAttribute("bean", bean);

		if (page == null)
			page = 1;
		if (bean == null) {
			bean = new Admin();
		}

		bean.setPageNumber(page);
		bean.setPageSize(50);

		Page<Admin> pageBean = adminService.getPage(bean, getRequest().getParameterMap());
		model.addAttribute("pageBean", pageBean);
		return "admin/admin";
	}

	@RequestMapping(value = "/lock", method = RequestMethod.POST)
	@ResponseBody
	public String lock(Integer id) {
		if (id == null) {
			return "id为空";
		}
		Admin bean = adminService.selectByPrimaryKey(id);
		if (bean == null) {
			return "管理员不存在";
		}
		Admin condition = new Admin();
		condition.setId(id);
		condition.setStatus(EnumAdminStatus.STATUS_NORMAL.getStatus());
		Admin update = new Admin();
		update.setStatus(EnumAdminStatus.STATUS_LOCK.getStatus());
		int row = adminService.updateByCondition(update, condition);
		if (row != 1) {
			return "稍后重试";
		}
		addSysLog(MODULE_NAME, SysLogConstant.OPRATE_LOCK, JSONObject.toJSONString(bean));
		return "success";
	}

	@RequestMapping(value = "/unlock", method = RequestMethod.POST)
	@ResponseBody
	public String unlock(Integer id) {
		if (id == null) {
			return "id为空";
		}
		Admin bean = adminService.selectByPrimaryKey(id);
		if (bean == null) {
			return "管理员不存在";
		}
		Admin condition = new Admin();
		condition.setId(id);
		condition.setStatus(EnumAdminStatus.STATUS_LOCK.getStatus());
		Admin update = new Admin();
		update.setStatus(EnumAdminStatus.STATUS_NORMAL.getStatus());
		int row = adminService.updateByCondition(update, condition);
		if (row != 1) {
			return "稍后重试";
		}
		addSysLog(MODULE_NAME, SysLogConstant.OPRATE_UNLOCK, JSONObject.toJSONString(bean));
		return "success";
	}

	@RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
	@ResponseBody
	public String resetPwd(Integer id, String pwd) {
		if (id == null) {
			return "id为空";
		}
		if (Strings.isNullOrEmpty(pwd)) {
			return "密码为空";
		}
		Admin bean = adminService.selectByPrimaryKey(id);
		if (bean == null) {
			return "管理员不存在";
		}
		bean.setPwd(AccountDigestUtils.getMd5Pwd(bean.getUserName(), pwd.trim()));
		adminService.updateByPrimaryKeySelective(bean);
		addSysLog(MODULE_NAME, SysLogConstant.OPRATE_RESET_PWD, JSONObject.toJSONString(bean));
		return "success";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, Integer id) {
		if (id != null) {
			Admin bean = adminService.selectByPrimaryKey(id);
			model.addAttribute("bean", bean);
			// 获取该管理员的角色
			AdminRoles condition = new AdminRoles();
			condition.setAid(id);
			List<AdminRoles> myRoles = adminRolesService.getList(condition);
			model.addAttribute("myRoles", myRoles);
		}
		// 取出所有角色
		List<Roles> roles = rolesService.getList();
		model.addAttribute("roles", roles);
		return "admin/admin_add";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public String save(@Valid AdminDto dto, BindingResult result) {
		if (result.hasErrors()) {
			Map<String, String> errors = getErrors(result);
			return JSON.toJSONString(errors);
		}
		Admin bean = BeanCovertUtil.beanCovert(dto, Admin.class);
		if (bean.getId() == null) {
			if (Strings.isNullOrEmpty(dto.getPwd())) {
				return "密码为空";
			}
			// 添加
			// 用户名重复性检测
			Admin condition = new Admin();
			condition.setUserName(bean.getUserName().trim());
			Admin admin = adminService.getByCondition(condition);
			if (admin != null) {
				return "用户名已存在";
			}
			// 手机号重复性检测
			condition = new Admin();
			condition.setMobilePhone(bean.getMobilePhone().trim());
			admin = adminService.getByCondition(condition);
			if (admin != null) {
				return "手机号已存在";
			}
			bean.setPwd(AccountDigestUtils.getMd5Pwd(bean.getUserName(), bean.getPwd()));
			bean.setUserCode(userCodeGenerator.getUserCode(EnumUserCodeType.TYPE_ADMIN.getType()));
			adminService.insertSelective(bean);
			addSysLog(MODULE_NAME, SysLogConstant.OPRATE_ADD, JSONObject.toJSONString(bean));
		} else {
			// 修改
			// 用户名重复性检测
			Admin condition = new Admin();
			condition.setUserName(bean.getUserName().trim());
			Admin admin = adminService.getByCondition(condition);
			if (admin != null && !admin.getId().equals(bean.getId())) {
				return "用户名已存在";
			}
			condition = new Admin();
			condition.setMobilePhone(bean.getMobilePhone().trim());
			admin = adminService.getByCondition(condition);
			if (admin != null && !admin.getId().equals(bean.getId())) {
				return "手机号已存在";
			}
			adminService.updateByPrimaryKeySelective(bean);
			 //删除原来的权限
			AdminRoles cond = new AdminRoles();
			cond.setAid(bean.getId());
			adminRolesService.delete(cond);
			addSysLog(MODULE_NAME, SysLogConstant.OPRATE_EDIT, JSONObject.toJSONString(bean));
		}
		// 添加角色
		if (dto.getRoleIds() != null && dto.getRoleIds().length > 0) {
			for (Integer roleId : dto.getRoleIds()) {
				AdminRoles role = new AdminRoles();
				role.setAid(bean.getId());
				role.setRid(roleId);
				adminRolesService.insertSelective(role);
			}
		}
		return "success";
	}

	@RequestMapping(value = "/del", method = RequestMethod.POST)
	@ResponseBody
	public String del(Integer id) {
		if (id == null) {
			return "id为空";
		}
		Admin bean = adminService.selectByPrimaryKey(id);
		if (bean == null) {
			return "管理员不存在";
		}
		adminService.deleteByPrimaryKey(id);
		// 删除原来的权限
		AdminRoles condition = new AdminRoles();
		condition.setAid(id);
		adminRolesService.delete(condition);
		addSysLog(MODULE_NAME, SysLogConstant.OPRATE_DEL, JSONObject.toJSONString(bean));
		return "success";
	}

}