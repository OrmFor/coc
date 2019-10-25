package com.yinmimoney.web.p2pnew.controller;

import cc.s2m.util.Page;
import com.alibaba.fastjson.JSONObject;
import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.core.ResultCode;
import com.yinmimoney.web.p2pnew.core.StatusCode;
import com.yinmimoney.web.p2pnew.pojo.ApiToken;
import com.yinmimoney.web.p2pnew.pojo.CommonOrderDetail;
import com.yinmimoney.web.p2pnew.requestbody.GetAdsPictureRequestBody;
import com.yinmimoney.web.p2pnew.responsebody.GetAdsPictureResponseBody;
import com.yinmimoney.web.p2pnew.service.ICommonOrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dyf on 2019/7/21 13:57
 */
@RestController("api_commonOrderDetail")
@RequestMapping("/api/1.0/common")
public class CommonOrderDetailController extends BaseController {
    @Autowired
    private ICommonOrderDetail commonOrderDetailService;

    @RequestMapping("/community/incomeAndOut")
    public ResultCode communityIncomeAndOut(){
        ApiToken apiToken = getApiToken();
        String userCode = apiToken.getUserCode();
        JSONObject json = getJsonFromRequest();
        Map<String,Object> map= new HashMap();
        map.put("userCode",userCode);
        Integer page = json.getInteger("pageNumber");
        if(page==null||page<1){page=1;}
        Integer pageSize = json.getInteger("pageSize");
        if(pageSize==null){pageSize=10;}
        int beginIndex=(page-1)*pageSize;
        map.put("beginIndex",beginIndex);
        map.put("pageSize",pageSize);
        Page<CommonOrderDetail> pageBean = commonOrderDetailService.getCommunicateBillPage(map);
        JSONObject result = new JSONObject();
        result.put("list",pageBean.getResult());
        result.put("pageNumber",pageBean.getCurPage());
        result.put("totalPage",pageBean.getTotalPage());
        result.put("totalCount",pageBean.getTotalRow());
        return new ResultCode(StatusCode.SUCCESS,result);
    }

    @RequestMapping("/city/incomeAndOut")
    public ResultCode cityIncomeAndOut(){
        ApiToken apiToken = getApiToken();
        String userCode = apiToken.getUserCode();
        JSONObject json = getJsonFromRequest();
        Map<String,Object> map= new HashMap();
        map.put("userCode",userCode);
        Integer page = json.getInteger("pageNumber");
        if(page==null||page<1){page=1;}
        Integer pageSize = json.getInteger("pageSize");
        if(pageSize==null){pageSize=10;}
        int beginIndex=(page-1)*pageSize;
        map.put("beginIndex",beginIndex);
        map.put("pageSize",pageSize);
        Page<CommonOrderDetail> pageBean = commonOrderDetailService.getCityBillPage(map);
        JSONObject result = new JSONObject();
        result.put("list",pageBean.getResult());
        result.put("pageNumber",pageBean.getCurPage());
        result.put("totalPage",pageBean.getTotalPage());
        result.put("totalCount",pageBean.getTotalRow());
        return new ResultCode(StatusCode.SUCCESS,result);
    }

    /**收益明细*/
    @RequestMapping("/In/infos")
    public ResultCode getInInfos(){
        List<JSONObject> billIn = commonOrderDetailService.getBillIn(getApiToken().getUserCode());
        return new ResultCode(StatusCode.SUCCESS,billIn);
    }

    /**支出明细*/
    @RequestMapping("/Out/infos")
    public ResultCode getOutInfos(){
        List<JSONObject> billOut = commonOrderDetailService.getBillOut(getApiToken().getUserCode());
        return new ResultCode(StatusCode.SUCCESS,billOut);
    }

}
