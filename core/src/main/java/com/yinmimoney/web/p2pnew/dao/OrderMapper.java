package com.yinmimoney.web.p2pnew.dao;

import com.yinmimoney.web.p2pnew.dto.NewCityPriceDto;
import com.yinmimoney.web.p2pnew.pojo.Order;
import cc.s2m.web.utils.webUtils.dao.BaseDao;
import com.yinmimoney.web.p2pnew.responsebody.CityMaxTransactionPriceResponseBody;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper extends BaseDao<Order, Integer> {
    Order selectByOrderNoForUpdate(@Param("id") Integer id);

    List<NewCityPriceDto> getNewTransactionPriceCity(@Param("beginIndex") Integer beginIndex,@Param("pageSize") Integer pageSize);

    List<CityMaxTransactionPriceResponseBody> getCityMaxTransactionPrice(@Param("beginIndex") Integer beginIndex,@Param("pageSize") Integer pageSize);

    List<NewCityPriceDto> getNewTransactionPriceCityOld();

    List<CityMaxTransactionPriceResponseBody> getCityMaxTransactionPriceOld();
}