package com.yinmimoney.web.p2pnew.service;

import com.alibaba.fastjson.JSONObject;
import com.yinmimoney.web.p2pnew.dto.NewCityPriceDto;
import com.yinmimoney.web.p2pnew.pojo.Order;
import cc.s2m.web.utils.webUtils.service.BaseService;
import com.yinmimoney.web.p2pnew.requestbody.CloseOrderRequestBody;
import com.yinmimoney.web.p2pnew.requestbody.PreOrderRequestBody;
import com.yinmimoney.web.p2pnew.responsebody.CityMaxTransactionPriceResponseBody;
import com.yinmimoney.web.p2pnew.responsebody.HistoryTransactionPriceResponseBody;
import com.yinmimoney.web.p2pnew.responsebody.NewTransactionPriceResponseBody;
import com.yinmimoney.web.p2pnew.responsebody.PreOrderResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface IOrder extends BaseService<Order, Integer> {

    List<HistoryTransactionPriceResponseBody> selectOrderByCityCode(String cityCode);

    List<NewTransactionPriceResponseBody> getCityNewTransactionPrice(Integer beginIndex,Integer pageSize);

    List<NewTransactionPriceResponseBody> getCityNewTransactionPriceOld();

    Order selectByOrderNo(String orderNo);

    PreOrderResponseBody processPreOrder(PreOrderRequestBody body, String userCode);

    void processCloseOrder(CloseOrderRequestBody body, String userCode);

    boolean processWebHook(JSONObject payment);

    void processCityDetail(Order order);

    void processOrderDetail(Order order);

    void processUnLock(String cityCode);

    Order selectByOrderNoForUpdate(String orderNo);

    List<NewCityPriceDto> getNewTransactionPriceCity(Integer pageNumber,Integer pageSize);

    List<NewCityPriceDto> getNewTransactionPriceCityOld();

    Order getTopTen();

    List<CityMaxTransactionPriceResponseBody> getCityMaxTransactionPrice(Integer beginIndex,Integer pageSize);

    List<CityMaxTransactionPriceResponseBody> getCityMaxTransactionPriceOld();

    Map<String,Object> getPreOrder(Integer pageSize, Integer pageNumber,HttpServletRequest request);

    void modifyPreOrder(String orderNo);
}