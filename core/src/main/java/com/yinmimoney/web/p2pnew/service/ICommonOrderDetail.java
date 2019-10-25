package com.yinmimoney.web.p2pnew.service;

import cc.s2m.util.Page;
import com.alibaba.fastjson.JSONObject;
import com.yinmimoney.web.p2pnew.core.ResultCode;
import com.yinmimoney.web.p2pnew.dto.DataLookInfo;
import com.yinmimoney.web.p2pnew.dto.HotCity;
import com.yinmimoney.web.p2pnew.dto.HotCityLasted;
import com.yinmimoney.web.p2pnew.dto.PlatformIncomeInfo;
import com.yinmimoney.web.p2pnew.pojo.CommonOrder;
import com.yinmimoney.web.p2pnew.pojo.CommonOrderDetail;
import cc.s2m.web.utils.webUtils.service.BaseService;
import com.yinmimoney.web.p2pnew.requestbody.GetAdsPictureRequestBody;
import com.yinmimoney.web.p2pnew.responsebody.GetAdsPictureResponseBody;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ICommonOrderDetail extends BaseService<CommonOrderDetail, Integer> {
    List<HotCity> getHotCity(Integer pageNumber, Integer pageSize);

    List<PlatformIncomeInfo> getPlatformIncomeInfoOthers(Map<String,Object> cond);

    BigDecimal getMoneyOut(String userCode);


    void processCallBackData(String type,JSONObject json);

    void processAds(JSONObject payment);

    BigDecimal getMoneyIn(String userCode);

    List<DataLookInfo> getDataOtherLookInfos();

    List<HotCityLasted> getCityHotLasted(Integer beginIndex, Integer pageSize);

    List<HotCityLasted> getCityHotLasted();


    Page<CommonOrderDetail> getCityBillPage(Map<String,Object> map);

    Page<CommonOrderDetail> getCommunicateBillPage(Map<String,Object> map);

    Integer getCityHotLastedCount();

    Integer getHotCityCount();

    void processAdsPicture(JSONObject json,String userCode);

    List<GetAdsPictureResponseBody> getAdsPicture(GetAdsPictureRequestBody getAdsPictureRequestBody);

    ResultCode processModifyAdsPicture(JSONObject json, String userCode);

    void processCallBack(String type, JSONObject payment);

    void processCityPicture(JSONObject json, String userCode);

    void processLikePicture(JSONObject json, String userCode);

    List<JSONObject> getBillIn(String userCode);

    List<JSONObject> getBillOut(String userCode);
}