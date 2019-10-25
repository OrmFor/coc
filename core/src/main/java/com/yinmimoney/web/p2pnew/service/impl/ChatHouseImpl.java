package com.yinmimoney.web.p2pnew.service.impl;

import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;
import com.alibaba.fastjson.JSONObject;
import com.yinmimoney.web.p2pnew.constant.Constant;
import com.yinmimoney.web.p2pnew.core.ResultCode;
import com.yinmimoney.web.p2pnew.core.StatusCode;
import com.yinmimoney.web.p2pnew.dao.ChatHouseMapper;
import com.yinmimoney.web.p2pnew.enums.EnumExpenseType;
import com.yinmimoney.web.p2pnew.enums.EnumOrderStatus;
import com.yinmimoney.web.p2pnew.enums.EnumTradeType;
import com.yinmimoney.web.p2pnew.pojo.*;
import com.yinmimoney.web.p2pnew.service.*;
import com.yinmimoney.web.p2pnew.util.OrderNoUtils;
import com.yinmimoney.web.p2pnew.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatHouseImpl extends BaseServiceImpl<ChatHouse, ChatHouseMapper, Integer> implements IChatHouse {
    @Autowired
    private ChatHouseMapper chatHouseMapper;

    @Autowired
    private ICityDetail cityDetailService;

    @Autowired
    private ICommonOrder commonOrderService;

    @Autowired
    private ICommonOrderDetail commonOrderDetailService;

    @Autowired
    private ICity cityService;

    protected ChatHouseMapper getDao() {
        return chatHouseMapper;
    }

    @Override
    public ChatHouse selectByCityCode(String cityCode) {
        if(StringUtil.isBlank(cityCode)){
            return null;
        }
        ChatHouse cond = new ChatHouse();
        cond.setCityCode(cityCode);
        return getByCondition(cond);
    }

    @Override
    public ResultCode processSetHouseName(JSONObject params, String userCode) {
        String cityCode = params.getString("cityCode");
        CityDetail cityDetail = cityDetailService.selectByCityCode(cityCode);
        if(cityDetail==null){
            return new ResultCode(StatusCode.ERROR_PARAM);
        }
        if(!userCode.equals(cityDetail.getUserCode())){
            return new ResultCode(StatusCode.ERROR_OWN_CITY);
        }
        //保存课堂名称
        ChatHouse cond = new ChatHouse();
        cond.setCityCode(cityCode);
        ChatHouse byCondition = getByCondition(cond);
        if(byCondition==null){
            cond.setHouseName(params.getString("houseName"));
            insertSelective(cond);
        }else{
            byCondition.setBeforeName(byCondition.getHouseName());
            byCondition.setHouseName(params.getString("houseName"));
            updateByPrimaryKey(byCondition);
        }
        //保存订单信息
        String shm = OrderNoUtils.makeRequestNo("shm");
        CommonOrder commonOrder = new CommonOrder();
        commonOrder.setOrderNo(shm);
        commonOrder.setTxid(params.getString("txId"));
        commonOrder.setOperatorCode(userCode);
        commonOrder.setAcceptorCode(Constant.PLAT_CODE);
        commonOrder.setCityCode(cityCode);
        commonOrder.setStatus(EnumOrderStatus.PRE_LOCK.getStatus());
        commonOrder.setAmount(params.getBigDecimal("money"));
        commonOrder.setOperateType(EnumTradeType.SET_HOUSE_NAME.getName());
        commonOrderService.insertSelective(commonOrder);
        //保存账单信息
        //出帐
        CommonOrderDetail commonOrderDetail = new CommonOrderDetail();
        commonOrderDetail.setUserCode(userCode);
        commonOrderDetail.setAmount(params.getBigDecimal("money"));
        commonOrderDetail.setTxid(params.getString("txId"));
        commonOrderDetail.setType(EnumExpenseType.EXPENSE_OUT.getName());
        commonOrderDetail.setOrderNo(shm);
        commonOrderDetail.setCityCode(cityCode);
        commonOrderDetail.setOperateType(EnumTradeType.SET_HOUSE_NAME.getName());
        City city = cityService.selectByCityCode(cityCode);
        commonOrderDetail.setCityName(city!=null?city.getCityName():null);
        commonOrderDetailService.insertSelective(commonOrderDetail);
        //入账
        commonOrderDetail.setType(EnumExpenseType.EXPENSE_IN.getName());
        commonOrderDetail.setUserCode(Constant.PLAT_CODE);
        commonOrderDetailService.insertSelective(commonOrderDetail);
        return new ResultCode(StatusCode.SUCCESS);
    }
}