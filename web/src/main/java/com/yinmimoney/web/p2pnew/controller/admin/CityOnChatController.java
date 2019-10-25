package com.yinmimoney.web.p2pnew.controller.admin;

import cc.s2m.util.Page;
import cc.s2m.web.utils.webUtils.vo.Expressions;
import com.alibaba.fastjson.JSONObject;
import com.yinmimoney.web.p2pnew.constant.Constant;
import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.core.ResultCode;
import com.yinmimoney.web.p2pnew.core.StatusCode;
import com.yinmimoney.web.p2pnew.enums.EnumNoticeNid;
import com.yinmimoney.web.p2pnew.enums.EnumNoticeType;
import com.yinmimoney.web.p2pnew.pojo.City;
import com.yinmimoney.web.p2pnew.pojo.CityOnChat;
import com.yinmimoney.web.p2pnew.service.ICity;
import com.yinmimoney.web.p2pnew.service.ICityOnChat;
import com.yinmimoney.web.p2pnew.util.JSONFiledUtils;
import com.yinmimoney.web.p2pnew.util.SmsHandelUtils;
import com.yinmimoney.web.p2pnew.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * Created by dyf on 2019/8/5 10:00
 */
@RestController("admin_cityOnChatController")
@RequestMapping("/admin/cityOnChat")
public class CityOnChatController extends BaseController {

    @Autowired
    private ICityOnChat cityOnChatService;
    @Autowired
    private ICity cityService;
    @Autowired
    private SmsHandelUtils smsHandelUtils;
    /**留言列表获取*/
    @RequestMapping("/list")
    public ResultCode getList(){
        JSONObject json = getJsonFromRequest();
        String txid = json.getString("txid");
        String code = json.getString("code");
        String content = json.getString("content");
        Integer isHidden = json.getInteger("isHidden");
        CityOnChat cond = new CityOnChat();
        if(StringUtil.isNotBlank(txid)){
            cond.setTxid(txid);
        }
        if(StringUtil.isNotBlank(code)){
            cond.setCode(code);
        }
        if(StringUtil.isNotBlank(content)){
            cond.and(Expressions.like("content","%"+content+"%"));
        }
        if(isHidden!=null){
            cond.setIsHidden(isHidden);
        }
        Integer pageNumber = json.getInteger("pageNumber");
        if(pageNumber==null){pageNumber=1;}
        cond.setPageNumber(pageNumber);
        cond.setPageSize(10);
        Page<CityOnChat> pageBean = cityOnChatService.getPage(cond, null);
        pageBean.getResult().forEach(x->{
            City city = cityService.selectByCityCode(x.getCityCode());
            if(city!=null){
                x.setCityName(city.getCityName());
            }
        });
        JSONObject result = new JSONObject();
        result.put("list",pageBean.getResult());
        result.put("pageNumber",pageBean.getCurPage());
        result.put("totalPage",pageBean.getTotalPage());
        result.put("totalCount",pageBean.getTotalRow());
        return new ResultCode(StatusCode.SUCCESS,result);
    }
    /**留言隐藏*/
    @RequestMapping("/hidden")
    public ResultCode hidden(){
        JSONObject json = getJsonFromRequest();
        String txid = json.getString("txid");
        if(StringUtil.isBlank(txid)){
            return new ResultCode(StatusCode.ERROR_LACK_PARAM);
        }
        CityOnChat cond = new CityOnChat();
        cond.setTxid(txid);
        cond.setIsHidden(0);
        CityOnChat cityOnChat = cityOnChatService.getByCondition(cond);
        if(cityOnChat==null){
            return new ResultCode(StatusCode.ERROR_PARAM);
        }
        //0:不隐藏   1:隐藏
        cityOnChat.setIsHidden(1);
        int row = cityOnChatService.updateByPrimaryKeySelective(cityOnChat);
        if(row>0){
            HashMap<String, Object> sendData = new HashMap();
            sendData.put("content", JSONFiledUtils.replaceSensitiveChar(cityOnChat.getContent()));
            try {
                smsHandelUtils.send(EnumNoticeNid.CHAT_CONTENT_HIDDEN, EnumNoticeType.TYPE_MESSAGE, Constant.PLAT_CODE,cityOnChat.getUserCode(),
                        cityOnChat.getUserCode(), sendData, null, null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ResultCode(StatusCode.SUCCESS);
    }

    /**留言显示*/
    @RequestMapping("/hidden/open")
    public ResultCode hiddenOpen(){
        JSONObject json = getJsonFromRequest();
        String txid = json.getString("txid");
        if(StringUtil.isBlank(txid)){
            return new ResultCode(StatusCode.ERROR_LACK_PARAM);
        }
        CityOnChat cond = new CityOnChat();
        cond.setTxid(txid);
        cond.setIsHidden(1);
        CityOnChat cityOnChat = cityOnChatService.getByCondition(cond);
        if(cityOnChat==null){
            return new ResultCode(StatusCode.ERROR_PARAM);
        }
        //0:不隐藏   1:隐藏
        cityOnChat.setIsHidden(0);
        cityOnChatService.updateByPrimaryKeySelective(cityOnChat);
        return new ResultCode(StatusCode.SUCCESS);
    }

}
