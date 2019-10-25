package com.yinmimoney.web.p2pnew.dao;

import com.yinmimoney.web.p2pnew.dto.DataLookInfo;
import com.yinmimoney.web.p2pnew.dto.OrderAmountDto;
import com.yinmimoney.web.p2pnew.dto.PlatformIncomeInfo;
import com.yinmimoney.web.p2pnew.dto.SumMoneyDto;
import com.yinmimoney.web.p2pnew.pojo.OrderDetail;
import cc.s2m.web.utils.webUtils.dao.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderDetailMapper extends BaseDao<OrderDetail, Integer> {
    SumMoneyDto getSumAmount(@Param("userCode") String userCode);

    List<DataLookInfo> getDataLookInfos(String platformCode);

    List<PlatformIncomeInfo> getPlatformIncomeInfos(Map<String,Object> params);

    List<PlatformIncomeInfo> getPlatformIncomeInfoOthers(Map<String,Object> params);

    OrderAmountDto getOrderAmount(@Param("userCode")String userCode);
}