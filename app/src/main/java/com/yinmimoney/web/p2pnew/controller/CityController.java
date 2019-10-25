package com.yinmimoney.web.p2pnew.controller;

import cc.s2m.web.utils.webUtils.vo.Expressions;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.core.ResultCode;
import com.yinmimoney.web.p2pnew.core.StatusCode;
import com.yinmimoney.web.p2pnew.dto.CheapCity;
import com.yinmimoney.web.p2pnew.dto.CityNameAndCode;
import com.yinmimoney.web.p2pnew.dto.HotCity;
import com.yinmimoney.web.p2pnew.dto.HotCityLasted;
import com.yinmimoney.web.p2pnew.enums.EnumNoticeNid;
import com.yinmimoney.web.p2pnew.enums.EnumNoticeType;
import com.yinmimoney.web.p2pnew.pojo.City;
import com.yinmimoney.web.p2pnew.pojo.CityActivity;
import com.yinmimoney.web.p2pnew.pojo.CityHot;
import com.yinmimoney.web.p2pnew.responsebody.CityDetailResponseBody;
import com.yinmimoney.web.p2pnew.responsebody.CityMaxTransactionPriceResponseBody;
import com.yinmimoney.web.p2pnew.responsebody.HistoryTransactionPriceResponseBody;
import com.yinmimoney.web.p2pnew.responsebody.NewTransactionPriceResponseBody;
import com.yinmimoney.web.p2pnew.service.*;
import com.yinmimoney.web.p2pnew.util.DateUtil;
import com.yinmimoney.web.p2pnew.util.SmsHandelUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("api_CityController")
@RequestMapping("/api/city")
public class CityController extends BaseController {
    private static final Logger logger = LogManager.getLogger(CityController.class);

    @Autowired
    private ICity cityService;
    @Autowired
    private SmsHandelUtils smsHandelUtils;

    @Autowired
    private IOrder orderService;
    @Autowired
    private ICommonOrderDetail commonOrderDetailService;
    @Autowired
    private ICityActivity cityActivityService;


    private static final String NO_CITY_INFO_MSG = "no result";

    /**
     * @return com.yinmimoney.web.p2pnew.core.ResultCode
     * @Author wwy
     * @Description 地图缩放
     * @Date 9:17 2019/6/24
     * @Param []
     **/
    @RequestMapping(value = "/zoom")
    public ResultCode list(HttpServletRequest request) {
        long l = System.currentTimeMillis();
        JSONObject json = getJsonFromRequest();
        Double lat = json.getDouble("lat");
        Double lon = json.getDouble("lon");
        Integer raidus = json.getInteger("raidus");
        HashMap<String, Object> data = Maps.newHashMap();
        if (lat == null || lon == null || raidus == null) {
            return new ResultCode(StatusCode.SUCCESS, data);
        }
        String token = request.getHeader("token");
        List list = cityService.getCityInRange(lat, lon, raidus, token);
        data.put("list", list);
        long l1 = System.currentTimeMillis();
        logger.info(MessageFormat.format("t2-t1={0}", l1 - l));
        return new ResultCode(StatusCode.SUCCESS, data);
    }


/*
    */
/**
     * @return com.yinmimoney.web.p2pnew.core.ResultCode
     * @Author wwy
     * @Description 地图缩放
     * @Date 9:17 2019/6/24
     * @Param []
     **//*

    @RequestMapping(value = "/zoomOld")
    public ResultCode zoomOld(HttpServletRequest request) {
        long l = System.currentTimeMillis();
        JSONObject json = getJsonFromRequest();
        Double lat = json.getDouble("lat");
        Double lon = json.getDouble("lon");
        Integer raidus = json.getInteger("raidus");
        HashMap<String, Object> data = Maps.newHashMap();
        if (lat == null || lon == null || raidus == null) {
            return new ResultCode(StatusCode.SUCCESS, data);
        }
        String token = request.getHeader("token");
        List list = cityService.getCityInRangeNew(lat, lon, raidus, token);
        data.put("list", list);
        long l1 = System.currentTimeMillis();
        logger.info(MessageFormat.format("t2-t1={0}", l1 - l));
        return new ResultCode(StatusCode.SUCCESS, data);
    }
*/

