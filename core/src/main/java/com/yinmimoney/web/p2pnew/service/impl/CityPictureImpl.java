package com.yinmimoney.web.p2pnew.service.impl;

import com.yinmimoney.web.p2pnew.dao.CityPictureMapper;
import com.yinmimoney.web.p2pnew.dto.CityPictureDto;
import com.yinmimoney.web.p2pnew.pojo.CityPicture;
import com.yinmimoney.web.p2pnew.service.ICityPicture;
import com.yinmimoney.web.p2pnew.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

import java.util.List;

@Service
public class CityPictureImpl extends BaseServiceImpl<CityPicture, CityPictureMapper, Integer> implements ICityPicture {
    @Autowired
    private CityPictureMapper cityPictureMapper;

    protected CityPictureMapper getDao() {
        return cityPictureMapper;
    }

    @Override
    public CityPicture selectByCode(String cityPictureCode) {
        CityPicture bean = new CityPicture();
        bean.setPictureCode(cityPictureCode);

        CityPicture p = getByCondition(bean);

        return p;
    }

    @Override
    public List<CityPictureDto> getHotPicture(String cityCode, Integer pageSize, Integer pageNumber) {
        String startTime = DateUtil.rollDayFormat(DateUtil.convertToGMT(), -6, "yyyy-MM-dd");
        String endTime = DateUtil.dateStr(DateUtil.convertToGMT(), "yyyy-MM-dd");
        Integer beginIndex = ( pageNumber - 1 ) * pageSize ;
        return cityPictureMapper.getHotPicture(cityCode,startTime,endTime,pageSize,beginIndex);

    }

    @Override
    public int getHotPictureCount(String cityCode) {
        String startTime = DateUtil.rollDayFormat(DateUtil.convertToGMT(), -6, "yyyy-MM-dd");
        String endTime = DateUtil.dateStr(DateUtil.convertToGMT(), "yyyy-MM-dd");
        return cityPictureMapper.getHotPictureCount(cityCode, startTime, endTime);
    }

    @Override
    public int getHotPictureAllCount() {
        String startTime = DateUtil.rollDayFormat(DateUtil.convertToGMT(), -6, "yyyy-MM-dd");
        String endTime = DateUtil.dateStr(DateUtil.convertToGMT(), "yyyy-MM-dd");
        return cityPictureMapper.getHotPictureAllCount( startTime, endTime);
    }

    @Override
    public List<CityPictureDto> getHotPictureAll(Integer pageSize, Integer pageNumber) {
        String startTime = DateUtil.rollDayFormat(DateUtil.convertToGMT(), -6, "yyyy-MM-dd");
        String endTime = DateUtil.dateStr(DateUtil.convertToGMT(), "yyyy-MM-dd");
        Integer beginIndex = ( pageNumber - 1 ) * pageSize ;

        return cityPictureMapper.getHotPictureAll( startTime, endTime,pageSize,beginIndex);
    }
}