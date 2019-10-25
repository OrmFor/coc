package com.yinmimoney.web.p2pnew.service.impl;

import cc.s2m.web.utils.webUtils.StaticProp;
import com.alibaba.fastjson.JSONObject;
import com.yinmimoney.web.p2pnew.constant.Constant;
import com.yinmimoney.web.p2pnew.core.StatusCode;
import com.yinmimoney.web.p2pnew.dao.CityDetailMapper;
import com.yinmimoney.web.p2pnew.dto.CityDto;
import com.yinmimoney.web.p2pnew.enums.EnumCitySellStatus;
import com.yinmimoney.web.p2pnew.enums.EnumOrderDetailType;
import com.yinmimoney.web.p2pnew.enums.EnumOrderStatus;
import com.yinmimoney.web.p2pnew.enums.EnumTradeType;
import com.yinmimoney.web.p2pnew.exception.BussinessException;
import com.yinmimoney.web.p2pnew.pojo.*;
import com.yinmimoney.web.p2pnew.requestbody.ModifyCityDetailRequestBody;
import com.yinmimoney.web.p2pnew.requestbody.ModifyUrlAndMessageRequestBody;
import com.yinmimoney.web.p2pnew.responsebody.MyCityDetailResponseBody;
import com.yinmimoney.web.p2pnew.service.*;
import com.yinmimoney.web.p2pnew.util.AESUtil;
import com.yinmimoney.web.p2pnew.util.BeanCovertUtil;
import com.yinmimoney.web.p2pnew.util.OrderNoUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CityDetailImpl extends BaseServiceImpl<CityDetail, CityDetailMapper, Integer> implements ICityDetail {
    @Autowired
    private CityDetailMapper cityDetailMapper;
    @Autowired
    private IUser userService;
    @Autowired
    private IOrder orderService;
    @Autowired
    private ICity cityService;
    @Autowired
    private ICommonOrder commonOrderService;
    @Autowired
    private ICommonOrderDetail commonOrderDetailService;


    @Autowired
    private IMessageFee messageFeeService;

    protected CityDetailMapper getDao() {
        return cityDetailMapper;
    }

    @Override
    public CityDetail selectByCityCode(String cityCode) {
        CityDetail condi = new CityDetail();
        condi.setCityCode(cityCode);
        return this.getByCondition(condi);
    }

    @Override
    public void modifyCityDetail(ModifyCityDetailRequestBody modifyCityDetailRequestBody) {
        User user = userService.selectByUserCode(modifyCityDetailRequestBody.getUserCode());
        /*Order condiOrder = new Order();
        condiOrder.setSellerCode(user.getUserCode());
        condiOrder.setCityCode(modifyCityDetailRequestBody.getCityCode());
        condiOrder.setStatus(EnumOrderStatus.PRE_LOCK.getStatus());
        Order order = orderService.getByCondition(condiOrder);
        if (order != null) {
            throw  new BussinessException(StatusCode.ERROR_CITY_NOT_EDIT);
        }*/

        CityDetail condi = new CityDetail();
        condi.setUserCode(modifyCityDetailRequestBody.getUserCode());
        condi.setCityCode(modifyCityDetailRequestBody.getCityCode());
        CityDetail update = new CityDetail();
        update.setMessage(modifyCityDetailRequestBody.getMessage());
        update.setAmount(modifyCityDetailRequestBody.getAmount());
        update.setCurrency(modifyCityDetailRequestBody.getCurrency());
        update.setCitySellStatus(modifyCityDetailRequestBody.getCitySellStatus());
        update.setUrl(modifyCityDetailRequestBody.getUrl());

        updateByCondition(update, condi);
    }

    @Override
    public MyCityDetailResponseBody getMyCityDetail(String userCode, String cityCode) {
        CityDetail condi = new CityDetail();
        condi.setCityCode(cityCode);
        condi.setUserCode(userCode);
        CityDetail cityDetail = getByCondition(condi);

        City city = cityService.selectByCityCode(cityCode);

        MyCityDetailResponseBody body = BeanCovertUtil.beanCovert(cityDetail,
                MyCityDetailResponseBody.class);
        body.setCityName(city == null ? "" : city.getCityName());
        return body;
    }

    @Override
    public void modifyMesaageAndUrl(ModifyUrlAndMessageRequestBody body) {

        CityDetail condi = new CityDetail();
        condi.setUserCode(body.getUserCode());
        condi.setCityCode(body.getCityCode());
        CityDetail update = new CityDetail();
        update.setMessage(body.getMessage());
        update.setUrl(body.getUrl());
        update.setTxId(body.getTxid());
        updateByCondition(update, condi);

        try {
            City city = cityService.selectByCityCode(body.getCityCode());
            String orderNo = OrderNoUtils.makeRequestNo("ads_");

            CommonOrder order = new CommonOrder();
            order.setCityCode(body.getCityCode());
            order.setOperatorCode(body.getUserCode());
            order.setOperateType(EnumTradeType.ADS.getName());
            order.setStatus(EnumOrderStatus.PRE_LOCK.getStatus());
            order.setOrderNo(orderNo);
            order.setTxid(body.getTxid());
            order.setAcceptorCode(Constant.PLAT_CODE);
            order.setAmount(new BigDecimal(0.02));
            order.setCurrency("USD");

            commonOrderService.insert(order);

           /* CommonOrderDetail detailUser = new CommonOrderDetail();
            detailUser.setAmount(new BigDecimal(0.02));
            detailUser.setOperateType(EnumTradeType.ADS.getName());
            detailUser.setCityName(city == null ? "" : city.getCityName());
            detailUser.setCityCode(body.getCityCode());
            detailUser.setUserCode(body.getUserCode());
            detailUser.setCurrency("USD");
            detailUser.setTxid(body.getTxid());
            detailUser.setType(EnumOrderDetailType.OUT.getName());
            detailUser.setOrderNo(orderNo);

            commonOrderDetailService.insert(detailUser);

            CommonOrderDetail detailPlat = new CommonOrderDetail();
            detailPlat.setAmount(new BigDecimal(0.02));
            detailPlat.setOperateType(EnumTradeType.ADS.getName());
            detailPlat.setCityName(city == null ? "" : city.getCityName());
            detailPlat.setCityCode(body.getCityCode());
            detailPlat.setUserCode(Constant.PLAT_CODE);
            detailPlat.setCurrency("USD");
            detailPlat.setTxid(body.getTxid());
            detailPlat.setType(EnumOrderDetailType.IN.getName());
            detailPlat.setOrderNo(orderNo);

            commonOrderDetailService.insert(detailPlat);*/


          /*  MessageFee bean = new MessageFee();

            bean.setTxid(body.getTxid());
            bean.setCityCode(body.getCityCode());
            bean.setCityName(city == null ? "" : city.getCityName());
            bean.setAmount(new BigDecimal(0.02));
            bean.setCurrency("USD");
            bean.setUserCode(Constant.PLAT_CODE);
            bean.setType(EnumOrderDetailType.IN.getName());
            messageFeeService.insert(bean);


            MessageFee user = new MessageFee();

            user.setTxid(body.getTxid());
            user.setCityCode(body.getCityCode());
            user.setCityName(city == null ? "" : city.getCityName());
            user.setAmount(new BigDecimal(0.02));
            user.setCurrency("USD");
            user.setUserCode(body.getUserCode());
            user.setType(EnumOrderDetailType.OUT.getName());
            messageFeeService.insert(user);*/

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    @Override
    public void processMessage(JSONObject payment) {
        String buttonData = payment.getString("buttonData");
        JSONObject buttonJson = JSONObject.parseObject(buttonData);

        String encodeUserCode = buttonJson.getString("encodeUserCode");

        String userCode = AESUtil.decrypt(encodeUserCode, StaticProp.sysConfig.get("url.request.validate"));
        String cityCode = buttonJson.getString("cityCode");
        String message = buttonJson.getString("message");
        String url = buttonJson.getString("url");

        String txid = payment.getString("txid");

        CityDetail condi = new CityDetail();
        condi.setUserCode(userCode);
        condi.setCityCode(cityCode);
        CityDetail update = new CityDetail();
        update.setMessage(message);
        update.setUrl(url);
        update.setTxId(txid);
        updateByCondition(update, condi);
    }

    @Override
    public List<CityDto> getMyCityList(String userCode) {
        return cityDetailMapper.getMyCityList(userCode);
    }
}