    /**
     * @return com.yinmimoney.web.p2pnew.core.ResultCode
     * @Author wwy
     * @Description 地图搜索下拉列表
     * @Date 9:17 2019/6/24
     * @Param []
     **/
    @RequestMapping(value = "/getNameList")
    public ResultCode getNameList() {
        JSONObject json = getJsonFromRequest();
        String cityName = json.getString("cityName");
        List<CityNameAndCode> list = new ArrayList<CityNameAndCode>();
        if (StringUtils.isNotBlank(cityName)) {
            list = cityService.getCityName(cityName);
        }
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("list", list == null || list.size() == 0 ? NO_CITY_INFO_MSG : list);
        return new ResultCode(StatusCode.SUCCESS, data);
    }


    /**
     * @return com.yinmimoney.web.p2pnew.core.ResultCode
     * @Author wwy
     * @Description 获取城市详细信息
     * @Date 9:17 2019/6/24
     * @Param []
     **/
    @RequestMapping(value = "/getCityDetail")
    public ResultCode getCityDetail(HttpServletRequest request) {
        JSONObject json = getJsonFromRequest();
        String cityName = json.getString("cityName");
        String token = request.getHeader("token");
        CityDetailResponseBody body = null;
        if (StringUtils.isNotBlank(cityName)) {
            body = cityService.getCityDetail(cityName, token);
        }
        if (body != null && body.getCityCode() != null) {
            body.setIsActivityOpening(0);
            CityActivity cond = new CityActivity();
            cond.setCityCode(body.getCityCode());
            cond.setIsDelete(0);
            cond.and(Expressions.le("start_time", DateUtil.getNow()));
            cond.and(Expressions.ge("end_time", DateUtil.getDayStart(DateUtil.getNow())));
            CityActivity cityActivity = cityActivityService.getByCondition(cond);
            if (cityActivity != null) {
                body.setActivityUrl(cityActivity.getUrl());
                //1:有活动
                body.setIsActivityOpening(1);
                body.setInfoPic(cityActivity.getInfoPic());
            }
        }

        return new ResultCode(StatusCode.SUCCESS, body == null ? NO_CITY_INFO_MSG : body);
    }

    @RequestMapping(value = "/getCityDetailForCode")
    public ResultCode getCityDetailForCode(HttpServletRequest request) {
        JSONObject json = getJsonFromRequest();
        String cityCode = json.getString("cityCode");
        String token = request.getHeader("token");
        CityDetailResponseBody body = null;
        if (StringUtils.isNotBlank(cityCode)) {
            body = cityService.getCityDetailForCode(cityCode, token);
        }
        if (body != null) {
            //0:没有活动
            body.setIsActivityOpening(0);
            CityActivity cond = new CityActivity();
            cond.setCityCode(cityCode);
            cond.setIsDelete(0);
            cond.and(Expressions.le("start_time", DateUtil.getNow()));
            cond.and(Expressions.ge("end_time", DateUtil.getDayStart(DateUtil.getNow())));
            CityActivity cityActivity = cityActivityService.getByCondition(cond);
            if (cityActivity != null) {
                body.setActivityUrl(cityActivity.getUrl());
                //1:有活动
                body.setIsActivityOpening(1);
                body.setInfoPic(cityActivity.getInfoPic());
            }
        }
        return new ResultCode(StatusCode.SUCCESS, body == null ? NO_CITY_INFO_MSG : body);
    }


