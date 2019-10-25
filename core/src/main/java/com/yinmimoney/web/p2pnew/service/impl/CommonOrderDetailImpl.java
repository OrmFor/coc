package com.yinmimoney.web.p2pnew.service.impl;

import cc.s2m.util.Page;
import cc.s2m.util.PageUtil;
import cc.s2m.web.utils.webUtils.StaticProp;
import cc.s2m.web.utils.webUtils.vo.Expressions;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import com.yinmimoney.web.p2pnew.constant.Constant;
import com.yinmimoney.web.p2pnew.core.ResultCode;
import com.yinmimoney.web.p2pnew.core.StatusCode;
import com.yinmimoney.web.p2pnew.dao.CommonOrderDetailMapper;
import com.yinmimoney.web.p2pnew.dto.*;
import com.yinmimoney.web.p2pnew.enums.*;
import com.yinmimoney.web.p2pnew.exception.BussinessException;
import com.yinmimoney.web.p2pnew.pojo.*;
import com.yinmimoney.web.p2pnew.requestbody.GetAdsPictureRequestBody;
import com.yinmimoney.web.p2pnew.responsebody.GetAdsPictureResponseBody;
import com.yinmimoney.web.p2pnew.service.*;
import com.yinmimoney.web.p2pnew.util.DateUtil;
import com.yinmimoney.web.p2pnew.util.OrderNoUtils;
import com.yinmimoney.web.p2pnew.util.SmsHandelUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

import java.math.BigDecimal;
import java.util.*;

@Service
public class CommonOrderDetailImpl extends BaseServiceImpl<CommonOrderDetail, CommonOrderDetailMapper, Integer> implements ICommonOrderDetail {
    private static final Logger LOGGER = LogManager.getLogger(CommonOrderDetailImpl.class);


    @Autowired
    private CommonOrderDetailMapper commonOrderDetailMapper;
    @Autowired
    private ICity cityService;
    @Autowired
    private ICommonOrder commonOrderService;
    @Autowired
    private IAdsPicture adsPictureService;
    @Autowired
    private ICityDetail cityDetailService;
    @Autowired
    private ILikeLog likeLogService;
    @Autowired
    private IPlatform platformService;
    @Autowired
    private IUser userService;

    @Autowired
    private ICityPicture cityPictureService;

    @Autowired
    private IOrderDetail orderDetailService;

    @Autowired
    private SmsHandelUtils smsHandelUtils;

    protected CommonOrderDetailMapper getDao() {
        return commonOrderDetailMapper;
    }

    private String env = StaticProp.sysConfig.get("environment");

    @Override
    public List<HotCity> getHotCity(Integer beginIndex, Integer pageSize) {
        String startTime = DateUtil.rollDayFormat(DateUtil.convertToGMT(), -6, "yyyy-MM-dd");
        String endTime = DateUtil.dateStr(DateUtil.convertToGMT(), "yyyy-MM-dd");
        //String dateFormat="%Y-%m-%d";
        List<HotCity> hotCityList = commonOrderDetailMapper.getHotCity(beginIndex, pageSize, startTime, endTime);
        return hotCityList;
    }

    @Override
    public List<PlatformIncomeInfo> getPlatformIncomeInfoOthers(Map<String, Object> cond) {
        return commonOrderDetailMapper.getPlatformIncomeInfoOthers(cond);
    }


    @Override
    public BigDecimal getMoneyOut(String userCode) {
        return commonOrderDetailMapper.getMoneyOut(userCode);
    }


