package com.yinmimoney.web.p2pnew.service.impl;

import com.yinmimoney.web.p2pnew.dao.CommonOrderMapper;
import com.yinmimoney.web.p2pnew.pojo.CommonOrder;
import com.yinmimoney.web.p2pnew.service.ICommonOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

@Service
public class CommonOrderImpl extends BaseServiceImpl<CommonOrder, CommonOrderMapper, Integer> implements ICommonOrder {
    @Autowired
    private CommonOrderMapper commonOrderMapper;

    protected CommonOrderMapper getDao() {
        return commonOrderMapper;
    }

    @Override
    public CommonOrder selectByOrderNo(String orderNo) {
        CommonOrder order = new CommonOrder();
        order.setOrderNo(orderNo);
        return this.getByCondition(order);
    }
}