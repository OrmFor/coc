package com.yinmimoney.web.p2pnew.service.impl;

import com.yinmimoney.web.p2pnew.dao.MessageSendMapper;
import com.yinmimoney.web.p2pnew.pojo.MessageSend;
import com.yinmimoney.web.p2pnew.service.IMessageSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

@Service
public class MessageSendImpl extends BaseServiceImpl<MessageSend, MessageSendMapper, Integer> implements IMessageSend {
    @Autowired
    private MessageSendMapper messageSendMapper;

    protected MessageSendMapper getDao() {
        return messageSendMapper;
    }
}