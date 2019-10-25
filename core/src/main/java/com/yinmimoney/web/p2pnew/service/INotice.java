package com.yinmimoney.web.p2pnew.service;

import com.yinmimoney.web.p2pnew.pojo.Notice;
import cc.s2m.web.utils.webUtils.service.BaseService;

public interface INotice extends BaseService<Notice, java.lang.Integer> {
    void sendEamilToResetPwd(String userCode, String email, String ip);

    void sendEamilToOauth(String userCode, String email, String ip);
}