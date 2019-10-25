package com.yinmimoney.web.p2pnew.dao;

import com.yinmimoney.web.p2pnew.pojo.MessageFee;
import cc.s2m.web.utils.webUtils.dao.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface MessageFeeMapper extends BaseDao<MessageFee, Integer> {
    BigDecimal getMoneyOut(@Param("userCode") String userCode);
}