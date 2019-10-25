package com.yinmimoney.web.p2pnew.service.impl;

import cc.s2m.util.Page;
import com.google.common.collect.Lists;
import com.yinmimoney.web.p2pnew.constant.Constant;
import com.yinmimoney.web.p2pnew.dao.OrderDetailMapper;
import com.yinmimoney.web.p2pnew.dto.DataLookInfo;
import com.yinmimoney.web.p2pnew.dto.OrderAmountDto;
import com.yinmimoney.web.p2pnew.dto.PlatformIncomeInfo;
import com.yinmimoney.web.p2pnew.dto.SumMoneyDto;
import com.yinmimoney.web.p2pnew.pojo.City;
import com.yinmimoney.web.p2pnew.pojo.Order;
import com.yinmimoney.web.p2pnew.pojo.OrderDetail;
import com.yinmimoney.web.p2pnew.responsebody.MyOrderDetailsResponseBody;
import com.yinmimoney.web.p2pnew.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderDetailImpl extends BaseServiceImpl<OrderDetail, OrderDetailMapper, Integer> implements IOrderDetail {
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private ICity cityService;

    @Autowired
    private IOrder orderService;

    @Autowired
    private IMessageFee messageFeeService;

    @Autowired
    private ICommonOrderDetail commonOrderDetailService;

    protected OrderDetailMapper getDao() {
        return orderDetailMapper;
    }

    @Override
    public SumMoneyDto getSumAmount(String userCode) {
        BigDecimal otherOut =   commonOrderDetailService.getMoneyOut(userCode);
        BigDecimal otherIn =   commonOrderDetailService.getMoneyIn(userCode);

        SumMoneyDto sumAmountDto = orderDetailMapper.getSumAmount(userCode);

        if(sumAmountDto == null){
            sumAmountDto = new SumMoneyDto();
        }
        BigDecimal sumOut =  sumAmountDto == null ? BigDecimal.ZERO :sumAmountDto.getMoneySumOut();
        if(sumOut == null){
            sumOut = BigDecimal.ZERO;
        }
        BigDecimal sumIn =  sumAmountDto == null ? BigDecimal.ZERO :sumAmountDto.getMoneySumIn();
        if(sumIn == null){
            sumIn = BigDecimal.ZERO;
        }

        sumAmountDto.setMoneySumOut(sumOut.add(otherOut == null ? BigDecimal.ZERO : otherOut));
        sumAmountDto.setMoneySumIn(sumIn.add(otherIn == null ? BigDecimal.ZERO : otherIn));

        return sumAmountDto;

    }

    @Override
    public Map<String, Object> getMyOrderDetails(String userCode, Integer pageNumber,
                                                 Map<String, String[]> parameterMap) {
        OrderDetail condi = new OrderDetail();
        condi.setUserCode(userCode);
        condi.setPageNumber(pageNumber);
        condi.setPageSize(10);
        Page<OrderDetail> page = getPage(condi, parameterMap);
        List<MyOrderDetailsResponseBody> bodyList = Lists.newArrayList();
        int total = 0;
        if (page != null) {
            List<OrderDetail> orderDetails = page.getResult();
            if (orderDetails != null && orderDetails.size() > 0) {
                for (OrderDetail orderDetail : orderDetails) {
                    MyOrderDetailsResponseBody body = new MyOrderDetailsResponseBody();
                    Order order = orderService.selectByOrderNo(orderDetail.getOrderNo());
                    City city = new City();
                    if (order != null) {
                        city = cityService.selectByCityCode(order.getCityCode());
                        body.setTxId(StringUtils.isNotBlank(order.getTxid()) ? order.getTxid() : "");
                    }
                    body.setCreateTime(orderDetail.getCreateTime());
                    body.setType(orderDetail.getType());
                    body.setAmount(orderDetail.getAmount());
                    body.setCurrency(orderDetail.getCurrency());
                    body.setCityName(StringUtils.isNotBlank(city.getCityName())
                            ? city.getCityName() : "");
                    body.setCityCode(StringUtils.isNotBlank(city.getCityCode())
                            ? city.getCityCode() : "");


                    bodyList.add(body);
                }
            }
            total = page.getTotalRow();
        }

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("list", bodyList);
        data.put("total", total);
        return data;
    }

    @Override
    public List<DataLookInfo> getDataLookInfos() {
        return orderDetailMapper.getDataLookInfos(Constant.PLAT_CODE);
    }

    @Override
    public List<PlatformIncomeInfo> getPlatformIncomeInfos(Map<String, Object> params) {
        params.put("platformCode", Constant.PLAT_CODE);
        return orderDetailMapper.getPlatformIncomeInfos(params);
    }

    @Override
    public OrderAmountDto getOrderAmount(String userCode) {
        return orderDetailMapper.getOrderAmount(userCode);
    }

}