package com.yinmimoney.web.p2pnew.service.impl;

import com.yinmimoney.web.p2pnew.constant.Constant;
import com.yinmimoney.web.p2pnew.dao.CityPictureOpenLogMapper;
import com.yinmimoney.web.p2pnew.enums.EnumOrderDetailType;
import com.yinmimoney.web.p2pnew.enums.EnumOrderNoType;
import com.yinmimoney.web.p2pnew.enums.EnumOrderStatus;
import com.yinmimoney.web.p2pnew.enums.EnumTradeType;
import com.yinmimoney.web.p2pnew.pojo.City;
import com.yinmimoney.web.p2pnew.pojo.CityPictureOpenLog;
import com.yinmimoney.web.p2pnew.pojo.CommonOrder;
import com.yinmimoney.web.p2pnew.pojo.CommonOrderDetail;
import com.yinmimoney.web.p2pnew.service.ICity;
import com.yinmimoney.web.p2pnew.service.ICityPictureOpenLog;
import com.yinmimoney.web.p2pnew.service.ICommonOrder;
import com.yinmimoney.web.p2pnew.service.ICommonOrderDetail;
import com.yinmimoney.web.p2pnew.util.DateUtil;
import com.yinmimoney.web.p2pnew.util.OrderNoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

import java.math.BigDecimal;

@Service
public class CityPictureOpenLogImpl extends BaseServiceImpl<CityPictureOpenLog, CityPictureOpenLogMapper, Integer> implements ICityPictureOpenLog {
    @Autowired
    private CityPictureOpenLogMapper cityPictureOpenLogMapper;
    @Autowired
    private ICommonOrder commonOrderService;
    @Autowired
    private ICommonOrderDetail commonOrderDetailService;
    @Autowired
    private ICity cityService;

    protected CityPictureOpenLogMapper getDao() {
        return cityPictureOpenLogMapper;
    }

    @Override
    public void processOpenCityPicture(String cityCode, String txid, String userCode,String userName, BigDecimal money) {

        String open_cp = OrderNoUtils.makeRequestNo("open_cp");
        //保存城市开通城市相册记录
        CityPictureOpenLog cityPictureOpenLog = new CityPictureOpenLog();
        cityPictureOpenLog.setOrderNo(open_cp);
        cityPictureOpenLog.setTxid(txid);
        cityPictureOpenLog.setUserCode(userCode);
        cityPictureOpenLog.setCityCode(cityCode);
        City city = cityService.selectByCityCode(cityCode);
        cityPictureOpenLog.setCityName(city!=null?city.getCityName():null);
        insertSelective(cityPictureOpenLog);
        //保存订单记录
        CommonOrder commonOrder = new CommonOrder();
        commonOrder.setOrderNo(open_cp);
        commonOrder.setTxid(txid);
        commonOrder.setAmount(money);
        commonOrder.setCityCode(cityCode);
        commonOrder.setStatus(EnumOrderStatus.PRE_LOCK.getStatus());
        commonOrder.setOperateType(EnumTradeType.CITY_PICTURE_OPEN.getName());
        commonOrder.setOperatorCode(userCode);
        commonOrder.setOperatorName(userName);
        commonOrder.setAcceptorCode(Constant.PLAT_CODE);
        commonOrderService.insertSelective(commonOrder);
        //保存交易记录
        CommonOrderDetail commonOrderDetail = new CommonOrderDetail();
        commonOrderDetail.setOrderNo(open_cp);
        commonOrderDetail.setTxid(txid);
        commonOrderDetail.setAmount(money);
        commonOrderDetail.setUserCode(userCode);
        commonOrderDetail.setCityCode(cityCode);
        commonOrderDetail.setCityName(city!=null?city.getCityName():null);
        commonOrderDetail.setOperateType(EnumTradeType.CITY_PICTURE_OPEN.getName());
        commonOrderDetail.setType(EnumOrderDetailType.OUT.getName());
        commonOrderDetailService.insertSelective(commonOrderDetail);
        commonOrderDetail.setUserCode(Constant.PLAT_CODE);
        commonOrderDetail.setType(EnumOrderDetailType.IN.getName());
        commonOrderDetailService.insertSelective(commonOrderDetail);
    }
}