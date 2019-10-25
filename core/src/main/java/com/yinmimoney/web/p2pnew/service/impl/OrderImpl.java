package com.yinmimoney.web.p2pnew.service.impl;

import cc.s2m.util.Page;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;
import cc.s2m.web.utils.webUtils.vo.Expressions;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.yinmimoney.web.p2pnew.constant.Constant;
import com.yinmimoney.web.p2pnew.core.StatusCode;
import com.yinmimoney.web.p2pnew.dao.OrderMapper;
import com.yinmimoney.web.p2pnew.dto.CityZoomDto;
import com.yinmimoney.web.p2pnew.dto.NewCityPriceDto;
import com.yinmimoney.web.p2pnew.dto.PreOrderDto;
import com.yinmimoney.web.p2pnew.enums.*;
import com.yinmimoney.web.p2pnew.exception.BussinessException;
import com.yinmimoney.web.p2pnew.pojo.*;
import com.yinmimoney.web.p2pnew.requestbody.CloseOrderRequestBody;
import com.yinmimoney.web.p2pnew.requestbody.PreOrderRequestBody;
import com.yinmimoney.web.p2pnew.responsebody.CityMaxTransactionPriceResponseBody;
import com.yinmimoney.web.p2pnew.responsebody.HistoryTransactionPriceResponseBody;
import com.yinmimoney.web.p2pnew.responsebody.NewTransactionPriceResponseBody;
import com.yinmimoney.web.p2pnew.responsebody.PreOrderResponseBody;
import com.yinmimoney.web.p2pnew.service.*;
import com.yinmimoney.web.p2pnew.util.DateUtil;
import com.yinmimoney.web.p2pnew.util.OrderNoUtils;
import com.yinmimoney.web.p2pnew.util.SmsHandelUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;

@Service
public class OrderImpl extends BaseServiceImpl<Order, OrderMapper, Integer> implements IOrder {

    private static final Logger LOGGER = LogManager.getLogger(OrderImpl.class);

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SmsHandelUtils smsHandelUtils;

    @Autowired
    private ICityAdsPrice cityAdsPriceService;

    protected OrderMapper getDao() {
        return orderMapper;
    }

    @Autowired
    private IPlatform platformService;
    @Autowired
    private IUser userService;
    @Autowired
    private ICity cityService;
    @Autowired
    private ICityDetail cityDetailService;
    @Autowired
    private IOrderDetail orderDetailService;
    @Autowired
    private ICityDetailHistory cityDetailHistoryService;

    @Override
    public List<HistoryTransactionPriceResponseBody> selectOrderByCityCode(String cityCode) {
        Order condi = new Order();
        condi.setCityCode(cityCode);
        List listStatus = Lists.newArrayList(EnumOrderStatus.RECEIVED.getStatus()
                ,EnumOrderStatus.COMPLETED.getStatus());
        condi.and(Expressions.in("status",listStatus));
        condi.setOrderBy("create_time asc");
        List<Order> list = getList(condi);
        List<HistoryTransactionPriceResponseBody> listBody = Lists.newArrayList();
        if (list != null && list.size() > 0) {
            for (Order order : list) {
                HistoryTransactionPriceResponseBody body = new HistoryTransactionPriceResponseBody();
                body.setAmount(order.getAmount());
                body.setCurrency(order.getCurrency());
                body.setCreateTime(order.getCreateTime());
                listBody.add(body);
            }
        }
        return listBody;
    }

