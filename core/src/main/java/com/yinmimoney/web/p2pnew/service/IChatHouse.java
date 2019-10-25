package com.yinmimoney.web.p2pnew.service;

import com.alibaba.fastjson.JSONObject;
import com.yinmimoney.web.p2pnew.core.ResultCode;
import com.yinmimoney.web.p2pnew.pojo.ChatHouse;
import cc.s2m.web.utils.webUtils.service.BaseService;

public interface IChatHouse extends BaseService<ChatHouse, Integer> {
    ChatHouse selectByCityCode(String cityCode);
    ResultCode processSetHouseName(JSONObject params,String userCode);
}