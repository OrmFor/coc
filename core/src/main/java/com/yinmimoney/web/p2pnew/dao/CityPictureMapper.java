package com.yinmimoney.web.p2pnew.dao;

import com.yinmimoney.web.p2pnew.dto.CityPictureDto;
import com.yinmimoney.web.p2pnew.pojo.CityPicture;
import cc.s2m.web.utils.webUtils.dao.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CityPictureMapper extends BaseDao<CityPicture, Integer> {
    List<CityPictureDto> getHotPicture(@Param("cityCode") String code,
                                       @Param("startTime") String startTime,
                                       @Param("endTime") String endTime,
                                       @Param("pageSize") Integer pageSize,
                                       @Param("beginIndex") Integer beginIndex);

    int getHotPictureCount(@Param("cityCode") String cityCode,
                           @Param("startTime") String startTime,
                           @Param("endTime") String endTime);

    List<CityPictureDto> getHotPictureAll(@Param("startTime")String startTime,
                                          @Param("endTime")String endTime,
                                          @Param("pageSize") Integer pageSize,
                                          @Param("beginIndex") Integer beginIndex);

    int getHotPictureAllCount(@Param("startTime")String startTime,
                              @Param("endTime")String endTime);
}