    /**
     * @return com.yinmimoney.web.p2pnew.core.ResultCode
     * @Author wwy
     * @Description 获取历史成交价格(折线图)
     * @Date 10:36 2019/6/24
     * @Param [cityCode]
     **/
    @RequestMapping("/historyTransactionPriceList")
    public ResultCode historyTransactionPriceList() {
        JSONObject json = getJsonFromRequest();
        String cityCode = json.getString("cityCode");
        List<HistoryTransactionPriceResponseBody> bodyList =
                orderService.selectOrderByCityCode(cityCode);
        City city = cityService.selectByCityCode(cityCode);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("list", bodyList);
        data.put("cityName", city.getCityName());
        return new ResultCode(StatusCode.SUCCESS, data);
    }


    /**
     * @return com.yinmimoney.web.p2pnew.core.ResultCode
     * @Author wwy
     * @Description 获取城市最新成交价  100条
     * @Date 11:00 2019/6/24
     * @Param []
     **/
    @RequestMapping("/cityNewTransactionPrice")
    public ResultCode cityNewTransactionPrice() {

        JSONObject json = getJsonFromRequest();
        Integer pageNumber = json.getInteger("pageNumber");
        Integer pageSize = json.getInteger("pageSize");
        if (pageNumber == null) {
            pageNumber = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Integer beginIndex = (pageNumber - 1) * pageSize;

        List<NewTransactionPriceResponseBody> bodyList =
                orderService.getCityNewTransactionPrice(beginIndex, pageSize);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("list", bodyList);
        data.put("totalCount", 100);
        return new ResultCode(StatusCode.SUCCESS, data);
    }

    /**
     * @return com.yinmimoney.web.p2pnew.core.ResultCode
     * @Author wwy
     * @Description 价格高低  50条
     * @Date 13:47 2019/7/20
     * @Param []
     **/
    @RequestMapping("/cityMaxTransactionPrice")
    public ResultCode cityMaxTransactionPrice() {
        JSONObject json = getJsonFromRequest();
        Integer pageNumber = json.getInteger("pageNumber");
        Integer pageSize = json.getInteger("pageSize");
        if (pageNumber == null || pageNumber < 0) {
            pageNumber = 1;
        }
        if (pageSize == null || pageSize < 0) {
            pageSize = 10;
        }

        Integer beginIndex = (pageNumber - 1) * pageSize;
        List<CityMaxTransactionPriceResponseBody> body = orderService.getCityMaxTransactionPrice(beginIndex, pageSize);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("list", body);
        data.put("totalCount", 100);

        return new ResultCode(StatusCode.SUCCESS, data);
    }


    /**
     * @return com.yinmimoney.web.p2pnew.core.ResultCode
     * @Author wwy
     * @Description 最热城市  8条
     * @Date 13:49 2019/7/20
     * @Param []
     **/
    @RequestMapping("/hot")
    public ResultCode cityHot() {
        JSONObject json = getJsonFromRequest();

        Integer pageNumber = json.getInteger("pageNumber");
        Integer pageSize = json.getInteger("pageSize");
        if (pageNumber == null || pageNumber < 0) {
            pageNumber = 1;
        }
        if (pageSize == null || pageSize < 0) {
            pageSize = 10;
        }

        Integer beginIndex = (pageNumber - 1) * pageSize;

        // Integer count =  commonOrderDetailService.getHotCityCount();
        Map<String, Object> data = new HashMap<String, Object>();

        /*if(count == null || count == 0 ){
            data.put("list", null);
            data.put("totalCount",count);
            return new ResultCode(StatusCode.SUCCESS,data);

        }
*/
        List<HotCity> list = commonOrderDetailService.getHotCity(beginIndex, pageSize);
        data.put("list", list);
        data.put("totalCount", 50);
        return new ResultCode(StatusCode.SUCCESS, data);
    }

    @RequestMapping("/getCheapCity")
    public ResultCode getCheapCity() {
        JSONObject json = getJsonFromRequest();
        Integer pageNumber = json.getInteger("pageNumber");
        Integer pageSize = json.getInteger("pageSize");
        String orderBy = json.getString("orderBy");
        if (pageNumber == null || pageNumber < 0) {
            pageNumber = 1;
        }
        if (pageSize == null || pageSize < 0) {
            pageSize = 10;
        }
        Integer beginIndex = (pageNumber - 1) * pageSize;
        Map<String, Object> data = new HashMap<String, Object>();
        List<CheapCity> list = cityService.getCheapCity(beginIndex, pageSize, orderBy);
        data.put("list", list);
        data.put("totalCount", 1000);
        return new ResultCode(StatusCode.SUCCESS, data);
    }


    @Autowired
    private ICityHot cityHotService;

    //最近发言
    @RequestMapping("/hotLasted")
    public ResultCode cityHotLasted() {
        JSONObject json = getJsonFromRequest();

        Integer pageNumber = json.getInteger("pageNumber");
        Integer pageSize = json.getInteger("pageSize");
        if (pageNumber == null || pageNumber < 0) {
            pageNumber = 1;
        }
        if (pageSize == null || pageSize < 0) {
            pageSize = 10;
        }

        Integer beginIndex = (pageNumber - 1) * pageSize;
        //Integer count =  commonOrderDetailService.getCityHotLastedCount();
        Map<String, Object> data = new HashMap<String, Object>();

        /*if(count == null || count == 0 ){
            data.put("list", null);
            data.put("totalCount",count);
            return new ResultCode(StatusCode.SUCCESS,data);

        }*/

        CityHot condi = new CityHot();
        condi.setPageNumber(pageNumber);
        condi.setPageSize(pageSize);
        condi.setOrderBy("id asc");
        List<CityHot> hots = cityHotService.getList(condi);
        if (hots != null && hots.size() > 0) {
            data.put("list", hots);
            data.put("totalCount", 50);

            return new ResultCode(StatusCode.SUCCESS, data);
        }

        List<HotCityLasted> list = commonOrderDetailService.getCityHotLasted(beginIndex, pageSize);
        data.put("list", list);
        data.put("totalCount", 50);

        return new ResultCode(StatusCode.SUCCESS, data);
    }


    /**
     * @return com.yinmimoney.web.p2pnew.core.ResultCode
     * @Author wwy
     * @Description 获取城市最新成交价  8条
     * @Date 11:00 2019/6/24
     * @Param []
     **/
/*    @RequestMapping("/cityNewTransactionPrice")
    public ResultCode cityNewTransactionPrice() {


        List<NewTransactionPriceResponseBody> bodyList =
                orderService.getCityNewTransactionPriceOld();
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("list", bodyList);
        return new ResultCode(StatusCode.SUCCESS, data);
    }

    */

    /**
     * @return com.yinmimoney.web.p2pnew.core.ResultCode
     * @Author wwy
     * @Description 价格高低  8条
     * @Date 13:47 2019/7/20
     * @Param []
     **//*
    @RequestMapping("/cityMaxTransactionPrice")
    public ResultCode cityMaxTransactionPriceOld(){

        List<CityMaxTransactionPriceResponseBody> body = orderService.getCityMaxTransactionPriceOld();
        return new ResultCode(StatusCode.SUCCESS,body);
    }*/


    //获取城市相册
    @RequestMapping("/sendMessage")
    public ResultCode sendMessage() {
        //City city = cityService.selectByCityCode(order.getCityCode());
        //发送通知
        HashMap<String, Object> sendData = new HashMap();
        sendData.put("buyerName", "老章");
        sendData.put("cityName", "杭州");
        sendData.put("money", 1000);
        sendData.put("cityCode", "12313132131");
        try {
            smsHandelUtils.send(EnumNoticeNid.SUCCESS_BUY, EnumNoticeType.TYPE_MESSAGE, "老章",
                    "laowu",
                    "6093", sendData, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResultCode(StatusCode.SUCCESS);
    }


}