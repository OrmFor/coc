package com.yinmimoney.web.p2pnew.service.impl;

import com.yinmimoney.web.p2pnew.constant.Constant;
import com.yinmimoney.web.p2pnew.dao.CityOnChatMapper;
import com.yinmimoney.web.p2pnew.dto.Shout;
import com.yinmimoney.web.p2pnew.dto.ShoutResponse;
import com.yinmimoney.web.p2pnew.enums.*;
import com.yinmimoney.web.p2pnew.pojo.*;
import com.yinmimoney.web.p2pnew.service.*;
import com.yinmimoney.web.p2pnew.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class CityOnChatImpl extends BaseServiceImpl<CityOnChat, CityOnChatMapper, Integer> implements ICityOnChat {
    private static final Logger log = LogManager.getLogger(CityOnChatImpl.class);
    @Autowired
    private CityOnChatMapper cityOnChatMapper;
    @Autowired
    private ICommonOrder commonOrderService;
    @Autowired
    private IApiToken apiTokenService;
    @Autowired
    private IUser userService;
    @Autowired
    private ILikeLog likeLogService;
    @Autowired
    private IPlatform platformService;
    @Autowired
    private SmsHandelUtils smsHandelUtils;
    @Autowired
    private ICityDetail cityDetailService;
    @Autowired
    private ICity cityService;
    @Autowired
    private ICommonOrderDetail commonOrderDetailService;

    protected CityOnChatMapper getDao() {
        return cityOnChatMapper;
    }


    @Override
    public ShoutResponse processSpeak(Shout shout) {
        //对请求参数进行校验
        if(shout.getMoney()==null||StringUtil.isAnyBlank(shout.getSpeakType(),shout.getCityCode(),shout.getToken(),shout.getOwerCode(),shout.getTxId())){
            return new ShoutResponse(102,"Error Submit Message");
        }
        //对用户进行登陆校验
        String token = shout.getToken();
        if(StringUtil.isBlank(token)){
            return new ShoutResponse(100,"Error Login Info");
        }
        token= TokenUtil.getTokenString(token);
        ApiToken byToken = apiTokenService.getByToken(token);
        if(byToken==null){
            return new ShoutResponse(100,"Error Login Info");
        }
        User buyUser = userService.selectByUserCode(byToken.getUserCode());
        User owerUser = userService.selectByUserCode(shout.getOwerCode());
        if(buyUser==null||owerUser==null){
            return new ShoutResponse(101,"Error User Info");
        }
        //对留言进行保存
        CityOnChat cityOnChat = new CityOnChat();
        //生成留言编码
        String speak_ = OrderNoUtils.makeRequestNo("speak_");
        String speakOrderNo =speak_;
        //如果留言含有表情：
        String message = shout.getMessage();
        cityOnChat.setContent(message);
        cityOnChat.setCode(speakOrderNo);
        cityOnChat.setCityCode(shout.getCityCode());
        cityOnChat.setUserName(buyUser.getUserName());
        cityOnChat.setTxid(shout.getTxId());
        cityOnChat.setUserCode(buyUser.getUserCode());
        cityOnChat.setSpeakType(shout.getSpeakType());
        int row = insertSelective(cityOnChat);
        if(row<1){
          log.error("留言保存失败："+shout.toString());
          throw new RuntimeException("留言保存失败");
        }
        //留言通知
        City city = cityService.selectByCityCode(shout.getCityCode());
        if(!buyUser.getUserCode().equals(owerUser.getUserCode())) {
            HashMap<String, Object> sendData = new HashMap();
            sendData.put("sentUser", buyUser.getUserName());
            sendData.put("content", JSONFiledUtils.replaceSensitiveChar(shout.getMessage()));
            sendData.put("money", shout.getMoney());
            sendData.put("cityCode", shout.getCityCode());
            sendData.put("cityName", city != null ? city.getCityName() : "");
            try {
                smsHandelUtils.send(EnumNoticeNid.LEAVE_A_MESSAGE, EnumNoticeType.TYPE_MESSAGE, buyUser.getUserCode(), owerUser.getUserCode(),
                        owerUser.getUserCode(), sendData, null, null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //保存订单信息
        CommonOrder commonOrder = new CommonOrder();
        commonOrder.setOrderNo(OrderNoUtils.makeRequestNo("order_"));
        commonOrder.setTxid(shout.getTxId());
        commonOrder.setAmount(shout.getMoney());
        commonOrder.setStatus(EnumOrderStatus.PRE_LOCK.getStatus());
        commonOrder.setCityCode(shout.getCityCode());
        commonOrder.setOperateType(EnumTradeType.SPEAK.getName());
        commonOrder.setAcceptorCode(owerUser.getUserCode());
        commonOrder.setOperatorCode(buyUser.getUserCode());
        int i = commonOrderService.insertSelective(commonOrder);
        if(i<1){
            log.error("留言订单保存失败:"+shout.toString());
        }
        //保存账单信息
        //出帐人
        CommonOrderDetail commonOrderDetailOUT_IN = new CommonOrderDetail();
        commonOrderDetailOUT_IN.setTxid(shout.getTxId());
        commonOrderDetailOUT_IN.setType(EnumExpenseType.EXPENSE_OUT.getName());
        commonOrderDetailOUT_IN.setCityCode(shout.getCityCode());
        commonOrderDetailOUT_IN.setAmount(shout.getMoney());
        commonOrderDetailOUT_IN.setOperateType(EnumTradeType.SPEAK.getName());
        commonOrderDetailOUT_IN.setOrderNo(speak_);
        commonOrderDetailOUT_IN.setUserCode(buyUser.getUserCode());
        commonOrderDetailOUT_IN.setCityName(city==null?null:city.getCityName());
        commonOrderDetailService.insertSelective(commonOrderDetailOUT_IN);
        //入账人
        commonOrderDetailOUT_IN.setType(EnumExpenseType.EXPENSE_IN.getName());
        commonOrderDetailOUT_IN.setUserCode(owerUser.getUserCode());
        commonOrderDetailService.insertSelective(commonOrderDetailOUT_IN);

        //返回消息格式
        ShoutResponse shoutResponse = new ShoutResponse();
        shoutResponse.setCode(0);
        shoutResponse.setContent(shout.getMessage());
        shoutResponse.setSpeakCode(speakOrderNo);
        shoutResponse.setUserName(buyUser.getUserName());
        shoutResponse.setUserCode(buyUser.getUserCode());
        shoutResponse.setAddTime(DateUtil.convertToGMT());
        shoutResponse.setTxid(shout.getTxId());
        shoutResponse.setSpeakType(shout.getSpeakType());
        return shoutResponse;
    }

    @Override
    public ShoutResponse processLike(Shout shout) {
        //对请求参数进行校验
        if(shout.getMoney()==null||StringUtil.isAnyBlank(shout.getSpeakCode(),shout.getCityCode(),shout.getToken(),shout.getOwerCode(),shout.getTxId())){
            return new ShoutResponse(102,"Error Submit Message");
        }
        //对用户进行登陆校验
        String token = shout.getToken();
        if(StringUtil.isBlank(token)){
            return new ShoutResponse(100,"Error Login Info");
        }
        token= TokenUtil.getTokenString(token);
        ApiToken byToken = apiTokenService.getByToken(token);
        if(byToken==null){
            return new ShoutResponse(100,"Error Login Info");
        }
        User buyUser = userService.selectByUserCode(byToken.getUserCode());
        User owerUser = userService.selectByUserCode(shout.getOwerCode());
        if(buyUser==null||owerUser==null){
            return new ShoutResponse(101,"Error User Info");
        }
        //对留言编号进行校验
        CityOnChat cond = new CityOnChat();
        cond.setCode(shout.getSpeakCode());
        CityOnChat byCondition = getByCondition(cond);
        if(byCondition==null){
            return new ShoutResponse(103,"Error Message Info");
        }
        //对点赞数量进行更新

        CityOnChat update = new CityOnChat();
        update.setCode(shout.getSpeakCode());
        CityOnChat condi = new CityOnChat();
        condi.setCode(shout.getSpeakCode());
        int number=0;
        while (number<50){
            update.setVersion(byCondition.getVersion()+1);
            update.setLikeNumber(byCondition.getLikeNumber()+1);
            condi.setVersion(byCondition.getVersion());
            int i = updateByCondition(update, condi);
            if(i<1){
                 byCondition=getByCondition(cond);
            }else{
                break;
            }
            number++;
        }
        //保存点赞记录
        LikeLog likeLog = new LikeLog();
        likeLog.setChatCode(shout.getSpeakCode());
        likeLog.setUserCode(buyUser.getUserCode());
        likeLog.setCityCode(shout.getCityCode());
        likeLogService.insertSelective(likeLog);
        //保存留言人订单信息
        City city = cityService.selectByCityCode(shout.getCityCode());
        String order_ = OrderNoUtils.makeRequestNo("order_");
        CommonOrder commonOrder = new CommonOrder();
        commonOrder.setOrderNo(order_);
        commonOrder.setTxid(shout.getTxId());
        commonOrder.setAmount(shout.getMoney().multiply(new BigDecimal(0.7)));
        commonOrder.setStatus(EnumOrderStatus.PRE_LOCK.getStatus());
        commonOrder.setCityCode(shout.getCityCode());
        commonOrder.setOperateType(EnumTradeType.LIKE.getName());
        commonOrder.setAcceptorCode(owerUser.getUserCode());
        commonOrder.setOperatorCode(buyUser.getUserCode());
        int i = commonOrderService.insertSelective(commonOrder);
        if(i<1){
            log.error("留言人点赞订单保存失败:"+shout.toString());
        }
        //留言点赞通知
        if(!buyUser.getUserCode().equals(owerUser.getUserCode())) {
            HashMap<String, Object> sendData = new HashMap();
            sendData.put("sentUser", buyUser.getUserName());
            sendData.put("content", JSONFiledUtils.replaceSensitiveChar(byCondition.getContent()));
            sendData.put("money", shout.getMoney().multiply(new BigDecimal(0.7)));
            sendData.put("cityCode", shout.getCityCode());
            sendData.put("cityName", city != null ? city.getCityName() : "");
            try {
                smsHandelUtils.send(EnumNoticeNid.GIVE_A_LIKE, EnumNoticeType.TYPE_MESSAGE, buyUser.getUserCode(), owerUser.getUserCode(),
                        owerUser.getUserCode(), sendData, null, null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //保存城市拥有者订单信息
        CityDetail cityDetail = cityDetailService.selectByCityCode(shout.getCityCode());
        commonOrder.setAcceptorCode(cityDetail!=null?cityDetail.getUserCode():platformService.getByPlatCode(Constant.PLAT_CODE).getPlatformCode());
        commonOrder.setAmount(shout.getMoney().multiply(new BigDecimal(0.2)));
        int i1 = commonOrderService.insertSelective(commonOrder);
        if(i1<1){
            log.error("城市拥有者点赞订单保存失败:"+shout.toString());
        }
        String platformCode = platformService.getByPlatCode(Constant.PLAT_CODE).getPlatformCode();
        //保存平台订单信息
        commonOrder.setAcceptorCode(platformCode);
        commonOrder.setAmount(shout.getMoney().multiply(new BigDecimal(0.1)));
        int i2 = commonOrderService.insertSelective(commonOrder);
        if(i2<1){
            log.error("平台点赞订单保存失败:"+shout.toString());
        }
        //保存账单信息
        //出帐人
        CommonOrderDetail commonOrderDetailOUT_IN = new CommonOrderDetail();
        commonOrderDetailOUT_IN.setTxid(shout.getTxId());
        commonOrderDetailOUT_IN.setType(EnumExpenseType.EXPENSE_OUT.getName());
        commonOrderDetailOUT_IN.setCityCode(shout.getCityCode());
        commonOrderDetailOUT_IN.setAmount(shout.getMoney());
        commonOrderDetailOUT_IN.setOperateType(EnumTradeType.LIKE.getName());
        commonOrderDetailOUT_IN.setOrderNo(order_);
        commonOrderDetailOUT_IN.setUserCode(buyUser.getUserCode());
        commonOrderDetailOUT_IN.setCityName(city==null?null:city.getCityName());
        commonOrderDetailService.insertSelective(commonOrderDetailOUT_IN);
        //入账人(留言者,城市拥有者,平台)
        commonOrderDetailOUT_IN.setType(EnumExpenseType.EXPENSE_IN.getName());
        //留言者
        commonOrderDetailOUT_IN.setAmount(shout.getMoney().multiply(new BigDecimal(0.7)));
        commonOrderDetailOUT_IN.setUserCode(owerUser.getUserCode());
        commonOrderDetailService.insertSelective(commonOrderDetailOUT_IN);
        //城市拥有者
        commonOrderDetailOUT_IN.setAmount(shout.getMoney().multiply(new BigDecimal(0.2)));
        commonOrderDetailOUT_IN.setUserCode(cityDetail!=null?cityDetail.getUserCode():platformCode);
        commonOrderDetailService.insertSelective(commonOrderDetailOUT_IN);
        //平台
        commonOrderDetailOUT_IN.setAmount(shout.getMoney().multiply(new BigDecimal(0.1)));
        commonOrderDetailOUT_IN.setUserCode(platformCode);
        commonOrderDetailService.insertSelective(commonOrderDetailOUT_IN);

        //返回消息格式
        ShoutResponse shoutResponse = new ShoutResponse();
        shoutResponse.setCode(0);
        shoutResponse.setContent(shout.getMessage());
        shoutResponse.setSpeakCode(shout.getSpeakCode());
        shoutResponse.setUserName(buyUser.getUserName());
        shoutResponse.setUserCode(buyUser.getUserCode());
        shoutResponse.setAddTime(DateUtil.getNow());
        return shoutResponse;
    }
}