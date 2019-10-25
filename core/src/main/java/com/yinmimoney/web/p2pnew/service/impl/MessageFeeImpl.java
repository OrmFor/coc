package com.yinmimoney.web.p2pnew.service.impl;

import cc.s2m.util.Page;
import com.google.common.collect.Lists;
import com.yinmimoney.web.p2pnew.dao.MessageFeeMapper;
import com.yinmimoney.web.p2pnew.enums.EnumTradeType;
import com.yinmimoney.web.p2pnew.pojo.City;
import com.yinmimoney.web.p2pnew.pojo.MessageFee;
import com.yinmimoney.web.p2pnew.pojo.Order;
import com.yinmimoney.web.p2pnew.pojo.OrderDetail;
import com.yinmimoney.web.p2pnew.responsebody.MyOrderDetailsResponseBody;
import com.yinmimoney.web.p2pnew.service.IMessageFee;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageFeeImpl extends BaseServiceImpl<MessageFee, MessageFeeMapper, Integer> implements IMessageFee {
    @Autowired
    private MessageFeeMapper messageFeeMapper;

    protected MessageFeeMapper getDao() {
        return messageFeeMapper;
    }

    @Override
    public Map<String, Object> getMyMessageFeeDetail(String userCode, Integer pageNumber, Map<String, String[]> parameterMap) {
        MessageFee condi = new MessageFee();
        condi.setUserCode(userCode);
        condi.setPageNumber(pageNumber);
        condi.setPageSize(10);
        Page<MessageFee> page = getPage(condi, parameterMap);
        List<MyOrderDetailsResponseBody> bodyList = Lists.newArrayList();
        int total = 0;
        if (page != null) {
            List<MessageFee> messageFees = page.getResult();
            if (messageFees != null && messageFees.size() > 0) {
                for (MessageFee orderDetail : messageFees) {
                    MyOrderDetailsResponseBody body = new MyOrderDetailsResponseBody();
                    body.setTxId(orderDetail.getTxid());

                    body.setCreateTime(orderDetail.getCreateTime());
                    body.setType(orderDetail.getType());
                    body.setAmount(orderDetail.getAmount());
                    body.setCurrency(orderDetail.getCurrency());
                    body.setCityName(orderDetail.getCityName());
                    body.setCityCode(orderDetail.getCityCode());
                    body.setTradeType(EnumTradeType.ADS.getName());

                    bodyList.add(body);
                }
            }
            total = page.getTotalRow();
        }

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("list", bodyList);
        data.put("total", total);
        return data;
    }

    @Override
    public BigDecimal getMoneyOut(String userCode) {
        return messageFeeMapper.getMoneyOut(userCode);
    }
}