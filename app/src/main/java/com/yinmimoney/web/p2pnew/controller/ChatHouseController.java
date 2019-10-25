package com.yinmimoney.web.p2pnew.controller;

import com.alibaba.fastjson.JSONObject;
import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.core.ResultCode;
import com.yinmimoney.web.p2pnew.core.StatusCode;
import com.yinmimoney.web.p2pnew.pojo.ApiToken;
import com.yinmimoney.web.p2pnew.service.IChatHouse;
import com.yinmimoney.web.p2pnew.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dyf on 2019/7/20 14:58
 */
@RestController("api_ChatHouseController")
@RequestMapping("/api/1.0/chatHouse")
public class ChatHouseController extends BaseController {

    @Autowired
    private IChatHouse chatHouseService;

    @RequestMapping("/setHouseName")
    public ResultCode setHouseName(){
        JSONObject json = getJsonFromRequest();
        if(StringUtil.isAnyBlank(json.getString("cityCode"),json.getString("houseName"),json.getString("money"),json.getString("txId"))){
            return new ResultCode(StatusCode.ERROR_LACK_PARAM);
        }
        return chatHouseService.processSetHouseName(json,getApiToken().getUserCode());
    }
}
