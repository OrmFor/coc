package com.yinmimoney.web.p2pnew.controller.admin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.yinmimoney.web.p2pnew.constant.SysLogConstant;
import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.pojo.SysTaskHandel;
import com.yinmimoney.web.p2pnew.service.ISysTaskHandel;

import cc.s2m.util.Page;
import cc.s2m.web.utils.webUtils.vo.Expressions;

@Controller("admin_SysTaskHandelController")
@RequestMapping("/admin/sysTaskHandel")
public class SysTaskHandelController extends BaseController {
	private static final String MODULE_NAME = "定时任务";

	@Autowired
	private ISysTaskHandel sysTaskHandelService;

	@RequestMapping(value = "/list")
	public String list(Model model, SysTaskHandel bean, Integer page, String createdAtBegin, String createdAtEnd, String updatedAtBegin, String updatedAtEnd) {
		model.addAttribute("bean", bean);
		model.addAttribute("createdAtBegin", createdAtBegin);
		model.addAttribute("createdAtEnd", createdAtEnd);
		model.addAttribute("updatedAtBegin", updatedAtBegin);
		model.addAttribute("updatedAtEnd", updatedAtEnd);

		if (page == null)
			page = 1;
		if (bean == null) {
			bean = new SysTaskHandel();
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

		Page<SysTaskHandel> pageBean = sysTaskHandelService.getPage(bean, getRequest().getParameterMap());
		model.addAttribute("pageBean", pageBean);

		return "admin/sysTaskHandel";
	}

	@RequestMapping(value = "/del", method = RequestMethod.POST)
	@ResponseBody
	public String del(Integer id) {
		SysTaskHandel bean = sysTaskHandelService.selectByPrimaryKey(id);
		if (bean == null) {
			return "任务不存在";
		}
		addSysLog(MODULE_NAME, SysLogConstant.OPRATE_DEL, JSONObject.toJSONString(bean));
		sysTaskHandelService.deleteByPrimaryKey(id);
		return "success";
	}

	@RequestMapping(value = "/enable", method = RequestMethod.POST)
	@ResponseBody
	public String enable(Integer id) {
		SysTaskHandel bean = sysTaskHandelService.selectByPrimaryKey(id);
		if (bean == null) {
			return "任务不存在";
		}
		bean.setIsEnabled(!bean.getIsEnabled());
		sysTaskHandelService.updateByPrimaryKey(bean);
		addSysLog(MODULE_NAME, SysLogConstant.OPRATE_EDIT, JSONObject.toJSONString(bean));
		return "success";
	}
}