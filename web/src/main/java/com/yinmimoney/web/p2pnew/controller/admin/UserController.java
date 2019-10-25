package com.yinmimoney.web.p2pnew.controller.admin;


import cc.s2m.util.Page;
import cc.s2m.web.utils.webUtils.vo.Expressions;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.core.ResultCode;
import com.yinmimoney.web.p2pnew.core.StatusCode;
import com.yinmimoney.web.p2pnew.pojo.CityDetail;
import com.yinmimoney.web.p2pnew.pojo.CityDetailHistory;
import com.yinmimoney.web.p2pnew.pojo.User;
import com.yinmimoney.web.p2pnew.service.ICityDetail;
import com.yinmimoney.web.p2pnew.service.ICityDetailHistory;
import com.yinmimoney.web.p2pnew.service.IOrderDetail;
import com.yinmimoney.web.p2pnew.service.IUser;
import com.yinmimoney.web.p2pnew.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@Controller("admin_UserController")
@RequestMapping("/admin/user")
public class UserController extends BaseController {
    private static final String MODULE_NAME = "用户列表";

    @Autowired
    private IUser userService;

    @Autowired
    private ICityDetail cityDetailService;

    @Autowired
    private ICityDetailHistory cityDetailHistoryService;

    /**用户列表*/
    @RequestMapping("/list")
    @ResponseBody
    public ResultCode getList(){
        Map<String,Object> result=new HashMap();
        User cond = new User();
        JSONObject json = getJsonFromRequest();
        String nickName = json.getString("nickName");
        if(StringUtils.isNotBlank(nickName)){
            cond.setUserName(nickName);
        }
        String userCode = json.getString("userCode");
        if(StringUtil.isNotBlank(userCode)){
            cond.setUserCode(userCode);
        }
        cond.setOrderBy("register_time desc");
        String addTimeBegin = json.getString("addTimeBegin");
        String addTimeEnd = json.getString("addTimeEnd");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (!Strings.isNullOrEmpty(addTimeBegin)) {
                cond.and(Expressions.ge("register_time", format.parse(addTimeBegin)));
            }
            if (!Strings.isNullOrEmpty(addTimeEnd)) {
                cond.and(Expressions.lt("register_time", DateUtils.addDays(format.parse(addTimeEnd), 1)));
            }
        } catch (Exception e) {}
        Integer page = json.getInteger("page");
        if(page==null){
            page=1;
        }
        cond.setPageNumber(page);
        cond.setPageSize(10);
        Page<User> pageBean = userService.getPage(cond, getRequest().getParameterMap());
        CityDetail cityDetailCond = new CityDetail();
        pageBean.getResult().forEach(x->{
           cityDetailCond.setUserCode(x.getUserCode());
            int count = cityDetailService.getCount(cityDetailCond);
            x.setOwnCityNumber(count);
        });
        result.put("list",pageBean.getResult());
        result.put("pageNumber",pageBean.getCurPage());
        result.put("totalPage",pageBean.getTotalPage());
        result.put("totalCount",pageBean.getTotalRow());
        return new ResultCode(StatusCode.SUCCESS,result);
    }

    @RequestMapping("/hidden/message")
    @ResponseBody
    public ResultCode hiddenMessage(){
        JSONObject json = getJsonFromRequest();
        String orderNo = json.getString("orderNo");
        if(StringUtil.isBlank(orderNo)){
            return new ResultCode(StatusCode.ERROR_LACK_PARAM);
        }
        CityDetail cityDetail = new CityDetail();
        cityDetail.setOrderNo(orderNo);
        CityDetail byCondition = cityDetailService.getByCondition(cityDetail);
        if(byCondition!=null) {
            byCondition.setMessageStatus(false);
            cityDetailService.updateByPrimaryKey(byCondition);
        }
        CityDetailHistory cityDetailHistory = new CityDetailHistory();
        cityDetailHistory.setHisOrderNo(orderNo);
        CityDetailHistory byCondition1 = cityDetailHistoryService.getByCondition(cityDetailHistory);
        if(byCondition1!=null) {
            byCondition1.setHisMessageStatus(false);
            cityDetailHistoryService.updateByPrimaryKey(byCondition1);
        }
        return new ResultCode(StatusCode.SUCCESS);
    }

    @RequestMapping("/open/message")
    @ResponseBody
    public ResultCode openMessage(){
        JSONObject json = getJsonFromRequest();
        String orderNo = json.getString("orderNo");
        if(StringUtil.isBlank(orderNo)){
            return new ResultCode(StatusCode.ERROR_LACK_PARAM);
        }
        CityDetail cityDetail = new CityDetail();
        cityDetail.setOrderNo(orderNo);
        CityDetail byCondition = cityDetailService.getByCondition(cityDetail);
        if(byCondition!=null) {
            byCondition.setMessageStatus(true);
            cityDetailService.updateByPrimaryKey(byCondition);
        }
        CityDetailHistory cityDetailHistory = new CityDetailHistory();
        cityDetailHistory.setHisOrderNo(orderNo);
        CityDetailHistory byCondition1 = cityDetailHistoryService.getByCondition(cityDetailHistory);
        if(byCondition1!=null) {
            byCondition1.setHisMessageStatus(true);
            cityDetailHistoryService.updateByPrimaryKey(byCondition1);
        }
        return new ResultCode(StatusCode.SUCCESS);
    }
}