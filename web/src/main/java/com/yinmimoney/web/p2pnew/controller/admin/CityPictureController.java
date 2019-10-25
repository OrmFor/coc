package com.yinmimoney.web.p2pnew.controller.admin;

import cc.s2m.util.Page;
import com.alibaba.fastjson.JSONObject;
import com.yinmimoney.web.p2pnew.constant.Constant;
import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.core.ResultCode;
import com.yinmimoney.web.p2pnew.core.StatusCode;
import com.yinmimoney.web.p2pnew.enums.EnumNoticeNid;
import com.yinmimoney.web.p2pnew.enums.EnumNoticeType;
import com.yinmimoney.web.p2pnew.pojo.AdsPicture;
import com.yinmimoney.web.p2pnew.pojo.CityPicture;
import com.yinmimoney.web.p2pnew.service.ICityPicture;
import com.yinmimoney.web.p2pnew.util.SmsHandelUtils;
import com.yinmimoney.web.p2pnew.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * Created by dyf on 2019/8/16 11:10
 */
@RestController("admin_cityPictureController")
@RequestMapping("/admin/cityPicture")
public class CityPictureController extends BaseController {
    @Autowired
    private ICityPicture cityPictureService;
    @Autowired
    private SmsHandelUtils smsHandelUtils;

    /**聊天室图片列表获取*/
    @RequestMapping("/list")
    public ResultCode getList(){
        JSONObject json = getJsonFromRequest();
        String txid = json.getString("txid");
        String code = json.getString("code");
        Integer isHidden = json.getInteger("isHidden");
        CityPicture cond = new CityPicture();
        if(StringUtil.isNotBlank(txid)){
            cond.setTxid(txid);
        }
        if(StringUtil.isNotBlank(code)){
            cond.setOrderNo(code);
        }
        String cityName = json.getString("cityName");
        if(StringUtil.isNotBlank(cityName)){
            cond.setCityName(cityName);
        }
        if(isHidden!=null){
            cond.setIsShow(isHidden==0?Boolean.FALSE:Boolean.TRUE);
        }
        Integer pageNumber = json.getInteger("pageNumber");
        if(pageNumber==null){pageNumber=1;}
        cond.setPageNumber(pageNumber);
        cond.setPageSize(10);
        Page<CityPicture> pageBean = cityPictureService.getPage(cond, null);
        JSONObject result = new JSONObject();
        result.put("list",pageBean.getResult());
        result.put("pageNumber",pageBean.getCurPage());
        result.put("totalPage",pageBean.getTotalPage());
        result.put("totalCount",pageBean.getTotalRow());
        return new ResultCode(StatusCode.SUCCESS,result);
    }
    /**聊天室图片隐藏*/
    @RequestMapping("/hidden")
    public ResultCode hidden(){
        JSONObject json = getJsonFromRequest();
        String txid = json.getString("txid");
        if(StringUtil.isBlank(txid)){
            return new ResultCode(StatusCode.ERROR_LACK_PARAM);
        }
        CityPicture cond = new CityPicture();
        cond.setTxid(txid);
        cond.setIsShow(Boolean.TRUE);
        CityPicture adsPicture = cityPictureService.getByCondition(cond);
        if(adsPicture==null){
            return new ResultCode(StatusCode.ERROR_PARAM);
        }
        adsPicture.setIsShow(Boolean.FALSE);
        int row = cityPictureService.updateByPrimaryKeySelective(adsPicture);
        if(row>0){
            HashMap<String, Object> sendData = new HashMap();
            sendData.put("content",adsPicture.getTxid());
            try {
                smsHandelUtils.send(EnumNoticeNid.CITY_PICTURE_HIDDEN, EnumNoticeType.TYPE_MESSAGE, Constant.PLAT_CODE,adsPicture.getUserCode(),
                        adsPicture.getUserCode(), sendData, null, null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ResultCode(StatusCode.SUCCESS);
    }

    /**聊天室图片显示*/
    @RequestMapping("/hidden/open")
    public ResultCode hiddenOpen(){
        JSONObject json = getJsonFromRequest();
        String txid = json.getString("txid");
        if(StringUtil.isBlank(txid)){
            return new ResultCode(StatusCode.ERROR_LACK_PARAM);
        }
        CityPicture cond = new CityPicture();
        cond.setTxid(txid);
        cond.setIsShow(Boolean.FALSE);
        CityPicture adsPicture = cityPictureService.getByCondition(cond);
        if(adsPicture==null){
            return new ResultCode(StatusCode.ERROR_PARAM);
        }
        adsPicture.setIsShow(Boolean.TRUE);
        cityPictureService.updateByPrimaryKeySelective(adsPicture);
        return new ResultCode(StatusCode.SUCCESS);
    }}

