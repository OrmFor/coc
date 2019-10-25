package com.yinmimoney.web.p2pnew.dao;

import com.yinmimoney.web.p2pnew.dto.*;
import com.yinmimoney.web.p2pnew.pojo.City;
import cc.s2m.web.utils.webUtils.dao.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CityMapper extends BaseDao<City, Integer> {
    List<CityNameAndCode> getCityName(Map<String,String> condi);

    List<CityZoomDto> getCityInRange(Map<String,Double> around);

    List<CityZoomDtoNew> getCityInRangeNew();

    List<CheapCity> getCheapCity(@Param("beginIndex") Integer beginIndex,@Param("pageSize") Integer pageSize,
                                 @Param("orderBy") String orderBy);
}