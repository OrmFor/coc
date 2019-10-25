package com.yinmimoney.web.p2pnew.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yinmimoney.web.p2pnew.dao.MessageMapper;
import com.yinmimoney.web.p2pnew.pojo.Message;
import com.yinmimoney.web.p2pnew.service.IMessage;

import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

@Service
public class MessageImpl extends BaseServiceImpl<Message, MessageMapper, java.lang.Integer> implements IMessage {
    @Autowired
    private MessageMapper messageMapper;

    protected MessageMapper getDao() {
        return messageMapper;
    }
}