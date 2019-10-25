package com.yinmimoney.web.p2pnew.dao;

import com.yinmimoney.web.p2pnew.dto.*;
import com.yinmimoney.web.p2pnew.pojo.CommonOrderDetail;
import cc.s2m.web.utils.webUtils.dao.BaseDao;
import com.yinmimoney.web.p2pnew.requestbody.GetAdsPictureRequestBody;
import com.yinmimoney.web.p2pnew.responsebody.GetAdsPictureResponseBody;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CommonOrderDetailMapper extends BaseDao<CommonOrderDetail, Integer> {
    List<HotCity> getHotCity(@Param("beginIndex") Integer beginIndex,@Param("pageSize") Integer pageSize,
                             @Param("startTime")String startTime,@Param("endTime")String endTime);

    List<PlatformIncomeInfo> getPlatformIncomeInfoOthers(Map<String,Object> cond);

    BigDecimal getMoneyOut(@Param("userCode") String userCode);

    BigDecimal getMoneyIn(@Param("userCode")String userCode);

    List<DataLookInfo> getDataLookInfos(@Param("platCode")String platCode);

    List<CommonOrderDetail> getCityBillList(Map<String,Object> map);
    Integer getCityBillCount(Map<String,Object> map);

    List<CommonOrderDetail> getCommunicateBillList(Map<String,Object> map);
    Integer getCommunicateBillCount(Map<String,Object> map);

    List<HotCityLasted> getCityHotLasted(@Param("beginIndex") Integer beginIndex, @Param("pageSize") Integer pageSize);

    List<HotCityLasted> getCityHotLastedQuartz();

    Integer getHotCityCount( @Param("startTime")String startTime,@Param("endTime")String endTime);

    Integer getCityHotLastedCount();

    Integer getCityHotNew(@Param("cityCode") String cityCode,@Param("startTime") String startTime,@Param("endTime") String endTime);

    BigDecimal getCityBillIn(String userCode);

    BigDecimal getCityBillOut(String userCode);

    BigDecimal getCommunityBillIn(String userCode);

    BigDecimal getCommunityBillOut(String userCode);

}