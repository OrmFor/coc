package com.yinmimoney.web.p2pnew.service;

import com.yinmimoney.web.p2pnew.dto.CityPictureDto;
import com.yinmimoney.web.p2pnew.pojo.CityPicture;
import cc.s2m.web.utils.webUtils.service.BaseService;

import java.util.List;

public interface ICityPicture extends BaseService<CityPicture, Integer> {
    CityPicture selectByCode(String cityPictureCode);

    List<CityPictureDto> getHotPicture(String cityCode, Integer pageSize, Integer pageNumber);

    int getHotPictureCount(String cityCode);

    int getHotPictureAllCount();

    List<CityPictureDto> getHotPictureAll(Integer pageSize, Integer pageNumber);
}