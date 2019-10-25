package com.yinmimoney.web.p2pnew.service.impl;

import cc.s2m.web.utils.webUtils.StaticProp;
import com.yinmimoney.web.p2pnew.constant.Constant;
import com.yinmimoney.web.p2pnew.core.ResultCode;
import com.yinmimoney.web.p2pnew.core.StatusCode;
import com.yinmimoney.web.p2pnew.dao.CityAdsPriceMapper;
import com.yinmimoney.web.p2pnew.dto.CityAdsPriceDetailDto;
import com.yinmimoney.web.p2pnew.dto.CityAdsPriceDto;
import com.yinmimoney.web.p2pnew.enums.EnumOrderDetailType;
import com.yinmimoney.web.p2pnew.enums.EnumOrderStatus;
import com.yinmimoney.web.p2pnew.enums.EnumTradeType;
import com.yinmimoney.web.p2pnew.pojo.*;
import com.yinmimoney.web.p2pnew.requestbody.CityAdsPriceRequestBody;
import com.yinmimoney.web.p2pnew.service.ICity;
import com.yinmimoney.web.p2pnew.service.ICityAdsPrice;
import com.yinmimoney.web.p2pnew.service.ICommonOrder;
import com.yinmimoney.web.p2pnew.service.ICommonOrderDetail;
import com.yinmimoney.web.p2pnew.util.DateUtil;
import com.yinmimoney.web.p2pnew.util.OrderNoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CityAdsPriceImpl extends BaseServiceImpl<CityAdsPrice, CityAdsPriceMapper, Integer> implements ICityAdsPrice {
    @Autowired
    private CityAdsPriceMapper cityAdsPriceMapper;

    @Autowired
    private ICity cityService;

    @Autowired
    private ICommonOrder commonOrderService;



    @Autowired
    private ICommonOrderDetail commonOrderDetailService;

    protected CityAdsPriceMapper getDao() {
        return cityAdsPriceMapper;
    }

    @Override
    public ResultCode processCityAdsPrice(CityAdsPriceRequestBody body) {

        CityAdsPrice bean = new CityAdsPrice();
        City city = cityService.selectByCityCode(body.getCityCode());
        String orderNo = OrderNoUtils.makeRequestNo("ap_");

        int time = body.getTime() == 0 ? 3 : body.getTime();
        bean.setAmount(body.getAmount());
        bean.setUnitPrice(body.getUnitPrice());
        bean.setUnit("d");
        bean.setTime(time);
        bean.setCityCode(body.getCityCode());
        bean.setCityName(city.getCityName());
        Date date = null;
        if("local".equals(StaticProp.sysConfig.get("environment"))){
            date = DateUtil.getNow();

        }else {
            date = DateUtil.convertToGMT();
        }
        bean.setCreateTime(date);
        bean.setExpireTime(DateUtil.rollDay(date,time));
        bean.setIsExpire(Boolean.FALSE);
        bean.setUserCode(body.getUserCode());
        bean.setOrderNo(orderNo);
        bean.setTxid(body.getTxid());

        insert(bean);

        CommonOrder order = new CommonOrder();
        order.setCityCode(body.getCityCode());
        order.setOperatorCode(body.getUserCode());
        order.setOperateType(EnumTradeType.CITY_ADS_PRICE.getName());
        order.setStatus(EnumOrderStatus.PRE_LOCK.getStatus());
        order.setOrderNo(orderNo);
        order.setTxid(body.getTxid());
        order.setAcceptorCode(Constant.PLAT_CODE);
        order.setAmount(body.getAmount());
        order.setCurrency("USD");

        commonOrderService.insert(order);


        CommonOrderDetail detailUser = new CommonOrderDetail();
        detailUser.setAmount(body.getAmount());
        detailUser.setOperateType(EnumTradeType.CITY_ADS_PRICE.getName());
        detailUser.setCityName(city == null ? "" : city.getCityName());
        detailUser.setCityCode(body.getCityCode());
        detailUser.setUserCode(body.getUserCode());
        detailUser.setCurrency("USD");
        detailUser.setTxid(body.getTxid());
        detailUser.setType(EnumOrderDetailType.OUT.getName());
        detailUser.setOrderNo(orderNo);

        commonOrderDetailService.insert(detailUser);

        CommonOrderDetail detailPlat = new CommonOrderDetail();
        detailPlat.setAmount(body.getAmount());
        detailPlat.setOperateType(EnumTradeType.CITY_ADS_PRICE.getName());
        detailPlat.setCityName(city == null ? "" : city.getCityName());
        detailPlat.setCityCode(body.getCityCode());
        detailPlat.setUserCode(Constant.PLAT_CODE);
        detailPlat.setCurrency("USD");
        detailPlat.setTxid(body.getTxid());
        detailPlat.setType(EnumOrderDetailType.IN.getName());
        detailPlat.setOrderNo(orderNo);

        commonOrderDetailService.insert(detailPlat);

        return new ResultCode(StatusCode.SUCCESS);
    }

    @Override
    public Map<String,Object> getCityAdsPriceList(Integer pageNumber, Integer pageSize) {
        /*CityAdsPrice condi = new CityAdsPrice();
        condi.setPageNumber(pageNumber);
        condi.setPageSize(pageSize);

        condi.and(Expressions.ge("create_time",DateUtil.convertToGMT()));
        condi.and(Expressions.le("expire_time",DateUtil.convertToGMT()));

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {

            condi.and(Expressions.ge("create_time",format.parse(format.format(DateUtil.convertToGMT()))));
            condi.and(Expressions.le("expire_time",format.parse(format.format(DateUtil.convertToGMT()))));
        } catch (Exception e) {}

        condi.setOrderBy("unit_price desc");

        Page<CityAdsPrice> page = this.getPage(condi, null);
        List<CityAdsPriceDto> dtos = new ArrayList<>();
        if(page !=null){
            List<CityAdsPrice> list = page.getResult();

            list.forEach(x->{
                CityAdsPriceDto dto = new CityAdsPriceDto();
                dto.setCityName(x.getCityName());
                dto.setCityCode(x.getCityCode());
                dto.setUnitPrice(x.getUnitPrice());
                dto.setCreateTime(x.getCreateTime());
                dto.setExpireTime(x.getExpireTime());
                dtos.add(dto);
            });
        }*/
        Integer beginIndex = ( pageNumber -1 ) * pageSize;
        int count = cityAdsPriceMapper.getCityAdsPriceListCount();

        if(count == 0){
            Map<String,Object> data = new HashMap<>();
            data.put("list",new ArrayList<>());
            data.put("totalCount", 0 );

            return data;
        }

        List<CityAdsPriceDto> dtos = cityAdsPriceMapper.getCityAdsPriceList(beginIndex,pageSize);

        Map<String,Object> data = new HashMap<>();
        data.put("list",dtos);
        data.put("totalCount", count);

        return data;
    }

    @Override
    public CityAdsPriceDetailDto getByCityCodeAndUserCode(String cityCode, String userCode) {
        CityAdsPriceDetailDto dto = cityAdsPriceMapper.getByCityCodeAndUserCode(cityCode,userCode);

        if(dto != null){
             Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if("local".equals(StaticProp.sysConfig.get("environment"))){
                date = DateUtil.getNow();
            }else {
                date = DateUtil.convertToGMT();
            }
            BigDecimal between = BigDecimal.valueOf(
                        Long.parseLong(DateUtil.getTimesBetween(dto.getExpireTime(),
                                date))
                ).divide(new BigDecimal(3600*24), 0, BigDecimal.ROUND_UP);

            dto.setDays(between.intValue());
        }
        return dto;
    }

    @Override
    public List<CityAdsPriceDto> getMyCityAdsPriceList(String userCode) {
        return cityAdsPriceMapper.getMyCityAdsPriceList(userCode);
    }

    @Override
    public ResultCode modifyCityAdsPrice(CityAdsPriceRequestBody body) {
        CityAdsPrice bean = new CityAdsPrice();
        City city = cityService.selectByCityCode(body.getCityCode());
        String orderNo = OrderNoUtils.makeRequestNo("apm_");


        CityAdsPrice cityAdsPrice = this.
                selectByCityCodeAndUserCode(body.getCityCode(),body.getUserCode());

        int time = body.getTime() == 0 ? 3 : body.getTime();
        bean.setAmount(body.getAmount());
        bean.setUnitPrice(body.getUnitPrice());
        bean.setUnit("d");
        bean.setTime(time);
        bean.setCityCode(body.getCityCode());
        bean.setCityName(city.getCityName());
        Date date = null;
        if("local".equals(StaticProp.sysConfig.get("environment"))){
            date = DateUtil.getNow();
        }else {
            date = DateUtil.convertToGMT();
        }
        bean.setCreateTime(date);
        if(cityAdsPrice != null) {
            bean.setExpireTime(cityAdsPrice.getExpireTime() == null ?
                    DateUtil.rollDay(date, time) : cityAdsPrice.getExpireTime());
        }else{
            bean.setExpireTime(DateUtil.rollDay(date, time));
        }
        bean.setIsExpire(Boolean.FALSE);
        bean.setUserCode(body.getUserCode());
        bean.setOrderNo(orderNo);
        bean.setTxid(body.getTxid());

        insert(bean);

        CommonOrder order = new CommonOrder();
        order.setCityCode(body.getCityCode());
        order.setOperatorCode(body.getUserCode());
        order.setOperateType(EnumTradeType.MODIFY_CITY_ADS_PRICE.getName());
        order.setStatus(EnumOrderStatus.PRE_LOCK.getStatus());
        order.setOrderNo(orderNo);
        order.setTxid(body.getTxid());
        order.setAcceptorCode(Constant.PLAT_CODE);
        order.setAmount(body.getAmount());
        order.setCurrency("USD");

        commonOrderService.insert(order);


        CommonOrderDetail detailUser = new CommonOrderDetail();
        detailUser.setAmount(body.getAmount());
        detailUser.setOperateType(EnumTradeType.MODIFY_CITY_ADS_PRICE.getName());
        detailUser.setCityName(city == null ? "" : city.getCityName());
        detailUser.setCityCode(body.getCityCode());
        detailUser.setUserCode(body.getUserCode());
        detailUser.setCurrency("USD");
        detailUser.setTxid(body.getTxid());
        detailUser.setType(EnumOrderDetailType.OUT.getName());
        detailUser.setOrderNo(orderNo);

        commonOrderDetailService.insert(detailUser);

        CommonOrderDetail detailPlat = new CommonOrderDetail();
        detailPlat.setAmount(body.getAmount());
        detailPlat.setOperateType(EnumTradeType.MODIFY_CITY_ADS_PRICE.getName());
        detailPlat.setCityName(city == null ? "" : city.getCityName());
        detailPlat.setCityCode(body.getCityCode());
        detailPlat.setUserCode(Constant.PLAT_CODE);
        detailPlat.setCurrency("USD");
        detailPlat.setTxid(body.getTxid());
        detailPlat.setType(EnumOrderDetailType.IN.getName());
        detailPlat.setOrderNo(orderNo);

        commonOrderDetailService.insert(detailPlat);

        return new ResultCode(StatusCode.SUCCESS);
    }

    private CityAdsPrice selectByCityCodeAndUserCode(String cityCode, String userCode) {
        CityAdsPrice condi = new CityAdsPrice();
        condi.setCityCode(cityCode);
        condi.setUserCode(userCode);

        return this.getByCondition(condi);

    }
}