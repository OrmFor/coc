package com.yinmimoney.web.p2pnew.controller.admin;

import cc.s2m.util.Page;
import cc.s2m.web.utils.webUtils.vo.Expressions;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.yinmimoney.web.p2pnew.constant.Constant;
import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.core.ResultCode;
import com.yinmimoney.web.p2pnew.core.StatusCode;
import com.yinmimoney.web.p2pnew.dto.CityNameAndCode;
import com.yinmimoney.web.p2pnew.dto.PreOrderDto;
import com.yinmimoney.web.p2pnew.enums.EnumOrderStatus;
import com.yinmimoney.web.p2pnew.pojo.City;
import com.yinmimoney.web.p2pnew.pojo.CommonOrder;
import com.yinmimoney.web.p2pnew.pojo.Order;
import com.yinmimoney.web.p2pnew.service.ICity;
import com.yinmimoney.web.p2pnew.service.IOrder;
import com.yinmimoney.web.p2pnew.util.DateUtil;
import com.yinmimoney.web.p2pnew.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dyf on 2019/8/1 9:30
 */
@RestController("admin_cityController")
@RequestMapping("/admin/city")
public class CityController extends BaseController {
    private static final String NO_CITY_INFO_MSG = "no result";
    @Autowired
    private ICity cityService;

    @Autowired
    private IOrder orderService;


    @RequestMapping(value = "/getNameList")
    public ResultCode getNameList() {
        JSONObject json = getJsonFromRequest();
        String cityName = json.getString("cityName");
        List<CityNameAndCode> list = new ArrayList<CityNameAndCode>();
        if (StringUtils.isNotBlank(cityName)) {
            list = cityService.getCityName(cityName);
        }
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("list", list == null || list.size() == 0 ? NO_CITY_INFO_MSG : list);
        return new ResultCode(StatusCode.SUCCESS, data);
    }


    @RequestMapping("/getPreOrder")
    public ResultCode getPreOrder() {
        JSONObject json = getJsonFromRequest();
        String orderNo = json.getString("orderNo");
        Order cond = new Order();
        cond.and(Expressions.in("status",Lists.newArrayList(EnumOrderStatus.PRE_LOCK.getStatus(),
                EnumOrderStatus.ADMIN_CLOSE.getStatus())));
        cond.and(Expressions.le("create_time", DateUtil.rollMinute(DateUtil.convertToGMT(), -60)));
        if(StringUtil.isNotBlank(orderNo)){
            cond.setOrderNo(orderNo);
        }
        Integer pageSize = json.getInteger("pageSize");
        Integer pageNumber = json.getInteger("pageNumber");
        if (pageSize == null || pageSize < 0) {
            pageSize = 10;
        }

        if (pageNumber == null || pageNumber < 0) {
            pageNumber = 1;
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

        cond.setPageSize(pageSize);
        cond.setPageNumber(pageNumber);

        Page<Order> page = orderService.getPage(cond,getRequest().getParameterMap());
        List<PreOrderDto> dtos = new ArrayList<>();
        if(page != null ){
            List<Order> list = page.getResult();
            if(list != null && list.size() > 0){

                for(Order order : list) {
                    PreOrderDto dto = new PreOrderDto();
                    Long orderTime = DateUtil.getTime(order.getCreateTime());
                    Long current = DateUtil.getTime(DateUtil.convertToGMT());
                    dto.setBuyerCode(order.getBuyerCode());
                    dto.setCityCode(order.getCityCode());
                    dto.setSellerCode(order.getSellerCode());
                    City city = cityService.selectByCityCode(order.getCityCode());
                    if(city != null) {
                        dto.setCityName(city.getCityName());
                    }
                    dto.setCreateTime(order.getCreateTime());
                    dto.setOrderNo(order.getOrderNo());
                    dto.setStatusStr(order.getStatus());
                    dto.setAmount(order.getAmount());
                    dto.setStatus(order.getStatus());
                    dto.setTimeStr( new StringBuilder(
                            String.valueOf(Math.round(( current - orderTime) / Constant.HOUR_TIME)))
                            .append(Constant.HOUR).toString());
                    dtos.add(dto);
                }
            }
        }

        Map<String,Object> data = new HashMap<>();
        data.put("list",dtos);
        data.put("totalCount",page == null ? 0 : page.getTotalRow());

        return new ResultCode(StatusCode.SUCCESS,data);
    }


    @RequestMapping("/modifyPreOrder")
    public ResultCode modifyPreOrder() {
        JSONObject json = getJsonFromRequest();
        String orderNo = json.getString("orderNo");
        if (StringUtils.isBlank(orderNo)) {
            return new ResultCode(StatusCode.ERROR);
        }
        orderService.modifyPreOrder(orderNo);
        return new ResultCode(StatusCode.SUCCESS);
    }
}

