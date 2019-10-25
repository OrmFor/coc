package com.yinmimoney.web.p2pnew.controller.admin;

import cc.s2m.util.Page;
import cc.s2m.web.utils.webUtils.vo.Expressions;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.core.ResultCode;
import com.yinmimoney.web.p2pnew.core.StatusCode;
import com.yinmimoney.web.p2pnew.dto.DataLookInfo;
import com.yinmimoney.web.p2pnew.dto.PlatformIncomeInfo;
import com.yinmimoney.web.p2pnew.enums.EnumInComeType;
import com.yinmimoney.web.p2pnew.pojo.*;
import com.yinmimoney.web.p2pnew.service.*;
import com.yinmimoney.web.p2pnew.util.DateUtil;
import com.yinmimoney.web.p2pnew.util.StringUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by dyf on 2019/6/26 13:34
 */
@RestController("admin_dataStatisticsController")
@RequestMapping("/admin/dataStatistics")
public class DataStatisticsController extends BaseController {

    @Autowired
    private IOrderDetail orderDetailService;
    @Autowired
    private IOrder orderService;
    @Autowired
    private ICity cityService;
    @Autowired
    private IUser userService;
    @Autowired
    private ICityDetail cityDetai1Service;
    @Autowired
    private ICommonOrderDetail commonOrderDetailService;
    @Autowired
    private ICommonOrder commonOrderService;

    /**数据总览*/
    @RequestMapping("/data/look")
    public ResultCode dataLook(){
        List<DataLookInfo> dataLookInfos = orderDetailService.getDataLookInfos();
        return new ResultCode(StatusCode.SUCCESS,dataLookInfos);
    }

    /**其他交易数据总览*/
    @RequestMapping("/data/otherlook")
    public ResultCode dataOtherLook(){
        List<DataLookInfo> dataLookInfos = commonOrderDetailService.getDataOtherLookInfos();
        return new ResultCode(StatusCode.SUCCESS,dataLookInfos);
    }

