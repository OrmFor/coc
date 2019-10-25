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

import com.google.common.base.Strings;
import com.yinmimoney.web.p2pnew.constant.SysLogConstant;
import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.pojo.NoticeType;
import com.yinmimoney.web.p2pnew.service.INoticeType;

import cc.s2m.util.Page;
import cc.s2m.web.utils.webUtils.util.JSONResultCode;
import cc.s2m.web.utils.webUtils.vo.Expressions;

@Controller("admin_NoticeTypeController")
@RequestMapping("/admin/noticeType")
public class NoticeTypeController extends BaseController {

	private static final String MODULE_NAME = "消息模板";

	@Autowired
	private INoticeType noticeTypeService;

	@RequestMapping(value = "/list")
	public String list(Model model, NoticeType bean, Integer page, String addTimeBegin, String addTimeEnd, String updateTimeBegin, String updateTimeEnd, String nameLike,
			String nidLike, String titleLike) {
		model.addAttribute("bean", bean);
		model.addAttribute("addTimeBegin", addTimeBegin);
		model.addAttribute("addTimeEnd", addTimeEnd);
		model.addAttribute("updateTimeBegin", updateTimeBegin);
		model.addAttribute("updateTimeEnd", updateTimeEnd);
		model.addAttribute("nameLike", nameLike);
		model.addAttribute("nidLike", nidLike);
		model.addAttribute("titleLike", titleLike);

		if (page == null)
			page = 1;
		if (bean == null) {
			bean = new NoticeType();
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
		if (!Strings.isNullOrEmpty(nidLike)) {
			bean.and(Expressions.like("nid", "%" + nidLike.trim() + "%"));
		}
		if (!Strings.isNullOrEmpty(nameLike)) {
			bean.and(Expressions.like("name", "%" + nameLike.trim() + "%"));
		}
		if (!Strings.isNullOrEmpty(titleLike)) {
			bean.and(Expressions.like("title_templet", "%" + titleLike.trim() + "%"));
		}

		bean.setPageNumber(page);
		bean.setPageSize(50);

		Page<NoticeType> pageBean = noticeTypeService.getPage(bean, getRequest().getParameterMap());
		model.addAttribute("pageBean", pageBean);

		return "admin/noticeType";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, java.lang.Integer id) {
		if (id != null) {
			NoticeType bean = noticeTypeService.selectByPrimaryKey(id);
			model.addAttribute("bean", bean);
		}
		return "admin/noticeType_add";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public JSONResultCode save(NoticeType bean, String content) {
		if (bean == null) {
			return new JSONResultCode(100, "noData");
		}
		bean.setTemplet(content);
		if (bean.getId() == null) {
			// 添加
			noticeTypeService.insertSelective(bean);
			addSysLog(MODULE_NAME, SysLogConstant.OPRATE_ADD, bean.getName() + ":" + bean.getTitleTemplet());
		} else {
			// 修改
			noticeTypeService.updateByPrimaryKeySelective(bean);
			addSysLog(MODULE_NAME, SysLogConstant.OPRATE_EDIT, bean.getName() + ":" + bean.getTitleTemplet());
		}
		return new JSONResultCode(0, "success");
	}

	@RequestMapping(value = "/del", method = RequestMethod.POST)
	@ResponseBody
	public JSONResultCode del(java.lang.Integer id) {
		NoticeType bean = noticeTypeService.selectByPrimaryKey(id);
		if (bean == null) {
			return new JSONResultCode(100, "noData");
		}
		addSysLog(MODULE_NAME, SysLogConstant.OPRATE_DEL, bean.getName() + ":" + bean.getTitleTemplet());
		noticeTypeService.deleteByPrimaryKey(id);
		return new JSONResultCode(0, "success");
	}
}