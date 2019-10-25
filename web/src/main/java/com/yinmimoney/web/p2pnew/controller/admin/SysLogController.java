package com.yinmimoney.web.p2pnew.controller.admin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.pojo.Admin;
import com.yinmimoney.web.p2pnew.pojo.SysLog;
import com.yinmimoney.web.p2pnew.service.IAdmin;
import com.yinmimoney.web.p2pnew.service.ISysLog;

import cc.s2m.util.Page;
import cc.s2m.web.utils.webUtils.vo.Expressions;

@Controller("admin_SysLogController")
@RequestMapping("/admin/sysLog")
public class SysLogController extends BaseController {

	@Autowired
	private ISysLog sysLogService;
	@Autowired
	private IAdmin adminService;

	@RequestMapping(value = "/list")
	public String list(Model model, SysLog bean, Integer page, String uriLike, String msgLike, String ipLike, String dateAddBegin, String dateAddEnd) {
		model.addAttribute("bean", bean);
		model.addAttribute("uriLike", uriLike);
		model.addAttribute("msgLike", msgLike);
		model.addAttribute("ipLike", ipLike);
		model.addAttribute("dateAddBegin", dateAddBegin);
		model.addAttribute("dateAddEnd", dateAddEnd);

		if (page == null)
			page = 1;
		if (bean == null) {
			bean = new SysLog();
		}

		if (!Strings.isNullOrEmpty(uriLike)) {
			bean.and(Expressions.like("uri", "%" + uriLike.trim() + "%"));
		}
		if (!Strings.isNullOrEmpty(msgLike)) {
			bean.and(Expressions.like("msg", "%" + msgLike.trim() + "%"));
		}
		if (!Strings.isNullOrEmpty(ipLike)) {
			bean.and(Expressions.like("ip", "%" + ipLike.trim() + "%"));
		}

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if (!Strings.isNullOrEmpty(dateAddBegin)) {
				bean.and(Expressions.ge("date_add", format.parse(dateAddBegin)));
			}
			if (!Strings.isNullOrEmpty(dateAddEnd)) {
				bean.and(Expressions.lt("date_add", DateUtils.addDays(format.parse(dateAddEnd), 1)));
			}
		} catch (Exception e) {
		}

		bean.setPageNumber(page);
		bean.setPageSize(50);

		Page<SysLog> pageBean = sysLogService.getPage(bean, getRequest().getParameterMap());
		model.addAttribute("pageBean", pageBean);

		List<Admin> admins = adminService.getList();
		model.addAttribute("admins", admins);
		Map<String, Admin> adminMap = Maps.newHashMap();
		for (Admin admin : admins) {
			adminMap.put(String.valueOf(admin.getId()), admin);
		}
		model.addAttribute("adminMap", adminMap);

		return "admin/sysLog";
	}

}