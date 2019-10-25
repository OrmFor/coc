package com.yinmimoney.web.p2pnew.dao;

import com.yinmimoney.web.p2pnew.pojo.CityHot;
import cc.s2m.web.utils.webUtils.dao.BaseDao;

public interface CityHotMapper extends BaseDao<CityHot, Integer> {
    void deleteAll();
}