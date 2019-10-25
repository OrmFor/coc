package com.yinmimoney.web.p2pnew.service.impl;

import com.yinmimoney.web.p2pnew.dao.LikeLogMapper;
import com.yinmimoney.web.p2pnew.pojo.LikeLog;
import com.yinmimoney.web.p2pnew.service.ILikeLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

@Service
public class LikeLogImpl extends BaseServiceImpl<LikeLog, LikeLogMapper, Integer> implements ILikeLog {
    @Autowired
    private LikeLogMapper likeLogMapper;

    protected LikeLogMapper getDao() {
        return likeLogMapper;
    }
}