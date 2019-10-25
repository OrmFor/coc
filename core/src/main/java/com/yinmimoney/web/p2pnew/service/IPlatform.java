package com.yinmimoney.web.p2pnew.service;

import com.yinmimoney.web.p2pnew.pojo.Platform;
import cc.s2m.web.utils.webUtils.service.BaseService;

public interface IPlatform extends BaseService<Platform, Integer> {
    Platform getByPlatCode(String platCode);
}