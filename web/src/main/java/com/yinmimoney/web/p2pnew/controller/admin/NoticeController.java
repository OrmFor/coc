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
import com.yinmimoney.web.p2pnew.enums.EnumNoticeType;
import com.yinmimoney.web.p2pnew.pojo.Notice;
import com.yinmimoney.web.p2pnew.service.INotice;

import cc.s2m.util.Page;
import cc.s2m.web.utils.webUtils.vo.Expressions;

@Controller("admin_NoticeController")
@RequestMapping("/admin/notice")
public class NoticeController extends BaseController {

	private static final String MODULE_NAME = "短信记录";

    @Autowired
    private INotice noticeService;

    @RequestMapping(value = "/list")
    public String list(Model model, Notice bean, Integer page
			, String addTimeBegin, String addTimeEnd
			) {
        model.addAttribute("bean", bean);
		model.addAttribute("addTimeBegin", addTimeBegin);
		model.addAttribute("addTimeEnd", addTimeEnd);
		
        
        if (page == null) page = 1;
        if (bean == null) {
            bean = new Notice();
        }
        
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
          if (!Strings.isNullOrEmpty(addTimeBegin)) {
            bean.and(Expressions.ge("add_time", format.parse(addTimeBegin)));
          }
          if (!Strings.isNullOrEmpty(addTimeEnd)) {
            bean.and(Expressions.lt("add_time", DateUtils.addDays(format.parse(addTimeEnd), 1)));
          }
        } catch (Exception e) {}
        
        if (!"1".equals(getRequest().getParameter("excel"))) {//导出 EXCEL
          bean.setPageNumber(page);
          bean.setPageSize(50);
        } else {
          bean.setPageNumber(1);
          bean.setPageSize(Integer.MAX_VALUE);
        }
        
		bean.setType(EnumNoticeType.TYPE_SMS.getType());
        Page<Notice> pageBean = noticeService.getPage(bean, getRequest().getParameterMap());
        model.addAttribute("pageBean", pageBean);
        
        return "admin/notice";
    }
    
}