package com.yinmimoney.web.p2pnew.service;

import cc.s2m.web.utils.webUtils.service.BaseService;
import com.yinmimoney.web.p2pnew.dto.CheapCity;
import com.yinmimoney.web.p2pnew.dto.CityNameAndCode;
import com.yinmimoney.web.p2pnew.pojo.City;
import com.yinmimoney.web.p2pnew.responsebody.CityDetailResponseBody;
import com.yinmimoney.web.p2pnew.responsebody.MyCitiesResponseBody;

import java.util.List;

public interface ICity extends BaseService<City, Integer> {
    List<CityNameAndCode> getCityName(String cityName);

    City selectByCityCode (String cityCode);

    CityDetailResponseBody getCityDetail(String cityName,String token);

    List<MyCitiesResponseBody> getMyCities(String userCode, Integer pageSize);

    CityDetailResponseBody getCityDetailForCode(String cityCode,String token);

    List<CheapCity> getCheapCity(Integer beginIndex, Integer pageSize,String orderBy);


    List getCityInRangeNew(Double lat, Double lon, Integer raidus, String token);

    public List getCityInRange(Double lat, Double lon, int raidus, String token);
}