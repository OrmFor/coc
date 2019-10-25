package com.yinmimoney.web.p2pnew.controller;

import cc.s2m.util.Page;
import cc.s2m.web.utils.webUtils.util.JSONResultCode;
import com.google.common.collect.Lists;
import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.pojo.Platform;
import com.yinmimoney.web.p2pnew.service.IPlatform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller("admin_PlatformController")
@RequestMapping("/admin/platform")
public class PlatformController extends BaseController {
    private static final String MODULE_NAME = "platform";// TODO

    @Autowired
    private IPlatform platformService;

    @RequestMapping(value = "/list")
    public String list(Model model, Platform bean, Integer page
			) {
        model.addAttribute("bean", bean);
		
        
        if (page == null) page = 1;
        if (bean == null) {
            bean = new Platform();
        }
        
        
        if (!"1".equals(getRequest().getParameter("excel"))) {//导出 EXCEL
          bean.setPageNumber(page);
          bean.setPageSize(50);
        } else {
          bean.setPageNumber(1);
          bean.setPageSize(Integer.MAX_VALUE);
        }
        
        Page<Platform> pageBean = platformService.getPage(bean, getRequest().getParameterMap());
        model.addAttribute("pageBean", pageBean);
        
        if ("1".equals(getRequest().getParameter("excel"))) {//导出 EXCEL
          return exportExcel(model, pageBean);
        }
        
        return "admin/platform";
    }
    
    private String exportExcel (Model model, Page<Platform> pageBean) {
    	String[] titles = new String[]{
          "id",
          "platformCode",
          "platName",
          "amount",
          "currency",
          "feeRate",
          "walletAddress",
          "clientIdentifier",
          "clientSecret",
          "webhookSecret",
          "webhookUrl",
          "oauthIdentifier",
          "oauthRedirectUrl",
          "paltMessage"
    	};
    	List<String[]> paramList = Lists.newArrayList();
    	DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	for (Platform bean : pageBean.getResult()) {
    		paramList.add(new String[]{
              bean.getId().toString(),
              bean.getPlatformCode(),
              bean.getPlatName(),
              bean.getAmount().toString(),
              bean.getCurrency(),
              bean.getFeeRate().toString(),
              bean.getWalletAddress(),
              bean.getClientIdentifier(),
              bean.getClientSecret(),
              bean.getWebhookSecret(),
              bean.getWebhookUrl(),
              bean.getOauthIdentifier(),
              bean.getOauthRedirectUrl(),
              bean.getPaltMessage()
    		});
		}
    	model.addAttribute("fileName", MODULE_NAME);
    	model.addAttribute("titles", titles);
    	model.addAttribute("paramList", paramList);
    //  addSysLog(MODULE_NAME, SysLog.OPRATE_EXPORT, "EXCEL");
    	return "ExcelviewResolver";
    }

    @RequestMapping(value = "/add",method=RequestMethod.GET)
    public String add(Model model, Integer id) {
        if(id != null){
            Platform bean = platformService.selectByPrimaryKey(id);
            model.addAttribute("bean", bean);
        }
        return "admin/platform_add";
    }

    @RequestMapping(value = "/view",method=RequestMethod.GET)
    public String view(Model model, Integer id) {
        if(id != null){
            Platform bean = platformService.selectByPrimaryKey(id);
            model.addAttribute("bean", bean);
        }
        return "admin/platform_view";
    }
	
    @RequestMapping(value = "/info",method=RequestMethod.POST)
    @ResponseBody
    public Platform info(Integer id) {
    	if(id == null){
    		return new Platform();
    	}
    	Platform bean = platformService.selectByPrimaryKey(id);
    	if(bean == null){
    		return new Platform();
    	}
    	return bean;
    }

    @RequestMapping(value = "/save",method=RequestMethod.POST)
    @ResponseBody
    public JSONResultCode save (Platform bean) {
        if (bean == null) {
            return new JSONResultCode(100, "noData");
        }
        if (bean.getId() == null) {
            //添加
            platformService.insertSelective(bean);
           // addSysLog(MODULE_NAME, SysLog.OPRATE_ADD, JSONObject.toJSONString(bean));
        } else {
            //修改
            platformService.updateByPrimaryKeySelective(bean);
           // addSysLog(MODULE_NAME, SysLog.OPRATE_EDIT, JSONObject.toJSONString(bean));
        }
        return new JSONResultCode(0, "success");
    }

    @RequestMapping(value = "/del",method=RequestMethod.POST)
    @ResponseBody
    public JSONResultCode del (Integer id) {
        Platform bean = platformService.selectByPrimaryKey(id);
        if (bean == null) {
          return new JSONResultCode(100, "noData");
        }
       // addSysLog(MODULE_NAME, SysLog.OPRATE_DEL, JSONObject.toJSONString(bean));
        platformService.deleteByPrimaryKey(id);
        return new JSONResultCode(0, "success");
    }
}