    @Override
    public void processCallBackData(String type, JSONObject json) {
        String txid = json.getString("txid");
        String status = json.getString("status");
        CommonOrder cond = new CommonOrder();
        cond.setTxid(txid);
        cond.setStatus(EnumOrderStatus.PRE_LOCK.getStatus());
        List<CommonOrder> list = commonOrderService.getList(cond);
        //如果订单收到
        if (EnumOrderStatus.RECEIVED.getName().equals(status)) {
            list.forEach(x -> {
                //保存付费订单
                x.setStatus(EnumOrderStatus.RECEIVED.getStatus());
                commonOrderService.updateByPrimaryKey(x);
            });
        }
        //如果订单完成
        if (EnumOrderStatus.COMPLETED.getName().equals(status)) {
            cond.setStatus(EnumOrderStatus.RECEIVED.getStatus());
            List<CommonOrder> list2 = commonOrderService.getList(cond);
            list2.forEach(x -> {
                x.setStatus(EnumOrderStatus.COMPLETED.getStatus());
                commonOrderService.updateByPrimaryKey(x);
            });

            list.forEach(x->{
                x.setStatus(EnumOrderStatus.COMPLETED.getStatus());
                commonOrderService.updateByPrimaryKey(x);
            });

            //订单完成了,还有未处理订单
            if (list.size() > 0) {
                CommonOrderDetail commonOrderCond = new CommonOrderDetail();
                commonOrderCond.setTxid(txid);
                List<CommonOrderDetail> detailList = getList(commonOrderCond);
                if (detailList.size() < 1) {
                    if (!EnumTradeType.LIKE.getName().equals(type)) {
                        list.forEach(x -> {
                            //保存付费订单
                            x.setStatus(EnumOrderStatus.COMPLETED.getStatus());
                            commonOrderService.updateByPrimaryKey(x);
                            CommonOrderDetail commonOrderDetail = new CommonOrderDetail();
                            commonOrderDetail.setOrderNo(x.getOrderNo());
                            commonOrderDetail.setAmount(x.getAmount());
                            commonOrderDetail.setUserCode(x.getOperatorCode());
                            commonOrderDetail.setType(EnumExpenseType.EXPENSE_OUT.getName());
                            commonOrderDetail.setCreateTime(x.getCreateTime());
                            commonOrderDetail.setOperateType(type);
                            commonOrderDetail.setCityCode(x.getCityCode());
                            commonOrderDetail.setTxid(x.getTxid());
                            City city = cityService.selectByCityCode(x.getCityCode());
                            if (city != null) {
                                commonOrderDetail.setCityName(city.getCityName());
                            }
                            insertSelective(commonOrderDetail);

                            //保存收费订单
                            commonOrderDetail.setUserCode(x.getAcceptorCode());
                            commonOrderDetail.setType(EnumExpenseType.EXPENSE_IN.getName());
                            insertSelective(commonOrderDetail);
                        });
                    } else {
                        CommonOrderDetail commonOrderDetail = new CommonOrderDetail();
                        BigDecimal totalAmount = BigDecimal.ZERO;
                        for (CommonOrder x : list) {
                            totalAmount = totalAmount.add(x.getAmount());
                            //保存收费订单
                            x.setStatus(EnumOrderStatus.COMPLETED.getStatus());
                            commonOrderService.updateByPrimaryKey(x);
                            commonOrderDetail.setOrderNo(x.getOrderNo());
                            commonOrderDetail.setAmount(x.getAmount());
                            commonOrderDetail.setCreateTime(x.getCreateTime());
                            commonOrderDetail.setOperateType(type);
                            commonOrderDetail.setCityCode(x.getCityCode());
                            commonOrderDetail.setTxid(x.getTxid());
                            City city = cityService.selectByCityCode(x.getCityCode());
                            if (city != null) {
                                commonOrderDetail.setCityName(city.getCityName());
                            }
                            commonOrderDetail.setUserCode(x.getAcceptorCode());
                            commonOrderDetail.setType(EnumExpenseType.EXPENSE_IN.getName());
                            insertSelective(commonOrderDetail);
                        }
                        if (list.size() > 0) {
                            commonOrderDetail.setUserCode(list.get(0).getOperatorCode());
                            commonOrderDetail.setAmount(totalAmount);
                            commonOrderDetail.setType(EnumExpenseType.EXPENSE_OUT.getName());
                            insertSelective(commonOrderDetail);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void processAds(JSONObject payment) {
        String txid = payment.getString("txid");
        String status = payment.getString("status");
        CommonOrder cond = new CommonOrder();
        cond.setTxid(txid);
        cond.and(Expressions.in("status", Lists.newArrayList(EnumOrderStatus.RECEIVED.getStatus(),
                EnumOrderStatus.PRE_LOCK.getStatus())));
        CommonOrder bean = commonOrderService.getByCondition(cond);
        if (bean == null) {
            return;
        }
        if (EnumOrderStatus.RECEIVED.getName().equals(status)) {

            City city = cityService.selectByCityCode(bean.getCityCode());

            CommonOrder update = new CommonOrder();
            update.setStatus(EnumOrderStatus.RECEIVED.getStatus());
            CommonOrder condi = new CommonOrder();
            condi.setTxid(bean.getTxid());
            commonOrderService.updateByCondition(update, condi);


            CommonOrderDetail detailUser = new CommonOrderDetail();
            detailUser.setAmount(new BigDecimal(0.02));
            detailUser.setOperateType(EnumTradeType.ADS.getName());
            detailUser.setCityName(city == null ? "" : city.getCityName());
            detailUser.setCityCode(bean.getCityCode());
            detailUser.setUserCode(bean.getOperatorCode());
            detailUser.setCurrency("USD");
            detailUser.setTxid(bean.getTxid());
            detailUser.setType(EnumOrderDetailType.OUT.getName());
            detailUser.setOrderNo(bean.getOrderNo());

            insert(detailUser);

            CommonOrderDetail detailPlat = new CommonOrderDetail();
            detailPlat.setAmount(new BigDecimal(0.02));
            detailPlat.setOperateType(EnumTradeType.ADS.getName());
            detailPlat.setCityName(city == null ? "" : city.getCityName());
            detailPlat.setCityCode(bean.getCityCode());
            detailPlat.setUserCode(Constant.PLAT_CODE);
            detailPlat.setCurrency("USD");
            detailPlat.setTxid(bean.getTxid());
            detailPlat.setType(EnumOrderDetailType.IN.getName());
            detailPlat.setOrderNo(bean.getOrderNo());

            insert(detailPlat);
        } else if (EnumOrderStatus.COMPLETED.getName().equals(status)) {
            CommonOrder update = new CommonOrder();
            update.setStatus(EnumOrderStatus.COMPLETED.getStatus());
            CommonOrder condi = new CommonOrder();
            condi.setTxid(bean.getTxid());
            commonOrderService.updateByCondition(update, condi);
        }
    }

    @Override
    public BigDecimal getMoneyIn(String userCode) {
        return commonOrderDetailMapper.getMoneyIn(userCode);
    }

    @Override
    public List<DataLookInfo> getDataOtherLookInfos() {
        return commonOrderDetailMapper.getDataLookInfos(Constant.PLAT_CODE);
    }

    @Override
    public Page<CommonOrderDetail> getCityBillPage(Map<String, Object> map) {
        List<CommonOrderDetail> cityBillList = commonOrderDetailMapper.getCityBillList(map);
        Integer cityBillCount = commonOrderDetailMapper.getCityBillCount(map);
        Page<CommonOrderDetail> commonOrderDetailPage = new Page<>();
        Page<CommonOrderDetail> page = PageUtil.createPage(commonOrderDetailPage, cityBillCount);
        page.setResult(cityBillList);
        return page;
    }

    @Override
    public Page<CommonOrderDetail> getCommunicateBillPage(Map<String, Object> map) {
        List<CommonOrderDetail> communicateBillList = commonOrderDetailMapper.getCommunicateBillList(map);
        Integer communicateBillCount = commonOrderDetailMapper.getCommunicateBillCount(map);
        Page<CommonOrderDetail> commonOrderDetailPage = new Page<>();
        Page<CommonOrderDetail> page = PageUtil.createPage(commonOrderDetailPage, communicateBillCount);
        page.setResult(communicateBillList);
        return page;
    }

    @Override
    public Integer getCityHotLastedCount() {
        String startTime = DateUtil.rollDayFormat(DateUtil.convertToGMT(), -6, "yyyy-MM-dd");
        String endTime = DateUtil.dateStr(DateUtil.convertToGMT(), "yyyy-MM-dd");
        //String dateFormat="%Y-%m-%d";
        Integer count = commonOrderDetailMapper.getCityHotLastedCount();
        return count;
    }

    @Override
    public Integer getHotCityCount() {
        String startTime = DateUtil.rollDayFormat(DateUtil.convertToGMT(), -6, "yyyy-MM-dd");
        String endTime = DateUtil.dateStr(DateUtil.convertToGMT(), "yyyy-MM-dd");
        //String dateFormat="%Y-%m-%d";
        Integer count = commonOrderDetailMapper.getHotCityCount(startTime, endTime);
        return count;
    }

    @Override
    public synchronized void processAdsPicture(JSONObject json, String userCode) {

        String cityCode = json.getString("cityCode");
        String txid = json.getString("txid");
        // String normalizedTxid = json.getString("normalizedTxid");
        String status = json.getString("status");
        BigDecimal amount = json.getBigDecimal("amount");
        String url = json.getString("adsUrl");
        Integer orderNum = json.getInteger("orderNum");

        String orderNo = OrderNoUtils.makeRequestNo("crads_");

        CommonOrder commonOrder = new CommonOrder();
        commonOrder.setOrderNo(orderNo);
        commonOrder.setTxid(txid);
        commonOrder.setAmount(amount);
        commonOrder.setStatus(EnumOrderStatus.valueOf(status).getStatus());
        commonOrder.setCityCode(cityCode);
        commonOrder.setOperateType(EnumTradeType.CHATROOM_ADS.getName());
        commonOrder.setAcceptorCode(Constant.PLAT_CODE);
        commonOrder.setOperatorCode(userCode);
        int i = commonOrderService.insertSelective(commonOrder);
        if (i < 1) {
            // log.error("留言订单保存失败:"+shout.toString());
        }
        //保存账单信息
        CommonOrderDetail commonOrderDetailOUT_IN = new CommonOrderDetail();
        commonOrderDetailOUT_IN.setTxid(txid);
        commonOrderDetailOUT_IN.setType(EnumExpenseType.EXPENSE_IN.getName());
        commonOrderDetailOUT_IN.setCityCode(cityCode);
        commonOrderDetailOUT_IN.setAmount(amount);
        commonOrderDetailOUT_IN.setOperateType(EnumTradeType.CHATROOM_ADS.getName());
        commonOrderDetailOUT_IN.setOrderNo(orderNo);
        commonOrderDetailOUT_IN.setUserCode(Constant.PLAT_CODE);
        City city = cityService.selectByCityCode(cityCode);
        commonOrderDetailOUT_IN.setCityName(city == null ? "" : city.getCityName());
        insertSelective(commonOrderDetailOUT_IN);


        commonOrderDetailOUT_IN.setType(EnumExpenseType.EXPENSE_OUT.getName());
        commonOrderDetailOUT_IN.setUserCode(userCode);
        insertSelective(commonOrderDetailOUT_IN);


        //处理旧数据
        processOldAds(cityCode,orderNum);

        AdsPicture bean = new AdsPicture();
        bean.setAdsUrl(url);
        bean.setCityCode(cityCode);
        bean.setIsShow(Boolean.TRUE);
        bean.setOrderNo(orderNo);
        bean.setCityName(city == null ? "" : city.getCityName());
        bean.setOrderNum(orderNum);
        bean.setTxid(txid);

        adsPictureService.insert(bean);
    }

    private void processOldAds(String cityCode , Integer orderNum){
        AdsPicture co = new AdsPicture();
        co.setCityCode(cityCode);
        co.setIsShow(Boolean.TRUE);
        co.setOrderNum(orderNum);

        List<AdsPicture> ads = adsPictureService.getList(co);
        if(ads != null && ads.size() > 0) {
            AdsPicture update = new AdsPicture();
            update.setIsShow(Boolean.FALSE);
            AdsPicture condi = new AdsPicture();
            condi.setCityCode(cityCode);
            condi.setOrderNum(orderNum);
            adsPictureService.updateByCondition(update, condi);
        }
    }

    @Override
    public List<GetAdsPictureResponseBody> getAdsPicture(GetAdsPictureRequestBody getAdsPictureRequestBody) {
        List<GetAdsPictureResponseBody> list = new ArrayList<>();
        AdsPicture condi = new AdsPicture();
        condi.setCityCode(getAdsPictureRequestBody.getCityCode());
        condi.setIsShow(Boolean.TRUE);
        condi.setOrderBy("create_time desc");
        condi.setPageBeginIndex(0);
        condi.setPageSize(3);

        List<AdsPicture> adsPictures = adsPictureService.getList(condi);
        if (adsPictures != null && adsPictures.size() > 0) {
            for (AdsPicture adsPicture : adsPictures) {
                GetAdsPictureResponseBody body = new GetAdsPictureResponseBody();
                body.setAdsUrl(adsPicture.getAdsUrl());
                body.setOrderNum(adsPicture.getOrderNum());
                body.setTxid(adsPicture.getTxid());
                body.setCityCode(adsPicture.getCityCode());
                list.add(body);
            }
        }

        return list;
    }

    @Override
    public ResultCode processModifyAdsPicture(JSONObject json, String userCode) {
        String cityCode = json.getString("cityCode");

        CityDetail condiCityDetail = new CityDetail();
        condiCityDetail.setUserCode(userCode);
        condiCityDetail.setCityCode(cityCode);

        CityDetail cityDetail = cityDetailService.getByCondition(condiCityDetail);
        if (cityDetail == null) {
            return new ResultCode(StatusCode.ERROR_DELETE_ADS_PICTURE);
        }

        String txid = json.getString("txid");
        if (StringUtils.isBlank(txid)) {
            return new ResultCode(StatusCode.ERROR_TXID_EMPTY);
        }

        AdsPicture bean = new AdsPicture();
        bean.setIsShow(Boolean.FALSE);

        AdsPicture condi = new AdsPicture();
        condi.setTxid(txid);

        adsPictureService.updateByCondition(bean, condi);
        return new ResultCode(StatusCode.SUCCESS);

    }

    @Override
    public void processCallBack(String type, JSONObject payment) {
        String buttonData = payment.getString("buttonData");
        JSONObject buttonStr = JSON.parseObject(buttonData);
        String txid = payment.getString("txid");
        String status = payment.getString("status");
        CommonOrder cond = new CommonOrder();
        cond.setTxid(txid);
        cond.setStatus(EnumOrderStatus.PRE_LOCK.getStatus());
        List<CommonOrder> list = commonOrderService.getList(cond);

        if (list == null || list.size() == 0) {
            //插入订单
            processAdsPicture(buttonStr, buttonStr.getString("userCode"));
        }


        //如果订单收到
        if (EnumOrderStatus.RECEIVED.getName().equals(status)) {
            list.forEach(x -> {
                //保存付费订单
                x.setStatus(EnumOrderStatus.RECEIVED.getStatus());
                commonOrderService.updateByPrimaryKey(x);
            });
        }
        //如果订单完成
        if (EnumOrderStatus.COMPLETED.getName().equals(status)) {
            cond.setStatus(EnumOrderStatus.RECEIVED.getStatus());
            List<CommonOrder> list2 = commonOrderService.getList(cond);
            list2.forEach(x -> {
                x.setStatus(EnumOrderStatus.COMPLETED.getStatus());
                commonOrderService.updateByPrimaryKey(x);
            });

            //订单完成了,还有未处理订单
            if (list.size() > 0) {
                CommonOrderDetail commonOrderCond = new CommonOrderDetail();
                commonOrderCond.setTxid(txid);
                List<CommonOrderDetail> detailList = getList(commonOrderCond);
                if (detailList.size() < 1) {
                    if (!EnumTradeType.LIKE.getName().equals(type)) {
                        list.forEach(x -> {
                            //保存付费订单
                            x.setStatus(EnumOrderStatus.COMPLETED.getStatus());
                            commonOrderService.updateByPrimaryKey(x);
                            CommonOrderDetail commonOrderDetail = new CommonOrderDetail();
                            commonOrderDetail.setOrderNo(x.getOrderNo());
                            commonOrderDetail.setAmount(x.getAmount());
                            commonOrderDetail.setUserCode(x.getOperatorCode());
                            commonOrderDetail.setType(EnumExpenseType.EXPENSE_OUT.getName());
                            commonOrderDetail.setCreateTime(x.getCreateTime());
                            commonOrderDetail.setOperateType(type);
                            commonOrderDetail.setCityCode(x.getCityCode());
                            commonOrderDetail.setTxid(x.getTxid());
                            City city = cityService.selectByCityCode(x.getCityCode());
                            if (city != null) {
                                commonOrderDetail.setCityName(city.getCityName());
                            }
                            insertSelective(commonOrderDetail);

                            //保存收费订单
                            commonOrderDetail.setUserCode(x.getAcceptorCode());
                            commonOrderDetail.setType(EnumExpenseType.EXPENSE_IN.getName());
                            insertSelective(commonOrderDetail);
                        });
                    } else {
                        CommonOrderDetail commonOrderDetail = new CommonOrderDetail();
                        BigDecimal totalAmount = BigDecimal.ZERO;
                        for (CommonOrder x : list) {
                            totalAmount = totalAmount.add(x.getAmount());
                            //保存收费订单
                            x.setStatus(EnumOrderStatus.COMPLETED.getStatus());
                            commonOrderService.updateByPrimaryKey(x);
                            commonOrderDetail.setOrderNo(x.getOrderNo());
                            commonOrderDetail.setAmount(x.getAmount());
                            commonOrderDetail.setCreateTime(x.getCreateTime());
                            commonOrderDetail.setOperateType(type);
                            commonOrderDetail.setCityCode(x.getCityCode());
                            commonOrderDetail.setTxid(x.getTxid());
                            City city = cityService.selectByCityCode(x.getCityCode());
                            if (city != null) {
                                commonOrderDetail.setCityName(city.getCityName());
                            }
                            commonOrderDetail.setUserCode(x.getAcceptorCode());
                            commonOrderDetail.setType(EnumExpenseType.EXPENSE_IN.getName());
                            insertSelective(commonOrderDetail);
                        }
                        if (list.size() > 0) {
                            commonOrderDetail.setUserCode(list.get(0).getOperatorCode());
                            commonOrderDetail.setAmount(totalAmount);
                            commonOrderDetail.setType(EnumExpenseType.EXPENSE_OUT.getName());
                            insertSelective(commonOrderDetail);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void processCityPicture(JSONObject json, String userCode) {
        String cityCode = json.getString("cityCode");
        String txid = json.getString("txid");
        String status = json.getString("status");
        BigDecimal amount = json.getBigDecimal("amount");
        String orderNo = OrderNoUtils.makeRequestNo("cp_");
        String pictureCode = OrderNoUtils.makeRequestNo("p_");
        //获取城主
        CityDetail detail = cityDetailService.selectByCityCode(cityCode);

        CommonOrder commonOrder = new CommonOrder();
        commonOrder.setOrderNo(orderNo);
        commonOrder.setTxid(txid);
        commonOrder.setAmount(amount);
        commonOrder.setStatus(EnumOrderStatus.valueOf(status).getStatus());
        commonOrder.setCityCode(cityCode);
        commonOrder.setOperateType(EnumTradeType.CITY_PICTURE.getName());
        commonOrder.setAcceptorCode(Constant.PLAT_CODE);
        commonOrder.setOperatorCode(userCode);
        int i = commonOrderService.insertSelective(commonOrder);

        //保存账单信息
        CommonOrderDetail commonOrderDetailOUT = new CommonOrderDetail();
        commonOrderDetailOUT.setTxid(txid);
        commonOrderDetailOUT.setType(EnumExpenseType.EXPENSE_OUT.getName());
        commonOrderDetailOUT.setCityCode(cityCode);
        commonOrderDetailOUT.setAmount(amount);
        commonOrderDetailOUT.setOperateType(EnumTradeType.CITY_PICTURE.getName());
        commonOrderDetailOUT.setOrderNo(orderNo);
        commonOrderDetailOUT.setUserCode(userCode);
        City city = cityService.selectByCityCode(cityCode);
        commonOrderDetailOUT.setCityName(city == null ? "" : city.getCityName());
        insertSelective(commonOrderDetailOUT);

        //抽水 平台暂不抽水只给城主
        /*CommonOrderDetail commonOrderDetailIN = new CommonOrderDetail();
        commonOrderDetailIN.setTxid(txid);
        commonOrderDetailIN.setType(EnumExpenseType.EXPENSE_IN.getName());
        commonOrderDetailIN.setCityCode(cityCode);
        commonOrderDetailIN.setAmount(amount);
        commonOrderDetailIN.setOperateType(EnumTradeType.CITY_PICTURE.getName());
        commonOrderDetailIN.setOrderNo(orderNo);
        commonOrderDetailIN.setUserCode(detail.getUserCode());
        commonOrderDetailIN.setCityName(city == null ? "" : city.getCityName());
        commonOrderDetailIN.setUserCode(detail.getUserCode());
        insertSelective(commonOrderDetailIN);*/

        CityPicture bean = new CityPicture();
        bean.setPictureCode(pictureCode);
        bean.setCityCode(cityCode);
        bean.setCityName(city.getCityName());
        bean.setLikeNumber(0);
        bean.setOrderNo(orderNo);
        bean.setIsShow(Boolean.TRUE);
        bean.setTxid(txid);
        bean.setVersion(0);
        bean.setUserCode(userCode);//上传照片的人
        cityPictureService.insert(bean);
    }

    @Override
    public void processLikePicture(JSONObject json, String userCode) {

        String cityCode = json.getString("cityCode");
        String cityPictureCode = json.getString("cityPictureCode");
        String txid = json.getString("txid");
        String status = json.getString("status");
        BigDecimal amount = json.getBigDecimal("amount");

        String orderNo = OrderNoUtils.makeRequestNo("like_");

        CityDetail detail = cityDetailService.selectByCityCode(cityCode);
        //校验照片
        CityPicture bean = cityPictureService.selectByCode(cityPictureCode);
        if(bean == null){
            LOGGER.info("no picture");
            return;
        }

        CityPicture updateBean = new CityPicture();
        updateBean.setPictureCode(cityPictureCode);
        CityPicture condi = new CityPicture();
        condi.setPictureCode(cityPictureCode);
        int number=0;
        while (number<50){
            updateBean.setVersion(bean.getVersion()+1);
            updateBean.setLikeNumber(bean.getLikeNumber()+1);
            condi.setVersion(bean.getVersion());
            int i =cityPictureService.updateByCondition(updateBean, condi);
            if(i<1){
                bean=cityPictureService.getByCondition(condi);
            }else{
                break;
            }
            number++;
        }
        //保存点赞记录
        LikeLog likeLog = new LikeLog();
        likeLog.setChatCode(cityPictureCode);
        likeLog.setUserCode(userCode);
        likeLog.setCityCode(cityCode);
        likeLogService.insertSelective(likeLog);

        CommonOrder commonOrder = new CommonOrder();
        commonOrder.setOrderNo(orderNo);
        commonOrder.setTxid(txid);
        commonOrder.setAmount(amount.multiply(new BigDecimal(0.7)));
        commonOrder.setStatus(EnumOrderStatus.valueOf(status).getStatus());
        commonOrder.setCityCode(cityCode);
        commonOrder.setOperateType(EnumTradeType.PICTURE_LIKE.getName());
        commonOrder.setAcceptorCode(bean.getUserCode());//获取照片的拥有者
        commonOrder.setOperatorCode(userCode);
        int i = commonOrderService.insertSelective(commonOrder);
        if(i<1){
        }

        //保存城市拥有者订单信息
        commonOrder.setAcceptorCode(detail !=null ?  detail.getUserCode():
                platformService.getByPlatCode(Constant.PLAT_CODE).getPlatformCode());
        commonOrder.setAmount(amount.multiply(new BigDecimal(0.2)));


        int i1 = commonOrderService.insertSelective(commonOrder);
        if(i1<1){
        }
        String platformCode = platformService.getByPlatCode(Constant.PLAT_CODE).getPlatformCode();
        //保存平台订单信息
        commonOrder.setAcceptorCode(platformCode);
        commonOrder.setAmount(amount.multiply(new BigDecimal(0.1)));
        int i2 = commonOrderService.insertSelective(commonOrder);
        if(i2<1){

        }
        //保存账单信息
        //出帐人
        CommonOrderDetail commonOrderDetailOUT_IN = new CommonOrderDetail();
        commonOrderDetailOUT_IN.setTxid(txid);
        commonOrderDetailOUT_IN.setType(EnumExpenseType.EXPENSE_OUT.getName());
        commonOrderDetailOUT_IN.setCityCode(cityCode);
        commonOrderDetailOUT_IN.setAmount(amount);
        commonOrderDetailOUT_IN.setOperateType(EnumTradeType.PICTURE_LIKE.getName());
        commonOrderDetailOUT_IN.setOrderNo(orderNo);
        commonOrderDetailOUT_IN.setUserCode(userCode);
        City city = cityService.selectByCityCode(cityCode);
        commonOrderDetailOUT_IN.setCityName(city==null?null:city.getCityName());
        insertSelective(commonOrderDetailOUT_IN);
        //入账人(留言者,城市拥有者,平台)
        commonOrderDetailOUT_IN.setType(EnumExpenseType.EXPENSE_IN.getName());
        //照片上传者
        commonOrderDetailOUT_IN.setAmount(amount.multiply(new BigDecimal(0.7)));
        commonOrderDetailOUT_IN.setUserCode(bean.getUserCode());
        insertSelective(commonOrderDetailOUT_IN);
        //城市拥有者
        commonOrderDetailOUT_IN.setAmount(amount.multiply(new BigDecimal(0.2)));
        commonOrderDetailOUT_IN.setUserCode(detail !=null ? detail.getUserCode():platformCode);
        insertSelective(commonOrderDetailOUT_IN);
        //平台
        commonOrderDetailOUT_IN.setAmount(amount.multiply(new BigDecimal(0.1)));
        commonOrderDetailOUT_IN.setUserCode(platformCode);
        insertSelective(commonOrderDetailOUT_IN);


        User user = userService.selectByUserCode(userCode);
        //发送通知
        if(!userCode.equals(bean.getUserCode())) {
            LOGGER.info("图片点赞发送通知");
            HashMap<String, Object> sendData = new HashMap();
            sendData.put("sentUser", user.getUserName());
            sendData.put("cityName", city == null ? "" : city.getCityName());
            sendData.put("money", amount.multiply(new BigDecimal(0.7)));
            sendData.put("cityCode", cityCode);
            try {
                smsHandelUtils.send(EnumNoticeNid.PICTURE_LIKE, EnumNoticeType.TYPE_MESSAGE,
                        userCode, bean.getUserCode(),
                        bean.getUserCode(), sendData, null, null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<JSONObject> getBillIn(String userCode) {
        List<JSONObject> billIn= new ArrayList();
        OrderAmountDto orderAmount = orderDetailService.getOrderAmount(userCode);
        JSONObject city = new JSONObject();
        city.put("type","city_sell");
        city.put("money",orderAmount.getOrderAmountIn());
        billIn.add(city);
        BigDecimal cityBillIn = commonOrderDetailMapper.getCityBillIn(userCode);
        JSONObject cityInterests = new JSONObject();
        cityInterests.put("type","city_owner_interest");
        cityInterests.put("money",cityBillIn);
        billIn.add(cityInterests);
        BigDecimal communityBillIn = commonOrderDetailMapper.getCommunityBillIn(userCode);
        JSONObject communityInterests = new JSONObject();
        communityInterests.put("type","community_interest");
        communityInterests.put("money",communityBillIn);
        billIn.add(communityInterests);
        return billIn;
    }

    @Override
    public List<JSONObject> getBillOut(String userCode) {
        List<JSONObject> billOut= new ArrayList();
        OrderAmountDto orderAmount = orderDetailService.getOrderAmount(userCode);
        JSONObject city = new JSONObject();
        city.put("type","city_sell");
        city.put("money",orderAmount.getOrderAmountOut());
        billOut.add(city);
        BigDecimal cityBillIn = commonOrderDetailMapper.getCityBillOut(userCode);
        JSONObject cityInterests = new JSONObject();
        cityInterests.put("type","city_owner_interest");
        cityInterests.put("money",cityBillIn);
        billOut.add(cityInterests);
        BigDecimal communityBillIn = commonOrderDetailMapper.getCommunityBillOut(userCode);
        JSONObject communityInterests = new JSONObject();
        communityInterests.put("type","community_interest");
        communityInterests.put("money",communityBillIn);
        billOut.add(communityInterests);
        return billOut;
    }



    @Override
    public List<HotCityLasted> getCityHotLasted(Integer beginIndex, Integer pageSize) {
        long l = System.currentTimeMillis();
        List<HotCityLasted> list = commonOrderDetailMapper.getCityHotLasted(beginIndex, pageSize);
        List<HotCityLasted> newList = new ArrayList<HotCityLasted>();

        LOGGER.info("循环开始====================");


        if (list != null) {
            String startTime = DateUtil.rollDayFormat(DateUtil.convertToGMT(), -6, "yyyy-MM-dd");
            String endTime = DateUtil.dateStr(DateUtil.convertToGMT(), "yyyy-MM-dd");
            for (int i = 0; i < list.size(); i++) {
                HotCityLasted h = list.get(i);
                Long orderTime = DateUtil.getTime(h.getCreateTime());
                Long time = DateUtil.convertToGMT().getTime() / 1000 - orderTime;

                Integer hotCount = commonOrderDetailMapper.getCityHotNew(h.getCityCode(), startTime, endTime);

                if (0 < time && time < Constant.HOUR_TIME) {
                    h.setCreateTimeStr(new StringBuilder(
                            String.valueOf(Math.round(time / Constant.MINUTE_TIME)))
                            .append(Constant.MIN).toString());
                } else if (Constant.HOUR_TIME <= time && time < Constant.DAY_TIME) {

                    h.setCreateTimeStr(new StringBuilder(
                            String.valueOf(Math.round(time / Constant.HOUR_TIME)))
                            .append(Constant.HOUR).toString());

                } else if (Constant.DAY_TIME <= time) {
                    h.setCreateTimeStr(new StringBuilder(
                            String.valueOf(Math.round(time / Constant.DAY_TIME)))
                            .append(Constant.DAY).toString());

                }

                h.setCount(hotCount == null ? 0 : hotCount);
                newList.add(h);
            }
        }

        long l1 = System.currentTimeMillis();
        LOGGER.info("循环结束===================={}",l1-l);

        return newList;
    }



    @Override
    public List<HotCityLasted> getCityHotLasted() {
        long l = System.currentTimeMillis();
        List<HotCityLasted> list = commonOrderDetailMapper.getCityHotLastedQuartz();
        //List<HotCityLasted> newList = new ArrayList<HotCityLasted>();

        if (list != null) {
            String startTime = "";
            String endTime = "";
            if("local".equals(env)){
                startTime =  DateUtil.rollDayFormat(DateUtil.getNow(), -6, "yyyy-MM-dd");
                endTime =  DateUtil.dateStr(DateUtil.getNow(), "yyyy-MM-dd");
            }else {
                startTime =  DateUtil.rollDayFormat(DateUtil.convertToGMT(), -6, "yyyy-MM-dd");
                endTime =  DateUtil.dateStr(DateUtil.convertToGMT(), "yyyy-MM-dd");
            }
            for (int i = 0; i < list.size(); i++) {
                HotCityLasted h = list.get(i);
                Long orderTime = DateUtil.getTime(h.getCreateTime());
                Long time = DateUtil.convertToGMT().getTime() / 1000 - orderTime;

                Integer hotCount = commonOrderDetailMapper.getCityHotNew(h.getCityCode(), startTime, endTime);
                if (0 < time && time < Constant.HOUR_TIME) {
                    h.setCreateTimeStr(new StringBuilder(
                            String.valueOf(Math.round(time / Constant.MINUTE_TIME)))
                            .append(Constant.MIN).toString());
                } else if (Constant.HOUR_TIME <= time && time < Constant.DAY_TIME) {

                    h.setCreateTimeStr(new StringBuilder(
                            String.valueOf(Math.round(time / Constant.HOUR_TIME)))
                            .append(Constant.HOUR).toString());

                } else if (Constant.DAY_TIME <= time) {
                    h.setCreateTimeStr(new StringBuilder(
                            String.valueOf(Math.round(time / Constant.DAY_TIME)))
                            .append(Constant.DAY).toString());

                }

                h.setCount(hotCount == null ? 0 : hotCount);
                //newList.add(h);
            }
        }
        long l1 = System.currentTimeMillis();

        LOGGER.info(l1-l);
        return list;
    }
}