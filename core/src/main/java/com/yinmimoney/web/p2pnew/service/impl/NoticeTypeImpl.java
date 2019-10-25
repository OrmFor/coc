package com.yinmimoney.web.p2pnew.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yinmimoney.web.p2pnew.dao.NoticeTypeMapper;
import com.yinmimoney.web.p2pnew.pojo.NoticeType;
import com.yinmimoney.web.p2pnew.service.INoticeType;

import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

@Service
public class NoticeTypeImpl extends BaseServiceImpl<NoticeType, NoticeTypeMapper, java.lang.Integer> implements INoticeType {
    @Autowired
    private NoticeTypeMapper noticeTypeMapper;

    protected NoticeTypeMapper getDao() {
        return noticeTypeMapper;
    }
}