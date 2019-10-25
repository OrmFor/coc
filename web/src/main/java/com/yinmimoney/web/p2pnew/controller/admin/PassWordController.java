package com.yinmimoney.web.p2pnew.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Strings;
import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.pojo.Admin;
import com.yinmimoney.web.p2pnew.service.IAdmin;

import cc.s2m.web.utils.webUtils.util.AccountDigestUtils;

@Controller("admin_PassWordController")
@RequestMapping("/admin")
public class PassWordController extends BaseController {

	@Autowired
	private IAdmin adminService;

	@RequestMapping(value = "/editPwd", method = RequestMethod.GET)
	public String editPwd() {
		return "admin/editPass";
	}

	@RequestMapping(value = "/editPwd/save", method = RequestMethod.POST)
	@ResponseBody
	public String save(HttpServletRequest request, HttpServletResponse response, @RequestParam String oldPwd, @RequestParam String passWord, @RequestParam String confirmPassword) {
		if (Strings.isNullOrEmpty(oldPwd)) {
			return "请输入旧密码";
		}
		if (Strings.isNullOrEmpty(passWord)) {
			return "请输入新密码";
		}
		if (Strings.isNullOrEmpty(confirmPassword)) {
			return "请输入确认密码";
		}
		if (!passWord.equals(confirmPassword)) {
			return "密码输入不一致";
		}
		Admin admin = getSessionAdmin();
		if (!admin.getPwd().equals(AccountDigestUtils.getMd5Pwd(admin.getUserName(), oldPwd))) {
			return "密码错误";
		}
		admin.setPwd(AccountDigestUtils.getMd5Pwd(admin.getUserName(), passWord));
		adminService.updateByPrimaryKey(admin);
		return "success";
	}
}
