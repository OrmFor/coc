package com.yinmimoney.web.p2pnew.controller;

import com.alibaba.fastjson.JSONObject;
import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.core.ResultCode;
import com.yinmimoney.web.p2pnew.core.StatusCode;
import com.yinmimoney.web.p2pnew.pojo.ApiToken;
import com.yinmimoney.web.p2pnew.service.IBlackList;
import com.yinmimoney.web.p2pnew.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dyf on 2019/8/6 15:32
 */
@RestController("api_blackListController")
@RequestMapping("/api/1.0/blackList")
public class BlackListController extends BaseController {
    @Autowired
    private IBlackList blackListService;

    @RequestMapping("/isBlackPermission")
    public ResultCode isBlackList(){
        JSONObject json = getJsonFromRequest();
        String permissionNid = json.getString("permissionNid");
        if(StringUtil.isBlank(permissionNid)){
            return new ResultCode(StatusCode.ERROR_LACK_PARAM);
        }
        ApiToken apiToken = getApiToken();
        boolean blackPermission = blackListService.isBlackPermission(apiToken.getUserCode(), permissionNid);
        JSONObject result = new JSONObject();
        result.put("isBlack",blackPermission==true?1:0);
        return new ResultCode(StatusCode.SUCCESS,result);
    }
}

