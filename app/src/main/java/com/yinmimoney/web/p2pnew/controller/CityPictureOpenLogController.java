package com.yinmimoney.web.p2pnew.controller;

import cc.s2m.web.utils.webUtils.vo.Expressions;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.core.ResultCode;
import com.yinmimoney.web.p2pnew.core.StatusCode;
import com.yinmimoney.web.p2pnew.enums.EnumOrderStatus;
import com.yinmimoney.web.p2pnew.pojo.*;
import com.yinmimoney.web.p2pnew.service.*;
import com.yinmimoney.web.p2pnew.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * Created by dyf on 2019/8/12 10:35
 */
@RestController("api_cityPictureOpenLog")
@RequestMapping("/api/1.0/cityPictureOpenLog")
public class CityPictureOpenLogController extends BaseController {
    @Autowired
    private ICityPictureOpenLog cityPictureOpenLogService;
    @Autowired
    private IUser userService;
    @Autowired
    private IOrder orderService;


    @RequestMapping("/open")
    public ResultCode open(){
        ApiToken apiToken = getApiToken();
        JSONObject json = getJsonFromRequest();
        String cityCode = json.getString("cityCode");
        String txid = json.getString("txid");
        BigDecimal money = json.getBigDecimal("money");
        if(StringUtil.isAnyBlank(cityCode,txid)||money==null){
            return new ResultCode(StatusCode.ERROR_LACK_PARAM);
        }
        User user = userService.selectByUserCode(apiToken.getUserCode());
        //保存交易
        cityPictureOpenLogService.processOpenCityPicture(cityCode,txid,apiToken.getUserCode(),user.getUserName(),money);
        return new ResultCode(StatusCode.SUCCESS);
    }

    @RequestMapping("/isAbility/addPicture")
    public ResultCode isAddPicture(){
        JSONObject json = getJsonFromRequest();
        String cityCode = json.getString("cityCode");
        if(StringUtil.isBlank(cityCode)){
            return new ResultCode(StatusCode.ERROR_LACK_PARAM);
        }
        boolean isAbility=false;
        CityPictureOpenLog cond = new CityPictureOpenLog();
        cond.setIsValid(0);
        cond.setCityCode(cityCode);
        CityPictureOpenLog cityPictureOpenLog = cityPictureOpenLogService.getByCondition(cond);
        if(cityPictureOpenLog!=null){
            isAbility=true;
        }else{
            Order order = new Order();
            order.setCityCode(cityCode);
            order.and(Expressions.in("status", Lists.newArrayList(EnumOrderStatus.RECEIVED.getStatus(),
                    EnumOrderStatus.COMPLETED.getStatus())));
            order.and(Expressions.ge("amount",15));
            Order byCondition = orderService.getByCondition(order);
            if(byCondition!=null){
                isAbility=true;
            }
        }
        JSONObject result = new JSONObject();
        result.put("isAbility",isAbility);
        return new ResultCode(StatusCode.SUCCESS,result);
    }
}
