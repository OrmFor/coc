package com.yinmimoney.web.p2pnew.controller.admin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.base.Strings;
import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.pojo.SysTaskLog;
import com.yinmimoney.web.p2pnew.service.ISysTaskLog;

import cc.s2m.util.Page;
import cc.s2m.web.utils.webUtils.vo.Expressions;

@Controller("admin_SysTaskLogController")
@RequestMapping("/admin/sysTaskLog")
public class SysTaskLogController extends BaseController {
	private static final String MODULE_NAME = "定时任务日志";

	@Autowired
	private ISysTaskLog sysTaskLogService;

	@RequestMapping(value = "/list")
	public String list(Model model, SysTaskLog bean, Integer page, String createdAtBegin, String createdAtEnd, String updatedAtBegin, String updatedAtEnd) {
		model.addAttribute("bean", bean);
		model.addAttribute("createdAtBegin", createdAtBegin);
		model.addAttribute("createdAtEnd", createdAtEnd);
		model.addAttribute("updatedAtBegin", updatedAtBegin);
		model.addAttribute("updatedAtEnd", updatedAtEnd);

		if (page == null)
			page = 1;
		if (bean == null) {
			bean = new SysTaskLog();
		}

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if (!Strings.isNullOrEmpty(createdAtBegin)) {
				bean.and(Expressions.ge("created_at", format.parse(createdAtBegin)));
			}
			if (!Strings.isNullOrEmpty(createdAtEnd)) {
				bean.and(Expressions.lt("created_at", DateUtils.addDays(format.parse(createdAtEnd), 1)));
			}
			if (!Strings.isNullOrEmpty(updatedAtBegin)) {
				bean.and(Expressions.ge("updated_at", format.parse(updatedAtBegin)));
			}
			if (!Strings.isNullOrEmpty(updatedAtEnd)) {
				bean.and(Expressions.lt("updated_at", DateUtils.addDays(format.parse(updatedAtEnd), 1)));
			}
		} catch (Exception e) {
		}

		if (!"1".equals(getRequest().getParameter("excel"))) {// 导出 EXCEL
			bean.setPageNumber(page);
			bean.setPageSize(50);
		} else {
			bean.setPageNumber(1);
			bean.setPageSize(Integer.MAX_VALUE);
		}

		Page<SysTaskLog> pageBean = sysTaskLogService.getPage(bean, getRequest().getParameterMap());
		model.addAttribute("pageBean", pageBean);

		return "admin/sysTaskLog";
	}

}