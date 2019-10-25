package com.yinmimoney.web.p2pnew.controller;
import cc.s2m.web.utils.webUtils.vo.Expressions;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.core.ResultCode;
import com.yinmimoney.web.p2pnew.core.StatusCode;
import com.yinmimoney.web.p2pnew.pojo.City;
import com.yinmimoney.web.p2pnew.pojo.CityMapWeathersv;
import com.yinmimoney.web.p2pnew.pojo.WorldCountry;
import com.yinmimoney.web.p2pnew.service.ICity;
import com.yinmimoney.web.p2pnew.service.ICityMapWeathersv;
import com.yinmimoney.web.p2pnew.service.IWorldCountry;
import com.yinmimoney.web.p2pnew.util.HttpUtil;
import com.yinmimoney.web.p2pnew.util.PinYinUtils;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by dyf on 2019/8/26 11:02
 */
@RestController("app_testController")
@RequestMapping("/app/test")
public class TestController extends BaseController {
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(TestController.class);

    private final static String requestWSUrl="https://weathersv.app/api/channel/search";
    @Autowired
    private ICity cityService;
    @Autowired
    private ICityMapWeathersv cityMapWeathersvService;
    @Autowired
    private IWorldCountry worldCountryService;
    @RequestMapping("/update/cities")
    public ResultCode getCities(){

        return new ResultCode(StatusCode.SUCCESS);
    }
    public String buildHttpUrl(BigDecimal lon,BigDecimal lat){
        String lonStirng= lon.toPlainString();
        String latString=lat.toPlainString();
        return requestWSUrl+"?lat="+latString+"&lng="+lonStirng;
    }
}
