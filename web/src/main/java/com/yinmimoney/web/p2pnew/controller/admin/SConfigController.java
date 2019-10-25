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
import com.yinmimoney.web.p2pnew.pojo.SConfig;
import com.yinmimoney.web.p2pnew.service.ISConfig;

import cc.s2m.util.Page;
import cc.s2m.web.utils.webUtils.util.JSONResultCode;
import cc.s2m.web.utils.webUtils.vo.Expressions;

@Controller("admin_SConfigController")
@RequestMapping("/admin/sConfig")
public class SConfigController extends BaseController {
	private static final String MODULE_NAME = "参数列表";

	@Autowired
	private ISConfig sConfigService;

	@RequestMapping(value = "/list")
	public String list(Model model, SConfig bean, Integer page, String addTimeBegin, String addTimeEnd, String updateTimeBegin, String updateTimeEnd) {
		model.addAttribute("bean", bean);
		model.addAttribute("addTimeBegin", addTimeBegin);
		model.addAttribute("addTimeEnd", addTimeEnd);
		model.addAttribute("updateTimeBegin", updateTimeBegin);
		model.addAttribute("updateTimeEnd", updateTimeEnd);

		if (page == null)
			page = 1;
		if (bean == null) {
			bean = new SConfig();
		}

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if (!Strings.isNullOrEmpty(addTimeBegin)) {
				bean.and(Expressions.ge("add_time", format.parse(addTimeBegin)));
			}
			if (!Strings.isNullOrEmpty(addTimeEnd)) {
				bean.and(Expressions.lt("add_time", DateUtils.addDays(format.parse(addTimeEnd), 1)));
			}
			if (!Strings.isNullOrEmpty(updateTimeBegin)) {
				bean.and(Expressions.ge("update_time", format.parse(updateTimeBegin)));
			}
			if (!Strings.isNullOrEmpty(updateTimeEnd)) {
				bean.and(Expressions.lt("update_time", DateUtils.addDays(format.parse(updateTimeEnd), 1)));
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

		Page<SConfig> pageBean = sConfigService.getPage(bean, getRequest().getParameterMap());
		model.addAttribute("pageBean", pageBean);

		return "admin/sConfig";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, java.lang.Integer id) {
		if (id != null) {
			SConfig bean = sConfigService.selectByPrimaryKey(id);
			model.addAttribute("bean", bean);
		}
		return "admin/sConfig_add";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public JSONResultCode save(SConfig bean) {
		if (bean == null) {
			return new JSONResultCode(100, "noData");
		}
		if (bean.getId() == null) {
			// 添加
			sConfigService.insertSelective(bean);
			addSysLog(MODULE_NAME, SysLogConstant.OPRATE_ADD, JSONObject.toJSONString(bean));
		} else {
			// 修改
			sConfigService.updateByPrimaryKeySelective(bean);
			addSysLog(MODULE_NAME, SysLogConstant.OPRATE_EDIT, JSONObject.toJSONString(bean));
		}
		return new JSONResultCode(0, "success");
	}

	@RequestMapping(value = "/del", method = RequestMethod.POST)
	@ResponseBody
	public JSONResultCode del(java.lang.Integer id) {
		SConfig bean = sConfigService.selectByPrimaryKey(id);
		if (bean == null) {
			return new JSONResultCode(100, "noData");
		}
		addSysLog(MODULE_NAME, SysLogConstant.OPRATE_DEL, JSONObject.toJSONString(bean));
		sConfigService.deleteByPrimaryKey(id);
		return new JSONResultCode(0, "success");
	}
}