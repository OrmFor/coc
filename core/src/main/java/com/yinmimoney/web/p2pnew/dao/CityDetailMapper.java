package com.yinmimoney.web.p2pnew.dao;

import com.yinmimoney.web.p2pnew.dto.CityDto;
import com.yinmimoney.web.p2pnew.pojo.City;
import com.yinmimoney.web.p2pnew.pojo.CityDetail;
import cc.s2m.web.utils.webUtils.dao.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CityDetailMapper extends BaseDao<CityDetail, Integer> {
    List<CityDto> getMyCityList(@Param("userCode") String userCode);
}