    @Override
    public List<NewTransactionPriceResponseBody> getCityNewTransactionPrice(Integer beginIndex,Integer pageSize) {

        Long now = DateUtil.getNowTime();
        List<NewCityPriceDto> list = this.getNewTransactionPriceCity( beginIndex, pageSize);

        List<NewTransactionPriceResponseBody> bodyList = Lists.newArrayList();
        if (list != null && list.size() > 0) {
            for (NewCityPriceDto order : list) {
                NewTransactionPriceResponseBody body = new NewTransactionPriceResponseBody();
                System.out.println(MessageFormat.format("cityCode={0}",order.getCityCode()));
                City city = cityService.selectByCityCode(order.getCityCode());
                body.setAmount(order.getAmount());
                body.setCityCode(order.getCityCode());
                body.setCityName(city.getCityName());
                body.setCurrency(order.getCurrency());
                Long orderTime = DateUtil.getTime(order.getCreateTime());
                Long time = now - orderTime - 8 * 60 * 60;
                if (0 < time && time < Constant.HOUR_TIME) {
                    body.setTimeStr(new StringBuilder(
                            String.valueOf(Math.round(time / Constant.MINUTE_TIME)))
                            .append(Constant.MIN).toString());
                } else if (Constant.HOUR_TIME <= time && time < Constant.DAY_TIME) {

                    body.setTimeStr(new StringBuilder(
                            String.valueOf(Math.round(time / Constant.HOUR_TIME)))
                            .append(Constant.HOUR).toString());

                } else if (Constant.DAY_TIME <= time ) {
                    body.setTimeStr(new StringBuilder(
                            String.valueOf(Math.round(time / Constant.DAY_TIME)))
                            .append(Constant.DAY).toString());

                } /*else if (Constant.MONTH_TIME <= time && time < Constant.YEAR_TIME) {
                    body.setTimeStr(new StringBuilder(
                            String.valueOf(Math.round(time / Constant.MONTH_TIME)))
                            .append(" months ago ").toString());

                } else if (Constant.YEAR_TIME <= time) {

                    body.setTimeStr(new StringBuilder(
                            String.valueOf(Math.round(time / Constant.YEAR_TIME)))
                            .append(" years ago ").toString());
                }*/
                bodyList.add(body);

            }
        }
        return bodyList;
    }


    @Override
    public List<NewTransactionPriceResponseBody> getCityNewTransactionPriceOld() {

        Long now = DateUtil.getNowTime();
        List<NewCityPriceDto> list = this.getNewTransactionPriceCityOld();

        List<NewTransactionPriceResponseBody> bodyList = Lists.newArrayList();
        if (list != null && list.size() > 0) {
            for (NewCityPriceDto order : list) {
                NewTransactionPriceResponseBody body = new NewTransactionPriceResponseBody();
                System.out.println(MessageFormat.format("cityCode={0}",order.getCityCode()));
                City city = cityService.selectByCityCode(order.getCityCode());
                body.setAmount(order.getAmount());
                body.setCityCode(order.getCityCode());
                body.setCityName(city.getCityName());
                body.setCurrency(order.getCurrency());
                Long orderTime = DateUtil.getTime(order.getCreateTime());
                Long time = now - orderTime - 8 * 60 * 60;
                if (0 < time && time < Constant.HOUR_TIME) {
                    body.setTimeStr(new StringBuilder(
                            String.valueOf(Math.round(time / Constant.MINUTE_TIME)))
                            .append(Constant.MIN).toString());
                } else if (Constant.HOUR_TIME <= time && time < Constant.DAY_TIME) {

                    body.setTimeStr(new StringBuilder(
                            String.valueOf(Math.round(time / Constant.HOUR_TIME)))
                            .append(Constant.HOUR).toString());

                } else if (Constant.DAY_TIME <= time ) {
                    body.setTimeStr(new StringBuilder(
                            String.valueOf(Math.round(time / Constant.DAY_TIME)))
                            .append(Constant.DAY).toString());

                } /*else if (Constant.MONTH_TIME <= time && time < Constant.YEAR_TIME) {
                    body.setTimeStr(new StringBuilder(
                            String.valueOf(Math.round(time / Constant.MONTH_TIME)))
                            .append(" months ago ").toString());

                } else if (Constant.YEAR_TIME <= time) {

                    body.setTimeStr(new StringBuilder(
                            String.valueOf(Math.round(time / Constant.YEAR_TIME)))
                            .append(" years ago ").toString());
                }*/
                bodyList.add(body);

            }
        }
        return bodyList;
    }

    @Override
    public Order selectByOrderNo(String orderNo) {
        Order condi = new Order();
        condi.setOrderNo(orderNo);
        return getByCondition(condi);
    }

