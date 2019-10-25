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
import com.yinmimoney.web.p2pnew.pojo.CityDetail;
import com.yinmimoney.web.p2pnew.service.IAdsPicture;
import com.yinmimoney.web.p2pnew.service.ICityDetail;
import com.yinmimoney.web.p2pnew.util.SmsHandelUtils;
import com.yinmimoney.web.p2pnew.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * Created by dyf on 2019/8/6 11:14
 */
@RestController("admin_adsPictureController")
@RequestMapping("/admin/adsPicture")
public class AdsPictureController extends BaseController {
    @Autowired
    private IAdsPicture adsPictureService;
    @Autowired
    private SmsHandelUtils smsHandelUtils;
    @Autowired
    private ICityDetail cityDetailService;
    /**聊天室图片列表获取*/
    @RequestMapping("/list")
    public ResultCode getList(){
        JSONObject json = getJsonFromRequest();
        String txid = json.getString("txid");
        String code = json.getString("code");
        Integer isHidden = json.getInteger("isHidden");
        AdsPicture cond = new AdsPicture();
        if(StringUtil.isNotBlank(txid)){
            cond.setTxid(txid);
        }
        if(StringUtil.isNotBlank(code)){
            cond.setOrderNo(code);
        }
        if(isHidden!=null){
            cond.setIsShow(isHidden==0?Boolean.FALSE:Boolean.TRUE);
        }
        String cityName = json.getString("cityName");
        if(StringUtil.isNotBlank(cityName)){
            cond.setCityName(cityName);
        }
        Integer pageNumber = json.getInteger("pageNumber");
        if(pageNumber==null){pageNumber=1;}
        cond.setPageNumber(pageNumber);
        cond.setPageSize(10);
        Page<AdsPicture> pageBean = adsPictureService.getPage(cond, null);
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
        AdsPicture cond = new AdsPicture();
        cond.setTxid(txid);
        cond.setIsShow(Boolean.TRUE);
        AdsPicture adsPicture = adsPictureService.getByCondition(cond);
        if(adsPicture==null){
            return new ResultCode(StatusCode.ERROR_PARAM);
        }
        adsPicture.setIsShow(Boolean.FALSE);
        int row = adsPictureService.updateByPrimaryKeySelective(adsPicture);
        CityDetail cityDetail = cityDetailService.selectByCityCode(adsPicture.getCityCode());
        String userCode=(cityDetail!=null?cityDetail.getUserCode():Constant.PLAT_CODE);
        if(row>0){
            HashMap<String, Object> sendData = new HashMap();
            sendData.put("content",adsPicture.getTxid());
            try {
                smsHandelUtils.send(EnumNoticeNid.ADS_PICTURE_HIDDEN, EnumNoticeType.TYPE_MESSAGE, Constant.PLAT_CODE,userCode,
                        userCode, sendData, null, null, null);
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
        AdsPicture cond = new AdsPicture();
        cond.setTxid(txid);
        cond.setIsShow(Boolean.FALSE);
        AdsPicture adsPicture = adsPictureService.getByCondition(cond);
        if(adsPicture==null){
            return new ResultCode(StatusCode.ERROR_PARAM);
        }
        adsPicture.setIsShow(Boolean.TRUE);
        adsPictureService.updateByPrimaryKeySelective(adsPicture);
        return new ResultCode(StatusCode.SUCCESS);
    }}