    /**平台收益*/
    @RequestMapping("/platform/income")
    public ResultCode platformIncome(){
        JSONObject json = getJsonFromRequest();
        Integer dayTime = json.getInteger("dayTime");
        String type = json.getString("type");
        if(dayTime==null){
            dayTime=1;
        }
        String startTime="";
        String endTime="";
        String dateFormat="";
        Integer dateType=0;
        switch (dayTime){
            case 1:startTime= DateUtil.rollDayFormat(DateUtil.getNow(),-6,"yyyy-MM-dd");
                endTime=DateUtil.dateStr(DateUtil.getNow(),"yyyy-MM-dd");
                dateFormat="%Y-%m-%d";
                dateType=1;
                break;
            case 2:startTime=DateUtil.rollDayFormat(DateUtil.getNow(),-30,"yyyy-MM-dd");
                endTime=DateUtil.dateStr(DateUtil.getNow(),"yyyy-MM-dd");
                dateFormat="%Y-%m-%d";
                dateType=2;
                break;
            case 3:startTime=DateUtil.rollYearFormat(DateUtil.getNow(),-1,"yyyy-MM-dd");
                endTime=DateUtil.dateStr(DateUtil.getNow(),"yyyy-MM-dd");
                dateFormat="%Y-%m";
                dateType=3;
                break;
            default:startTime=DateUtil.rollYearFormat(DateUtil.getNow(),-1,"yyyy-MM-dd");
                endTime=DateUtil.dateStr(DateUtil.getNow(),"yyyy-MM-dd");
                dateFormat="%Y-%m";
                dateType=3;
        }
        Map<String,Object> cond = new HashMap();
        cond.put("startTime",startTime);
        cond.put("endTime",endTime);
        cond.put("dateFormat",dateFormat);
        List<PlatformIncomeInfo> platformIncomeInfos = new ArrayList<>();
        if(EnumInComeType.OTHER.getName().equals(type)){
            platformIncomeInfos = commonOrderDetailService.getPlatformIncomeInfoOthers(cond);
        }else {
            platformIncomeInfos = orderDetailService.getPlatformIncomeInfos(cond);
        }
        if(dateType==1){
            for(int i=0;i<7;i++){
                String dayFormat=DateUtil.rollDayFormat(DateUtil.dateStr(endTime,"yyyy-MM-dd"),-i,"yyyy-MM-dd");
                int flag=0;
                for(int j=0;j<platformIncomeInfos.size();j++){
                    if(platformIncomeInfos.get(j).getTime().equals(dayFormat)){
                        flag=1;
                        break;
                    }
                }
                if(flag==0){
                    PlatformIncomeInfo platformIncomeInfo = new PlatformIncomeInfo();
                    platformIncomeInfo.setTime(dayFormat);
                    platformIncomeInfo.setIncome(BigDecimal.ZERO);
                    platformIncomeInfos.add(platformIncomeInfo);
                }
            }
        }
        if(dateType==2){
            for(int i=0;i<31;i++){
                String dayFormat=DateUtil.rollDayFormat(DateUtil.dateStr(endTime,"yyyy-MM-dd"),-i,"yyyy-MM-dd");
                int flag=0;
                for(int j=0;j<platformIncomeInfos.size();j++){
                    if(platformIncomeInfos.get(j).getTime().equals(dayFormat)){
                        flag=1;
                        break;
                    }
                }
                if(flag==0){
                    PlatformIncomeInfo platformIncomeInfo = new PlatformIncomeInfo();
                    platformIncomeInfo.setTime(dayFormat);
                    platformIncomeInfo.setIncome(BigDecimal.ZERO);
                    platformIncomeInfos.add(platformIncomeInfo);
                }
            }
        }
        if(dateType==3){
            for(int i=0;i<12;i++){
                String dayFormat=DateUtil.rollMonFormat(DateUtil.dateStr(endTime,"yyyy-MM"),-i,"yyyy-MM");
                int flag=0;
                for(int j=0;j<platformIncomeInfos.size();j++){
                    if(platformIncomeInfos.get(j).getTime().equals(dayFormat)){
                        flag=1;
                        break;
                    }
                }
                if(flag==0){
                    PlatformIncomeInfo platformIncomeInfo = new PlatformIncomeInfo();
                    platformIncomeInfo.setTime(dayFormat);
                    platformIncomeInfo.setIncome(BigDecimal.ZERO);
                    platformIncomeInfos.add(platformIncomeInfo);
                }
            }
        }
        platformIncomeInfos.sort(Comparator.comparing(x->x.getTime()));
        return new ResultCode(StatusCode.SUCCESS,platformIncomeInfos);
    }