    @Override
    public PreOrderResponseBody processPreOrder(PreOrderRequestBody body, String userCode) {
        //首先判断是否是同一个人买
        City city = cityService.selectByCityCode(body.getCityCode());
        if (city != null && city.getIsLock()) {
            throw new BussinessException(StatusCode.ERROR_ORDER_LOCK);
        }
        PreOrderResponseBody preOrderResponseBody = new PreOrderResponseBody();

        CityDetail detail = cityDetailService.selectByCityCode(body.getCityCode());

        User seller = null;
        if (detail != null) {
            seller = userService.selectByUserCode(detail.getUserCode());
            if(detail.getCitySellStatus() == EnumCitySellStatus.NOT_SELL.getStatus()){
                throw new BussinessException(StatusCode.ERROR_CTIY_NOT_SELL);
            }
        }
        Platform platform = platformService.getByPlatCode(Constant.PLAT_CODE);

        if (platform == null || StringUtils.isBlank(platform.getWalletAddress())) {
            throw new BussinessException(StatusCode.ERROR_PLAT_WALLETADDRESS_NOT_EXISTS);
        }
        String orderNo = OrderNoUtils.makeRequestNo("order_");

        preOrderResponseBody.setSellerWalletAddress(seller == null ? "" : seller.getUserCode());
        preOrderResponseBody.setPlatWalletAddress(platform.getWalletAddress());

        preOrderResponseBody.setCityName(city.getCityName());
        preOrderResponseBody.setAmount(detail == null ? platform.getAmount() : detail.getAmount());
        preOrderResponseBody.setOrderNo(orderNo);
        preOrderResponseBody.setCurrency(detail == null ? platform.getCurrency() : detail.getCurrency());

        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setStatus(EnumOrderStatus.PRE_LOCK.getStatus());
        order.setBuyerCode(userCode);
        order.setCityCode(body.getCityCode());
        order.setAmount(detail == null ? platform.getAmount() : detail.getAmount());
        order.setCurrency(detail == null ? platform.getCurrency() : detail.getCurrency());
        order.setSellerCode(detail == null ? platform.getPlatformCode() : detail.getUserCode());
        BigDecimal platAmount = platform.getAmount();
        if (detail == null) {
            order.setPlatAmount(platAmount);
            preOrderResponseBody.setPlatAmount(platAmount);

        } else {
            platAmount = detail.getAmount().multiply(platform.getFeeRate());
            BigDecimal sellerAmount = detail.getAmount().subtract(platAmount);
            order.setPlatAmount(platAmount);
            order.setSellerAmount(sellerAmount);
            preOrderResponseBody.setPlatAmount(platAmount);
            preOrderResponseBody.setSellerAmount(sellerAmount);
        }
        int insert = insert(order);

        if (insert != 1) {
            throw new BussinessException(StatusCode.ERROR);
        }
        processLock(city.getCityCode());

        return preOrderResponseBody;
    }

    @Override
    public void processCloseOrder(CloseOrderRequestBody body, String userCode) {
        Order order = selectByOrderNo(body.getOrderNo());
        if (order == null || order.getStatus() != EnumOrderStatus.PRE_LOCK.getStatus()) {
            return;
        }

        City city = cityService.selectByCityCode(order.getCityCode());
        if (city == null) {
            return;
        }
        Order condi = new Order();
        condi.setOrderNo(order.getOrderNo());

        Order update = new Order();
        update.setStatus(EnumOrderStatus.CLOSE.getStatus());
        //delete(condi);

        updateByCondition(update,condi);
        processUnLock(city.getCityCode());

    }

