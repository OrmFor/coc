package com.yinmimoney.web.p2pnew.service;

import com.alibaba.fastjson.JSONObject;
import com.yinmimoney.web.p2pnew.dto.CityDto;
import com.yinmimoney.web.p2pnew.pojo.City;
import com.yinmimoney.web.p2pnew.pojo.CityDetail;
import cc.s2m.web.utils.webUtils.service.BaseService;
import com.yinmimoney.web.p2pnew.requestbody.ModifyCityDetailRequestBody;
import com.yinmimoney.web.p2pnew.requestbody.ModifyUrlAndMessageRequestBody;
import com.yinmimoney.web.p2pnew.responsebody.MyCityDetailResponseBody;

import java.util.List;

public interface ICityDetail extends BaseService<CityDetail, Integer> {
    CityDetail selectByCityCode(String cityCode);

    void modifyCityDetail(ModifyCityDetailRequestBody modifyCityDetailRequestBody);

    MyCityDetailResponseBody getMyCityDetail(String userCode, String cityCode);

    void modifyMesaageAndUrl(ModifyUrlAndMessageRequestBody body);

    void processMessage(JSONObject payment);

    List<CityDto> getMyCityList(String userCode);
}