    /**交易列表信息*/
    @RequestMapping("/order/list")
    public ResultCode getOrderlist(){
        JSONObject json = getJsonFromRequest();
        String orderNo = json.getString("orderNo");
        Order cond = new Order();
        if(StringUtil.isNotBlank(orderNo)){
            cond.setOrderNo(orderNo);
        }
        cond.setOrderBy("create_time desc");
        String addTimeBegin = json.getString("addTimeBegin");
        String addTimeEnd = json.getString("addTimeEnd");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (!Strings.isNullOrEmpty(addTimeBegin)) {
                cond.and(Expressions.ge("create_time", format.parse(addTimeBegin)));
            }
            if (!Strings.isNullOrEmpty(addTimeEnd)) {
                cond.and(Expressions.lt("create_time", DateUtils.addDays(format.parse(addTimeEnd), 1)));
            }
        } catch (Exception e) {}
        Integer page = json.getInteger("page");
        if(page==null){
            page=1;
        }
        cond.setPageNumber(page);
        cond.setPageSize(10);
        Page<Order> pageBean = orderService.getPage(cond, getRequest().getParameterMap());
        pageBean.getResult().forEach(x->{
            String cityCode = x.getCityCode();
            if(cityCode!=null){
                City city = cityService.selectByCityCode(cityCode);
                if(city!=null){
                    x.setCityCode(city.getCityName());
                }
            }
            String orderNo1 = x.getOrderNo();
            if(orderNo1!=null){
                CityDetail cityDetail = new CityDetail();
                cityDetail.setOrderNo(orderNo1);
                CityDetail byCondition = cityDetai1Service.getByCondition(cityDetail);
                if(byCondition!=null){
                    x.setMessageIsOpen(byCondition.getMessageStatus());
                }
            }
        });
        JSONObject result = new JSONObject();
        result.put("list",pageBean.getResult());
        result.put("pageNumber",pageBean.getCurPage());
        result.put("totalPage",pageBean.getTotalPage());
        result.put("totalCount",pageBean.getTotalRow());
        return new ResultCode(StatusCode.SUCCESS,result);
    }

    /**其他交易列表信息*/
    @RequestMapping("/otherOrder/list")
    public ResultCode getOtherOrderlist(){
        JSONObject json = getJsonFromRequest();
        String orderNo = json.getString("orderNo");
        CommonOrder cond = new CommonOrder();
        if(StringUtil.isNotBlank(orderNo)){
            cond.setOrderNo(orderNo);
        }
        String operateType = json.getString("operateType");
        if(StringUtil.isNotBlank(operateType)){
            cond.setOperateType(operateType);
        }
        cond.setOrderBy("create_time desc");
        String addTimeBegin = json.getString("addTimeBegin");
        String addTimeEnd = json.getString("addTimeEnd");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (!Strings.isNullOrEmpty(addTimeBegin)) {
                cond.and(Expressions.ge("create_time", format.parse(addTimeBegin)));
            }
            if (!Strings.isNullOrEmpty(addTimeEnd)) {
                cond.and(Expressions.lt("create_time", DateUtils.addDays(format.parse(addTimeEnd), 1)));
            }
        } catch (Exception e) {}
        Integer page = json.getInteger("page");
        if(page==null){
            page=1;
        }
        cond.setPageNumber(page);
        cond.setPageSize(10);
        Page<CommonOrder> pageBean = commonOrderService.getPage(cond, getRequest().getParameterMap());
        pageBean.getResult().forEach(x->{
            String cityCode = x.getCityCode();
            if(cityCode!=null){
                City city = cityService.selectByCityCode(cityCode);
                if(city!=null){
                    x.setCityCode(city.getCityName());
                }
            }
            x.getOperateTypeStr();
        });
        JSONObject result = new JSONObject();
        result.put("list",pageBean.getResult());
        result.put("pageNumber",pageBean.getCurPage());
        result.put("totalPage",pageBean.getTotalPage());
        result.put("totalCount",pageBean.getTotalRow());
        return new ResultCode(StatusCode.SUCCESS,result);
    }





    /**交易详情*/
    @RequestMapping("/order/info")
    public ResultCode getOrderInfo(){
        JSONObject json = getJsonFromRequest();
        String orderNo = json.getString("orderNo");
        if(StringUtil.isBlank(orderNo)){
            return new ResultCode(StatusCode.ERROR_LACK_PARAM);
        }
        Order order = orderService.selectByOrderNo(orderNo);
        if(order==null){
            return new ResultCode(StatusCode.ERROR_PARAM);
        }
        City city = cityService.selectByCityCode(order.getCityCode());
        if(city!=null){
            order.setCityCode(city.getCityName());
        }
        User user = userService.selectByUserCode(order.getBuyerCode());
        if(user!=null){
            order.setBuyerName(user.getUserName());
        }
        User user2 = userService.selectByUserCode(order.getSellerCode());
        if(user2!=null){
            order.setSellerName(user2.getUserName());
        }
        return new ResultCode(StatusCode.SUCCESS,order);
    }


    @RequestMapping("/otherOrder/info")
    public ResultCode getOtherOrderInfo(){
        JSONObject json = getJsonFromRequest();
        String orderNo = json.getString("orderNo");
        if(StringUtil.isBlank(orderNo)){
            return new ResultCode(StatusCode.ERROR_LACK_PARAM);
        }
        CommonOrder order = commonOrderService.selectByOrderNo(orderNo);
        if(order==null){
            return new ResultCode(StatusCode.ERROR_PARAM);
        }
        City city = cityService.selectByCityCode(order.getCityCode());
        if(city!=null){
            order.setCityCode(city.getCityName());
        }
        User user = userService.selectByUserCode(order.getOperatorCode());
         if(user != null){
             order.setOperatorCode(user.getUserCode());
             order.setOperatorName(user.getUserName());
         }
        return new ResultCode(StatusCode.SUCCESS,order);
    }
}