    @Override
    public synchronized boolean processWebHook(JSONObject payment) {
        LOGGER.info(payment);
        String orderNo = payment.getString("buttonId");

        String txid = payment.getString("txid");

        String normalizedTxid = payment.getString("normalizedTxid");

        String status = payment.getString("status");

        Order order = selectByOrderNo(orderNo);
        if (order.getStatus() == (EnumOrderStatus.valueOf(status).getStatus()) ||
                order.getStatus() == (EnumOrderStatus.COMPLETED.getStatus())) {
            LOGGER.info("订单原状态为order.status={},传入状态status={},不处理",order.getStatus(),status);
            return true;
        }


        if (status.equals(EnumOrderStatus.PENDING.getName())) {

        } else if (status.equals(EnumOrderStatus.RECEIVED.getName())  &&
                EnumOrderStatus.PRE_LOCK.getStatus() == order.getStatus()) {
            LOGGER.info("Received 修改订单");
            //修改订单状态 增加参数等
            Order update = new Order();
            update.setStatus(EnumOrderStatus.valueOf(status).getStatus());
            update.setButtonData("");
            update.setTxid(txid);
            update.setNormalizedTxid(normalizedTxid);

            Order condi = new Order();
            condi.setOrderNo(orderNo);
            condi.setStatus(order.getStatus());
            int row = updateByCondition(update, condi);
            if (row != 1) {
                throw new BussinessException(StatusCode.ERROR_501);
            }
            processCityDetail(order);

            //平台不是卖家多生成一条数据
            processOrderDetail(order);

            processUnLock(order.getCityCode());
            if (Constant.PLAT_CODE.equals(order.getSellerCode())) {
                modifyCityInitStatus( order.getCityCode());
            }

            City city = cityService.selectByCityCode(order.getCityCode());

            //发送通知
                LOGGER.info("购买发送通知");
                Order orderNew = selectByOrderNo(orderNo);
                HashMap<String, Object> sendData = new HashMap();
                User buyer = userService.selectByUserCode(orderNew.getBuyerCode());
                sendData.put("sentUser",buyer == null ? "" : buyer.getUserName());
                sendData.put("cityName", city == null ? "" : city.getCityName());
                sendData.put("money", orderNew.getSellerAmount()+"");
                sendData.put("cityCode", orderNew.getCityCode());
                try {
                    smsHandelUtils.send(EnumNoticeNid.SUCCESS_BUY, EnumNoticeType.TYPE_MESSAGE,
                            orderNew.getBuyerCode(), orderNew.getSellerCode(),
                            orderNew.getSellerCode(), sendData, null, null, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            //处理redis数据
           // processCache(order);

            //处理adsprice
            processAdsPrice(orderNew.getSellerCode(), city.getCityCode());

        } else if (status.equals(EnumOrderStatus.COMPLETED.getName()) &&
                EnumOrderStatus.RECEIVED.getStatus() == order.getStatus() ) {//成功
            LOGGER.info("COMPLETED 修改订单");

            Order update = new Order();
            update.setStatus(EnumOrderStatus.COMPLETED.getStatus());
            Order condi = new Order();
            condi.setOrderNo(orderNo);
            updateByCondition(update, condi);

        } else if (status.equals(EnumOrderStatus.FAILED.getName()) &&
                EnumOrderStatus.FAILED.getStatus() != order.getStatus() ) {//失败
            //修改订单状态 增加参数等
            Order update = new Order();
            update.setStatus(EnumOrderStatus.valueOf(status).getStatus());

            Order condi = new Order();
            condi.setOrderNo(orderNo);

            int row = updateByCondition(update, condi);

            if (row != 1) {
//                throw new RuntimeException("can not update User Account");
                throw new BussinessException(StatusCode.ERROR_502);

            }

            processOrderDetailFailed(order);
            processUnLock(order.getCityCode());

            if (Constant.PLAT_CODE.equals(order.getSellerCode())) {
                modifyRollBackCityInitStatus( order.getCityCode());
            }

        }else if((status.equals(EnumOrderStatus.RECEIVED.getName())
                || status.equals(EnumOrderStatus.COMPLETED.getName()) )
                && EnumOrderStatus.FAILED.getStatus() == order.getStatus()
                ){


        }

        return true;
    }

    private void processAdsPrice(String userCode, String cityCode) {
        CityAdsPrice condi = new CityAdsPrice();
        condi.setUserCode(userCode);
        condi.setCityCode(cityCode);
        CityAdsPrice    bean = new CityAdsPrice();
        bean.setIsExpire(Boolean.TRUE);
        cityAdsPriceService.updateByCondition(bean,condi);

    }

    private void processCache(Order order) {
        String cityCode = order.getCityCode();
        Map<String,CityZoomDto> ss = redisTemplate.opsForHash().entries("zoomcity");
        CityZoomDto cityZoomDto = ss.get(cityCode);
        if(cityZoomDto.getInitStatus() == 0) {
            cityZoomDto.setInitStatus(1);
        }

        ss.put(cityCode,cityZoomDto);
        redisTemplate.opsForHash().putAll("zoomcity",ss);

    }


    @Override
    public Order selectByOrderNoForUpdate(String orderNo) {
        Order order = selectByOrderNo(orderNo);
        return orderMapper.selectByOrderNoForUpdate(order.getId());
    }

    @Override
    public List<NewCityPriceDto> getNewTransactionPriceCity(Integer beginIndex,Integer pageSize) {
        return orderMapper.getNewTransactionPriceCity( beginIndex, pageSize);
    }

    @Override
    public List<NewCityPriceDto> getNewTransactionPriceCityOld() {
        return orderMapper.getNewTransactionPriceCityOld();
    }

    @Override
    public Order getTopTen() {


        return null;
    }

    @Override
    public List<CityMaxTransactionPriceResponseBody> getCityMaxTransactionPrice(Integer beginIndex,Integer pageSize) {

        List<CityMaxTransactionPriceResponseBody> list = orderMapper.getCityMaxTransactionPrice(beginIndex,pageSize);

        return list;
    }


    @Override
    public List<CityMaxTransactionPriceResponseBody> getCityMaxTransactionPriceOld() {

        List<CityMaxTransactionPriceResponseBody> list = orderMapper.getCityMaxTransactionPriceOld();

        return list;
    }

    @Override
    public Map<String,Object> getPreOrder(Integer pageSize, Integer pageNumber, HttpServletRequest request) {
        Order condi = new Order();
        condi.and(Expressions.in("status",Lists.newArrayList(EnumOrderStatus.PRE_LOCK.getStatus(),
                EnumOrderStatus.ADMIN_CLOSE.getStatus())));
        condi.and(Expressions.le("create_time", DateUtil.rollMinute(DateUtil.getNow(), -60)));
        condi.setPageSize(pageSize);
        condi.setPageNumber(pageNumber);

        Page<Order> page = this.getPage(condi,request.getParameterMap());
        List<PreOrderDto> dtos = new ArrayList<>();
        if(page != null ){
            List<Order> list = page.getResult();
            if(list != null && list.size() > 0){

                for(Order order : list) {
                    PreOrderDto dto = new PreOrderDto();
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
                    dtos.add(dto);
                }
            }
        }

        Map<String,Object> data = new HashMap<>();
        data.put("list",dtos);
        data.put("totalCount",page == null ? 0 : page.getTotalRow());
        return data;
    }

    @Override
    public void modifyPreOrder(String orderNo) {
        Order order = new Order();
        order.setStatus(EnumOrderStatus.ADMIN_CLOSE.getStatus());
        Order condi = new Order();
        condi.setOrderNo(orderNo);
        updateByCondition(order,condi);

    }

    public void processUnLock(String cityCode) {
        City updateCity = new City();
        updateCity.setIsLock(EnumCityLock.UN_LOCK.getStatus());

        City condiCity = new City();
        condiCity.setCityCode(cityCode);
        cityService.updateByCondition(updateCity, condiCity);
    }

    private void processLock(String cityCode) {
        City updateCity = new City();
        updateCity.setIsLock(EnumCityLock.LOCK.getStatus());

        City condiCity = new City();
        condiCity.setCityCode(cityCode);
        cityService.updateByCondition(updateCity, condiCity);
    }

    //处理citydetail
    public void processCityDetail(Order order) {
        CityDetail detail = cityDetailService.selectByCityCode(order.getCityCode());

        if (detail != null) {
            CityDetailHistory his = new CityDetailHistory();
            his.setHisId(detail.getId());
            his.setCityCode(detail.getCityCode());
            his.setHisUserCode(detail.getUserCode());
            his.setHisOrderNo(detail.getOrderNo());
            his.setHisAmount(detail.getAmount());
            his.setHisCitySellStatus(detail.getCitySellStatus());
            his.setHisMessage(detail.getMessage());
            his.setHisMessageStatus(detail.getMessageStatus());
            his.setHisUrl(detail.getUrl());
            his.setHisCurrency(detail.getCurrency());
            his.setCreateTime(detail.getCreateTime());
            his.setUpdateTime(detail.getUpdateTime());

            cityDetailHistoryService.insert(his);
            cityDetailService.delete(detail);
        }

        CityDetail insertBean = new CityDetail();
        insertBean.setUserCode(order.getBuyerCode());
        insertBean.setOrderNo(order.getOrderNo());
        insertBean.setCurrency(order.getCurrency());
        insertBean.setAmount(order.getAmount());
        insertBean.setCitySellStatus(EnumCitySellStatus.NOT_SELL.getStatus());
        insertBean.setCityCode(order.getCityCode());
        insertBean.setMessageStatus(EnumMessageIsShow.IS_SHOW.getStatus());

        cityDetailService.insert(insertBean);

    }

    public void modifyCityInitStatus(String cityCode) {
        City city = new City();
        city.setInitStatus(EnumCityInitStatus.USER_SELF.getStatus());
        City codi = new City();
        codi.setCityCode(cityCode);
        cityService.updateByCondition(city, codi);

    }

    public void modifyRollBackCityInitStatus(String cityCode) {
        City city = new City();
        city.setInitStatus(EnumCityInitStatus.PLATFORM.getStatus());
        City codi = new City();
        codi.setCityCode(cityCode);
        cityService.updateByCondition(city, codi);
    }

    public void processOrderDetail(Order order) {
        if (!Constant.PLAT_CODE.equals(order.getSellerCode())) {
            //出售方 金额是IN
            OrderDetail detail = new OrderDetail();
            detail.setUserCode(order.getSellerCode());
            detail.setAmount(order.getSellerAmount());
            detail.setCurrency(order.getCurrency());
            detail.setOrderNo(order.getOrderNo());
            detail.setType(EnumOrderDetailType.IN.getName());
            orderDetailService.insert(detail);

        }

        //平台方 金额是IN （1种全是出售，2是小费）
        OrderDetail detail = new OrderDetail();
        detail.setUserCode(Constant.PLAT_CODE);
        detail.setAmount(order.getPlatAmount());
        detail.setCurrency("USD");
        detail.setOrderNo(order.getOrderNo());
        detail.setType(EnumOrderDetailType.IN.getName());
        orderDetailService.insert(detail);

        //购买方 金额是OUT
        OrderDetail detailBuyer = new OrderDetail();
        detailBuyer.setUserCode(order.getBuyerCode());
        detailBuyer.setAmount(order.getAmount());
        detailBuyer.setCurrency(order.getCurrency());
        detailBuyer.setType(EnumOrderDetailType.OUT.getName());
        detailBuyer.setOrderNo(order.getOrderNo());
        orderDetailService.insert(detailBuyer);

    }

    /**
     * @return void
     * @Author wwy
     * @Description 失败回滚
     * @Date 11:13 2019/6/28
     * @Param [order]
     **/
    private void processOrderDetailFailed(Order order) {
        OrderDetail detail = new OrderDetail();
        detail.setOrderNo(order.getOrderNo());
        orderDetailService.delete(detail);
        //从历史数据中把原先数据给拿出来
        CityDetailHistory his = cityDetailHistoryService.selectByCityCode(order.getCityCode());

        if (his != null) {
            CityDetail insertBean = new CityDetail();
            insertBean.setUserCode(his.getHisUserCode());
            insertBean.setCurrency(his.getHisCurrency());
            insertBean.setAmount(his.getHisAmount());
            insertBean.setCitySellStatus(his.getHisCitySellStatus());
            insertBean.setCityCode(his.getCityCode());
            insertBean.setUrl(his.getHisUrl());
            insertBean.setMessage(his.getHisMessage());
            insertBean.setCreateTime(his.getCreateTime());
            insertBean.setUpdateTime(his.getUpdateTime());
            insertBean.setMessageStatus(his.getHisMessageStatus());
            cityDetailService.insert(insertBean);
        }

        CityDetail deleteBean = new CityDetail();
        deleteBean.setOrderNo(order.getOrderNo());
        cityDetailService.delete(deleteBean);

        cityDetailHistoryService.delete(his);

    }
}