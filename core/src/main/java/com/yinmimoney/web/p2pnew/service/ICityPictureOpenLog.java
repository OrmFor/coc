package com.yinmimoney.web.p2pnew.service;

import com.yinmimoney.web.p2pnew.pojo.CityPictureOpenLog;
import cc.s2m.web.utils.webUtils.service.BaseService;

import java.math.BigDecimal;

public interface ICityPictureOpenLog extends BaseService<CityPictureOpenLog, Integer> {

    void processOpenCityPicture(String cityCode, String txid, String userCode,String userName, BigDecimal money);
}