package com.yinmimoney.web.p2pnew.dao;

import com.yinmimoney.web.p2pnew.dto.CityAdsPriceDetailDto;
import com.yinmimoney.web.p2pnew.dto.CityAdsPriceDto;
import com.yinmimoney.web.p2pnew.pojo.CityAdsPrice;
import cc.s2m.web.utils.webUtils.dao.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CityAdsPriceMapper extends BaseDao<CityAdsPrice, Integer> {
    List<CityAdsPriceDto> getCityAdsPriceList(@Param("beginIndex") Integer beginIndex,
                                              @Param("pageSize") Integer pageSize);

    int getCityAdsPriceListCount();

    CityAdsPriceDetailDto getByCityCodeAndUserCode(@Param("cityCode")String cityCode,
                                                   @Param("userCode")String userCode);

    List<CityAdsPriceDto> getMyCityAdsPriceList(@Param("userCode")String userCode);

}