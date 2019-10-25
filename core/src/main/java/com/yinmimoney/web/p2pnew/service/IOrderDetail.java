package com.yinmimoney.web.p2pnew.service;

import com.yinmimoney.web.p2pnew.dto.DataLookInfo;
import com.yinmimoney.web.p2pnew.dto.OrderAmountDto;
import com.yinmimoney.web.p2pnew.dto.PlatformIncomeInfo;
import com.yinmimoney.web.p2pnew.dto.SumMoneyDto;
import com.yinmimoney.web.p2pnew.pojo.OrderDetail;
import cc.s2m.web.utils.webUtils.service.BaseService;
import com.yinmimoney.web.p2pnew.responsebody.MyOrderDetailsResponseBody;

import java.util.List;
import java.util.Map;

public interface IOrderDetail extends BaseService<OrderDetail, Integer> {
    SumMoneyDto getSumAmount(String userCode);


    Map<String,Object> getMyOrderDetails(String userCode, Integer pageNumber, Map<String, String[]> parameterMap);

    /**
     *@description 统计-数据总览
     *@param
     *@return
     */
    List<DataLookInfo> getDataLookInfos();

    /**
     *@description 统计-平台收益统计
     *@param
     *@return
     */
    List<PlatformIncomeInfo> getPlatformIncomeInfos(Map<String,Object> params);


    OrderAmountDto getOrderAmount(String userCode);
}