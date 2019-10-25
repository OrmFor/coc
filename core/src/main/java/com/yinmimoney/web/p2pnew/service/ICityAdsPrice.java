package com.yinmimoney.web.p2pnew.service;

import cc.s2m.web.utils.webUtils.service.BaseService;
import com.yinmimoney.web.p2pnew.core.ResultCode;
import com.yinmimoney.web.p2pnew.dto.CityAdsPriceDetailDto;
import com.yinmimoney.web.p2pnew.dto.CityAdsPriceDto;
import com.yinmimoney.web.p2pnew.pojo.CityAdsPrice;
import com.yinmimoney.web.p2pnew.requestbody.CityAdsPriceRequestBody;

import java.util.List;
import java.util.Map;

public interface ICityAdsPrice extends BaseService<CityAdsPrice, Integer> {
    ResultCode processCityAdsPrice(CityAdsPriceRequestBody body);

    Map<String,Object> getCityAdsPriceList(Integer pageNumber, Integer pageSize);

    CityAdsPriceDetailDto getByCityCodeAndUserCode(String cityCode, String userCode);

    List<CityAdsPriceDto> getMyCityAdsPriceList(String userCode);

    ResultCode modifyCityAdsPrice(CityAdsPriceRequestBody body);
}