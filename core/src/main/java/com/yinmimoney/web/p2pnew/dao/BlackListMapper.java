package com.yinmimoney.web.p2pnew.dao;

import com.yinmimoney.web.p2pnew.pojo.BlackList;
import cc.s2m.web.utils.webUtils.dao.BaseDao;

import java.util.List;
import java.util.Map;

public interface BlackListMapper extends BaseDao<BlackList, Integer> {
    List<Map<String,Object>> getBlackPermissions(String userCode);
}